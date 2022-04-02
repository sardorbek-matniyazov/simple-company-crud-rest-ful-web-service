package uz.pdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.entity.Address;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Integer> {
    boolean existsByStreetAndNumber(String street, Integer number);

    boolean existsByStreetAndNumberAndIdNot(String street, Integer number, Integer id);

    Address getByStreetAndNumber(String street, Integer number);

    Optional<Address> findByStreetAndNumber(String street, Integer number);

}
