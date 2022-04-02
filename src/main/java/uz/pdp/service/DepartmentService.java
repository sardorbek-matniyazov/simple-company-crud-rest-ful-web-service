package uz.pdp.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.entity.Department;
import uz.pdp.payload.DepartmentDto;
import uz.pdp.payload.Status;
import uz.pdp.repository.CompanyRepository;
import uz.pdp.repository.DepartmentRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DepartmentService {
    private final DepartmentRepository repository;
    private final CompanyRepository companyRepository;

    public List<Department> getAll() {
        return repository.findAll();
    }

    public Department getById(Integer id) {
        Optional<Department> byId = repository.findById(id);
        return byId.orElse(null);
    }

    public Status add(DepartmentDto dto) {
        if(!companyRepository.existsById(dto.getCompanyId()))
            return Status.builder()
                    .message("there is not any company with this id")
                    .active(false)
                    .build();
        if(repository.existsByNameAndCompanyId(dto.getName(), dto.getCompanyId()))
            return Status.builder()
                    .message("department has already created")
                    .active(false)
                    .build();
        repository.save(
                Department.builder()
                        .name(dto.getName())
                        .company(
                                companyRepository.getById(
                                        dto.getCompanyId()
                                ))
                        .build()
        );
        return Status.builder()
                .message("successfully created")
                .active(true)
                .build();
    }

    public Status edit(Integer id, DepartmentDto dto) {
        if(!companyRepository.existsById(dto.getCompanyId()))
            return Status.builder()
                    .message("there is not any company with this id")
                    .active(false)
                    .build();
        if(repository.existsByNameAndCompanyIdAndIdNot(dto.getName(), dto.getCompanyId(), id))
            return Status.builder()
                    .message("department has already created")
                    .active(false)
                    .build();
        repository.save(
                Department.builder()
                        .id(id)
                        .name(dto.getName())
                        .company(
                                companyRepository.getById(
                                        dto.getCompanyId()
                                ))
                        .build()
        );
        return Status.builder()
                .message("successfully edited")
                .active(true)
                .build();
    }

    public Status delete(Integer id) {
        if(!repository.existsById(id))
            return Status.builder()
                    .message("there is not any department with this id")
                    .active(false)
                    .build();
        repository.deleteById(id);
        return Status.builder()
                .message("successfully deleted")
                .active(true)
                .build();
    }
}
