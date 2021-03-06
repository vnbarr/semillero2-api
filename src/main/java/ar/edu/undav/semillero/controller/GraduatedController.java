package ar.edu.undav.semillero.controller;

import ar.edu.undav.semillero.domain.entity.Graduated;
import ar.edu.undav.semillero.request.CreateGraduatedRequest;
import ar.edu.undav.semillero.service.GraduatedService;
import ar.edu.undav.semillero.view.View;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.time.LocalDate;
import java.util.Collection;

@RestController
@RequestMapping("/graduated")
@CrossOrigin
public class GraduatedController {

    private final GraduatedService graduatedService;

    public GraduatedController(GraduatedService graduatedService) {
        this.graduatedService = graduatedService;
    }

    // Guardar un graduado
    @PostMapping
    public Graduated saveGraduated(@Valid @RequestBody CreateGraduatedRequest request) {
        return graduatedService.save(request);
    }

    // Obtener graduados por ID
    @GetMapping("/{id}")
    public ResponseEntity<Graduated> getGraduated(@PathVariable long id) {
        return WebUtils.emptyToNotFound(graduatedService.findById(id));
    }

    // Eliminar un graduado
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGraduated(@PathVariable long id) {
        return graduatedService.deleteById(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // Obtener todos los graduados
    @JsonView(View.Summary.class)
    @GetMapping
    public Collection<Graduated> getGraduated(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam(value = "when", required = false) LocalDate when, @RequestParam(value = "node", required = false) Long nodeId) {
        if (nodeId != null) {
            return graduatedService.findByNode(nodeId);
        } else if (when != null) {
            return graduatedService.findByDate(when);
        } else {
            return graduatedService.findAll();
        }
    }
}
