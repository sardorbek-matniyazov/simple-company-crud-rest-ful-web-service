package uz.pdp.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.entity.Address;
import uz.pdp.payload.Status;
import uz.pdp.service.AddressService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import static uz.pdp.controller.DepartmentController.getStringStringMap;

@RestController
@RequestMapping(value = "api/v1/address")
@AllArgsConstructor
public class AddressController {
    private final AddressService service;

    @GetMapping(value = "/all")
    public HttpEntity<List<Address>> get() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping(value = "/{id}")
    public HttpEntity<?> getById(@PathVariable Integer id) {
        Address byId = service.getById(id);
        return byId != null ? ResponseEntity.ok(byId):
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        Status.builder()
                                .active(false)
                                .message("Address not found")
                                .build()
                );
    }

    @PostMapping(value = "/add")
    public HttpEntity<Status> add(@Valid @RequestBody Address address) {
        Status add = service.add(address);
        return add.isActive() ? ResponseEntity.ok(add):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(add);
    }

    @PutMapping(value = "/{id}")
    public HttpEntity<Status> edit(@PathVariable Integer id,@Valid @RequestBody Address address) {
        Status edit = service.edit(id, address);
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
}
