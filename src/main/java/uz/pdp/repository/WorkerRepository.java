package uz.pdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.entity.Worker;

public interface WorkerRepository extends JpaRepository<Worker, Integer> {
    boolean existsByPhoneNumber(String phoneNumber);
}
