package uz.pdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.entity.Address;
import uz.pdp.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
    boolean existsByAddress(Address address);
    boolean existsByAddressAndIdNot(Address address, Integer id);
}
