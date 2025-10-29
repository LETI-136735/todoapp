// java
package ui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import org.springframework.stereotype.Component;
import pdf.PdfService;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import iscteiul.ista.examplefeature.Task;
import iscteiul.ista.examplefeature.TaskService;

@Route("pdf")
@Component
public class PdfView extends VerticalLayout {

    private final TaskService taskService;
    private final PdfService pdfService;

    public PdfView(TaskService taskService, PdfService pdfService) {
        this.taskService = taskService;
        this.pdfService = pdfService;

        Button exportBtn = new Button("Export to PDF");
        Anchor downloadAnchor = new Anchor("#", "");
        downloadAnchor.getElement().setAttribute("download", true);
        downloadAnchor.add(exportBtn);

        exportBtn.addClickListener(e -> {
            List<Task> tasks = taskService.list(null); // usa el método list(Pageable) si necesitas paginar
            List<Map<String, String>> mapped = tasks.stream()
                    .map(t -> Map.of(
                            "name", t.getDescription() == null ? "" : t.getDescription(),
                            "description", t.getDueDate() == null ? "" : t.getDueDate().toString()
                    ))
                    .collect(Collectors.toList());

            byte[] pdfBytes = pdfService.generateTasksPdf(mapped);
            StreamResource resource = new StreamResource("tasks.pdf",
                    () -> new ByteArrayInputStream(pdfBytes));
            downloadAnchor.setHref(resource);
            UI.getCurrent().getPage().executeJs("document.querySelector('a[download]').click();");
        });

        HorizontalLayout header = new HorizontalLayout(downloadAnchor);
        add(header);
        setPadding(true);
    }
}
