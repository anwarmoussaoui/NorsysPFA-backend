package ma.norsys.educogest.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestAPIController {
    @GetMapping("/api/v1")
    public String getResource() {
        return "test";
    }


    @GetMapping("api/v2/{id}")
    public Integer getById(@PathVariable Integer id) {
        return id;

    }
}
