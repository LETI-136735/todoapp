// java
package pdf;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

@Service
public class PdfService {

    private final PdfRepository pdfRepository;

    public PdfService(PdfRepository pdfRepository) {
        this.pdfRepository = pdfRepository;
    }

    /**
     * Genera bytes de PDF a partir de una lista de mapas con keys "name" y "description".
     */
    public byte[] generateTasksPdf(List<Map<String, String>> tasks) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            float[] columnWidths = {200f, 350f};
            Table table = new Table(columnWidths);

            // Header
            table.addHeaderCell(new Cell().add(new Paragraph("Name")));
            table.addHeaderCell(new Cell().add(new Paragraph("Description")));

            // Rows
            if (tasks != null) {
                for (Map<String, String> t : tasks) {
                    String name = t.getOrDefault("name", "");
                    String desc = t.getOrDefault("description", "");
                    table.addCell(new Cell().add(new Paragraph(name)));
                    table.addCell(new Cell().add(new Paragraph(desc)));
                }
            }

            document.add(table);
            document.close();
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error generating PDF", e);
        }
    }

    /**
     * Genera y guarda un PDF en la base de datos (opcional).
     */
    public Pdf generateAndSavePdf(String filename, List<Map<String, String>> tasks) {
        byte[] content = generateTasksPdf(tasks);
        Pdf entity = new Pdf(filename, content);
        return pdfRepository.save(entity);
    }

    /**
     * Permite crear un InputStream a partir de los bytes si hace falta.
     */
    public ByteArrayInputStream toStream(byte[] pdfBytes) {
        return new ByteArrayInputStream(pdfBytes);
    }
}
