package uz.pdp.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.entity.Address;
import uz.pdp.entity.Company;
import uz.pdp.payload.Status;
import uz.pdp.repository.AddressRepository;
import uz.pdp.repository.CompanyRepository;

import java.util.List;
import java.util.Optional;

import static uz.pdp.service.WorkerService.getAddressStatus;

@Service
@AllArgsConstructor
public class CompanyService {
    private final CompanyRepository repository;
    private final AddressRepository addressRepository;

    public List<Company> getAll() {
        return repository.findAll();
    }

    public Company getById(Integer id) {
        Optional<Company> byId = repository.findById(id);
        return byId.orElse(null);
    }

    public Status add(Company company) {
        if(!getAddressStatus(company.getAddress()).isActive())
            return getAddressStatus(company.getAddress());

        if(
                repository.existsByAddress_NumberAndAddress_Street(
                        company.getAddress().getNumber(),
                        company.getAddress().getStreet())
        ) return Status.builder()
                    .message("Company with this address already exists")
                    .active(false)
                    .build();

        company.setAddress(
                saveAddress(
                        company.getAddress().getStreet(),
                        company.getAddress().getNumber()
                )
        );

        repository.save(company);

        return Status.builder()
                .message("Company added successfully")
                .active(true)
                .build();
    }

    public Status edit(Integer id, Company company) {
        if(!getAddressStatus(company.getAddress()).isActive())
            return getAddressStatus(company.getAddress());
        Optional<Company> byId = repository.findById(id);
        if(byId.isEmpty())
            return Status.builder()
                    .message("Company with this id doesn't exist")
                    .active(false)
                    .build();
        if(
                repository.existsByAddress_NumberAndAddress_StreetAndIdNot(
                        company.getAddress().getNumber(),
                        company.getAddress().getStreet(),
                        id)
        ) return Status.builder()
                    .message("Company with this address already exists")
                    .active(false)
                    .build();
        Company company1 = byId.get();
        company1.setName(company.getName());
        company1.setAddress(saveAddress(company.getAddress().getStreet(), company.getAddress().getNumber()));
        company1.setDirectorName(company.getDirectorName());
        repository.save(company1);

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
        try {

            repository.delete(byId.get());
        }catch (Exception e){
            return Status.builder()
                    .active(false)
                    .message("Address is used")
                    .build();
        }
        return Status.builder()
                .message("Company deleted successfully")
                .active(true)
                .build();
    }

    private Address saveAddress(String street, int number) {
        Optional<Address> byStreetAndNumber = addressRepository.findByStreetAndNumber(street, number);
        return byStreetAndNumber.orElseGet(() -> addressRepository.save(
                Address.builder()
                        .street(street)
                        .number(number)
                        .build())
        );
    }
}
