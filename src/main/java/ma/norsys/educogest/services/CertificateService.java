package ma.norsys.educogest.services;
import java.text.SimpleDateFormat;
import ma.norsys.educogest.models.Student;


import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import ma.norsys.educogest.dto.CertificateDTO;
import ma.norsys.educogest.models.Certificate;
import ma.norsys.educogest.repository.CertificateRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import java.io.IOException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CertificateService {
    private final CertificateRepository certificateRepository;
    private static final ModelMapper modelMapper = new ModelMapper();

    public void generateCertificate(Student student, String filePath) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
                contentStream.newLineAtOffset(50, 700);
                contentStream.showText("Certificate of Achievement");

                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(0, -40);
                contentStream.showText("This is to certify that");

                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
                contentStream.newLineAtOffset(0, -30);
                contentStream.showText(student.getFirstName() + " " + student.getName());

                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Date of Birth:");

                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.newLineAtOffset(0, -20);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
                contentStream.showText(dateFormat.format(student.getDateOfBirth()));

                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(0, -30);
                contentStream.showText("Educational Path:");

                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText(student.getEducationalPath());

                contentStream.endText();
            }

            document.save(filePath);
        }
    }



    public List<CertificateDTO> getAllCertificates(Pageable pageable){
        return certificateRepository.findAll(pageable)
                .stream()
                .map(CertificateService::convertToDTO)
                .collect(Collectors.toList());
    }

    public CertificateDTO saveCertificate(CertificateDTO certificateDTO){
        Certificate certificate = convertToEntity(certificateDTO);
        Certificate savedCertificate = certificateRepository.save(certificate);
        return convertToDTO(savedCertificate);
    }

    public CertificateDTO updateCertificate(Long id, CertificateDTO updatedCertificateDTO){
        Certificate updatedCertificate = certificateRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException(
                        "Certificate not found with id: " + id
                ));

        if(updatedCertificateDTO.getCertificateGeneration() != null) updatedCertificate.setCertificateGeneration(updatedCertificateDTO.getCertificateGeneration());
        if(updatedCertificateDTO.getPrinting() != null) updatedCertificate.setPrinting(updatedCertificateDTO.getPrinting());
        if(updatedCertificateDTO.getFormat() != null) updatedCertificate.setFormat(updatedCertificateDTO.getFormat());

        Certificate savedCertificate = certificateRepository.save(updatedCertificate);
        return convertToDTO(savedCertificate);
    }

    public void deleteCertificate (Long id) {certificateRepository.deleteById(id);}

    private static CertificateDTO convertToDTO(Certificate certificate) {
        return modelMapper.map(certificate, CertificateDTO.class);
    }

    private Certificate convertToEntity(CertificateDTO certificateDTO) {
        return modelMapper.map(certificateDTO, Certificate.class);
    }
}
