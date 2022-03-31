package uz.pdp.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.entity.Company;
import uz.pdp.payload.Status;
import uz.pdp.repository.CompanyRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CompanyService {
    private final CompanyRepository repository;

    public List<Company> getAll() {
        return repository.findAll();
    }

    public Company getById(Integer id) {
        Optional<Company> byId = repository.findById(id);
        return byId.orElse(null);
    }

    public Status add(Company company) {
        if(repository.existsByAddress(company.getAddress()))
            return Status.builder()
                    .message("Company with this address already exists")
                    .active(false)
                    .build();
        repository.save(company);
        return Status.builder()
                .message("Company added successfully")
                .active(true)
                .build();
    }

    public Status edit(Integer id, Company company) {
        Optional<Company> byId = repository.findById(id);
        if(byId.isEmpty())
            return Status.builder()
                    .message("Company with this id doesn't exist")
                    .active(false)
                    .build();
        if(repository.existsByAddressAndIdNot(company.getAddress(), id))
            return Status.builder()
                    .message("Company with this address already exists")
                    .active(false)
                    .build();
        repository.save(
                Company.builder()
                        .id(id)
                        .address(company.getAddress())
                        .cName(company.getcName())
                        .build()
        );
        return Status.builder()
                .message("Company edited successfully")
                .active(true)
                .build();
    }

    public Status delete(Integer id) {
        Optional<Company> byId = repository.findById(id);
        if(byId.isEmpty())
            return Status.builder()
                    .message("Company with this id doesn't exist")
                    .active(false)
                    .build();
        repository.deleteById(id);
        return Status.builder()
                .message("Company deleted successfully")
                .active(true)
                .build();
    }
}
