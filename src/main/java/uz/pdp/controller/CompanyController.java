package uz.pdp.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.entity.Company;
import uz.pdp.payload.Status;
import uz.pdp.service.CompanyService;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/company")
@AllArgsConstructor
public class CompanyController {
    private final CompanyService service;

    @GetMapping(value = "/all")
    public HttpEntity<List<Company>> get() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping(value = "/{id}")
    public HttpEntity<?> getById(@PathVariable Integer id) {
        Company byId = service.getById(id);
        return byId != null ? ResponseEntity.ok(byId):
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        Status.builder()
                                .active(false)
                                .message("Company not found")
                                .build()
                );
    }

    @PostMapping(value = "/add")
    public HttpEntity<Status> add(@RequestBody Company company) {
        Status add = service.add(company);
        return add.isActive() ? ResponseEntity.ok(add):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(add);
    }

    @PutMapping(value = "/{id}")
    public HttpEntity<Status> edit(@PathVariable Integer id, @RequestBody Company company) {
        Status edit = service.edit(id, company);
        return edit.isActive() ? ResponseEntity.ok(edit):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(edit);
    }

    @DeleteMapping(value = "{id}")
    public HttpEntity<Status> delete(@PathVariable Integer id){
        Status delete = service.delete(id);
        return delete.isActive() ? ResponseEntity.ok(delete):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(delete);
    }
}
