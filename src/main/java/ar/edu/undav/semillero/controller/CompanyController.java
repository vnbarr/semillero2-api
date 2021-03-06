package ar.edu.undav.semillero.controller;

import ar.edu.undav.semillero.domain.entity.Company;
import ar.edu.undav.semillero.request.CreateCompanyRequest;
import ar.edu.undav.semillero.service.CompanyService;
import ar.edu.undav.semillero.view.View;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.util.Collection;

@RestController
@RequestMapping("/company")
@CrossOrigin
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    // Agregar una company
    @PostMapping
    public Company addCompany(@Valid @RequestBody CreateCompanyRequest request) {
        return companyService.save(request);
    }

    // Obtener company por ID
    @ResponseBody
    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompany(@PathVariable long id) {
        return WebUtils.emptyToNotFound(companyService.findById(id));
    }

    // Obtener todas las companias, si se pasa parametro, se devuelve por nombre
    @ResponseBody
    @JsonView(View.Summary.class)
    @GetMapping
    public ResponseEntity<Collection<Company>> getCompany(@RequestParam(value = "name", required = false) String name) {
        Collection<Company> companies = name == null ? companyService.findAll() : companyService.findByName(name);
        return WebUtils.emptyToNotFound(companies);
    }
}
