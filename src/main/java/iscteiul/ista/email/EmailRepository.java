package iscteiul.ista.email;
import iscteiul.ista.email.Email;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EmailRepository extends JpaRepository<Email, Long> {
    Slice<Email> findAllBy(Pageable pageable);

}




