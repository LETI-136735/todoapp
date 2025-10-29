// java
package iscteiul.ista.pdf;

// Importaciones necesarias de ahora
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface PdfRepository extends JpaRepository<pdf.Pdf, Long> {
    // métodos adicionales si se necesitan
}
