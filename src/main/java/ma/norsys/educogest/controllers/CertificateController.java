package ma.norsys.educogest.controllers;


import lombok.AllArgsConstructor;
import ma.norsys.educogest.services.StudentService;
import ma.norsys.educogest.models.Student;
import org.springframework.http.*;
import ma.norsys.educogest.constants.Mappings;
import ma.norsys.educogest.dto.CertificateDTO;
import ma.norsys.educogest.services.CertificateService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static ma.norsys.educogest.constants.PageRequestSizePage.DEFAULT_PAGE;
import static ma.norsys.educogest.constants.PageRequestSizePage.DEFAULT_SIZE;

@RestController

@RequestMapping(value = Mappings.CERTIFICATE_PAGE)
public class CertificateController {

    private final CertificateService certificateService;
    private final StudentService studentService;

    public CertificateController(CertificateService certificateService, StudentService studentService) {
        this.certificateService = certificateService;
        this.studentService = studentService;
    }


    @GetMapping
    public ResponseEntity<List<CertificateDTO>> getCertificatesinfo(@RequestParam(defaultValue = DEFAULT_SIZE, required = false)
                                                                        Integer pageSize,
                                                                    @RequestParam(defaultValue = DEFAULT_PAGE, required = false)
                                                                    Integer page){
        Pageable paging = PageRequest.of(page, pageSize);
        List<CertificateDTO> certificateDTOs = certificateService.getAllCertificates(paging);
        return new ResponseEntity<>(certificateDTOs, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CertificateDTO> saveCertificate(@RequestBody CertificateDTO certificateDTO){
        CertificateDTO savedCertificateDTO = certificateService.saveCertificate(certificateDTO);
        return new ResponseEntity<>(savedCertificateDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CertificateDTO> updateCertificate(@PathVariable Long id, @RequestBody CertificateDTO updatedCertificateDTO){
        CertificateDTO certificateDTO = certificateService.updateCertificate(id, updatedCertificateDTO);
        return new ResponseEntity<>(certificateDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCertificate(@PathVariable Long id){
        certificateService.deleteCertificate(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/generate/{studentId}")
    public ResponseEntity<String> generateCertificate(@PathVariable Long studentId) {
        try {
            Student student = studentService.getStudentById(studentId);
            String fileName = student.getName() + "_" + student.getIdentifier() + "_certificate.pdf";
            String filePath = "C:\\Users\\HP\\Desktop\\ProjetDeStage\\Certificates\\" + fileName;
            certificateService.generateCertificate(student, filePath);
            return ResponseEntity.ok("Certificate generated and saved successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error generating and saving certificate: " + e.getMessage());
        }
    }

}
