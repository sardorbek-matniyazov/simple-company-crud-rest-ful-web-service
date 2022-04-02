package uz.pdp.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.entity.Department;
import uz.pdp.payload.DepartmentDto;
import uz.pdp.payload.Status;
import uz.pdp.service.DepartmentService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "api/v1/department")
@AllArgsConstructor
public class DepartmentController {
    private final DepartmentService service;

    @GetMapping(value = "/all")
    public HttpEntity<List<Department>> get() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping(value = "/{id}")
    public HttpEntity<?> getById(@PathVariable Integer id) {
        Department byId = service.getById(id);
        return byId != null ? ResponseEntity.ok(byId):
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        Status.builder()
                                .active(false)
                                .message("Department not found")
                                .build()
                );
    }

    @PostMapping(value = "/add")
    public HttpEntity<Status> add(@Valid @RequestBody DepartmentDto dto) {
        Status add = service.add(dto);
        return add.isActive() ? ResponseEntity.ok(add):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(add);
    }

    @PutMapping(value = "/{id}")
    public HttpEntity<Status> edit(@PathVariable Integer id, @Valid @RequestBody DepartmentDto dto) {
        Status edit = service.edit(id, dto);
        return edit.isActive() ? ResponseEntity.ok(edit):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(edit);
    }

    @DeleteMapping(value = "{id}")
    public HttpEntity<Status> delete(@PathVariable Integer id){
        Status delete = service.delete(id);
        return delete.isActive() ? ResponseEntity.ok(delete):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(delete);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        return getStringStringMap(ex);
    }

    static Map<String, String> getStringStringMap(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((org.springframework.validation.FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
