package uz.pdp.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.entity.Address;
import uz.pdp.entity.Worker;
import uz.pdp.payload.Status;
import uz.pdp.payload.WorkerDto;
import uz.pdp.repository.AddressRepository;
import uz.pdp.repository.DepartmentRepository;
import uz.pdp.repository.WorkerRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class WorkerService {
    private final WorkerRepository repository;
    private final DepartmentRepository departmentRepository;
    private final AddressRepository addressRepository;

    public List<Worker> getAll() {
        return repository.findAll();
    }

    public Worker getById(Integer id) {
        Optional<Worker> byId = repository.findById(id);
        return byId.orElse(null);
    }

    public Status add(WorkerDto dto) {
        if(!getAddressStatus(dto.getAddress()).isActive())
            return getAddressStatus(dto.getAddress());
        if (!departmentRepository.existsById(dto.getDepartmentId()))
            return Status.builder()
                    .message("Department isn't found")
                    .active(false)
                    .build();
        if(repository.existsByPhoneNumber(dto.getPhoneNumber()))
            return Status.builder()
                    .message("phone number already exists")
                    .active(false)
                    .build();
        repository.save(
                Worker.builder()
                        .name(dto.getName())
                        .phoneNumber(
                                dto.getPhoneNumber())
                        .address(
                                saveAddress(
                                        dto.getAddress()
                                ))
                        .department(
                                departmentRepository.getById(
                                        dto.getDepartmentId()
                                ))
                        .build()
        );
        return Status.builder()
                .message("Worker added")
                .active(true)
                .build();
    }

    private Address saveAddress(Address address) {
        Optional<Address> byStreetAndNumber = addressRepository.findByStreetAndNumber(address.getStreet(), address.getNumber());
        return byStreetAndNumber.orElseGet(() -> addressRepository.save(address));
    }

    public Status edit(Integer id, WorkerDto dto) {
        if (!departmentRepository.existsById(dto.getDepartmentId()))
            return Status.builder()
                    .message("Department isn't found")
                    .active(false)
                    .build();
        if(!getAddressStatus(dto.getAddress()).isActive())
            return getAddressStatus(dto.getAddress());
        Worker worker = Worker.builder()
                .id(id)
                .name(dto.getName())
                .phoneNumber(dto.getPhoneNumber())
                .address(
                        saveAddress(
                                dto.getAddress()
                        ))
                .department(
                        departmentRepository.getById(
                                dto.getDepartmentId()
                        ))
                .build();
        repository.save(worker);
        return Status.builder()
                .message("Worker edited")
                .active(true)
                .build();
    }

    static Status getAddressStatus(Address address) {
        if (address == null)
            return Status.builder()
                    .message("address is required")
                    .active(false)
                    .build();
        if (address.getStreet() == null || address.getStreet().isEmpty())
            return Status.builder()
                    .message("street of address is required")
                    .active(false)
                    .build();
        if (address.getNumber() == null)
            return Status.builder()
                    .message("number of address is required")
                    .active(false)
                    .build();
        return Status.builder()
                .message("done")
                .active(true)
                .build();
    }

    public Status delete(Integer id) {
        if(!repository.existsById(id))
            return Status.builder()
                    .message("Worker isn't found")
                    .active(false)
                    .build();

        repository.deleteById(id);
        return Status.builder()
                .message("Worker deleted")
                .active(true)
                .build();
    }
}
