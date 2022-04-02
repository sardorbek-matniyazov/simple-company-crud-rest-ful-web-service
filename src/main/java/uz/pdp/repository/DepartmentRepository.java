package uz.pdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    boolean existsByNameAndCompanyId(String name, Integer company_id);

    boolean existsByNameAndCompanyIdAndIdNot(String name, Integer companyId, Integer id);

}
