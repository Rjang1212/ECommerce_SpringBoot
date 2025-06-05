package payment.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface repositoryProduct extends JpaRepository<entityProduct, Long> {
    Optional<entityProduct> findById(Long id);
}
