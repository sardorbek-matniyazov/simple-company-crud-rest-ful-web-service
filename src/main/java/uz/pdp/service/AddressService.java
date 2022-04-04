package uz.pdp.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.entity.Address;
import uz.pdp.payload.Status;
import uz.pdp.repository.AddressRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AddressService {

    private final AddressRepository repository;

    public List<Address> getAll() {
        return repository.findAll();
    }

    public Address getById(Integer id) {
        Optional<Address> byId = repository.findById(id);
        return byId.orElse(null);
    }

    public Status add(Address address) {
        if(repository.existsByStreetAndNumber(address.getStreet(), address.getNumber()))
            return Status.builder()
                    .active(false)
                    .message("Address already exists")
                    .build();
        repository.save(address);
        
        return Status.builder()
                .active(true)
                .message("Address added")
                .build();
    }

    public Status edit(Integer id, Address address) {
        Optional<Address> byId = repository.findById(id);
        if(byId.isEmpty())
            return Status.builder()
                    .active(false)
                    .message("Address not found")
                    .build();

        if(repository.existsByStreetAndNumberAndIdNot(address.getStreet(), address.getNumber(), id))
            return Status.builder()
                    .active(false)
                    .message("Address already exists")
                    .build();

        Address address1 = byId.get();
        address1.setStreet(address.getStreet());
        address1.setNumber(address.getNumber());
        repository.save(address1);
        return Status.builder()
                .active(true)
                .message("Address edited")
                .build();
    }

    public Status delete(Integer id){
        Optional<Address> byId = repository.findById(id);
        if(byId.isEmpty())
            return Status.builder()
                    .active(false)
                    .message("Address not found")
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
                .active(true)
                .message("Address deleted")
                .build();
    }
}
