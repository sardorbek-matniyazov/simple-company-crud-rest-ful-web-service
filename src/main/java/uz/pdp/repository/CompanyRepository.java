package uz.pdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
    boolean existsByAddress_NumberAndAddress_Street(Integer number, String street);
    boolean existsByAddress_NumberAndAddress_StreetAndIdNot(Integer number, String street, Integer id);

}
