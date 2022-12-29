package bg.tuvarna.universitytimetable.service.impl;

import bg.tuvarna.universitytimetable.dto.model.CourseScheduleModel;
import bg.tuvarna.universitytimetable.service.PdfService;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.expression.ThymeleafEvaluationContext;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.DayOfWeek;
import java.util.List;

@Service
public class PdfServiceImpl implements PdfService {

    private final TemplateEngine templateEngine;
    private final ApplicationContext applicationContext;

    @Autowired
    public PdfServiceImpl(TemplateEngine templateEngine,
                          ApplicationContext applicationContext) {
        this.templateEngine = templateEngine;
        this.applicationContext = applicationContext;
    }

    public Resource generateSchedule(CourseScheduleModel courseScheduleModel, List<DayOfWeek> daysOfWeek) {
        Context context = new Context();
        context.setVariable(ThymeleafEvaluationContext.THYMELEAF_EVALUATION_CONTEXT_CONTEXT_VARIABLE_NAME,
                new ThymeleafEvaluationContext(applicationContext, null));
        context.setVariable("scheduleCourse", courseScheduleModel);
        context.setVariable("disableEdit", true);
        context.setVariable("pdfView", true);
        context.setVariable("daysOfWeek", daysOfWeek);

        String html = templateEngine.process("schedule/item", context);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter pdfWriter = new PdfWriter(outputStream);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        pdfDocument.setDefaultPageSize(new PageSize(945, 1336).rotate());
        ConverterProperties converterProperties = new ConverterProperties();
        HtmlConverter.convertToPdf(html, pdfDocument, converterProperties);

        return new InputStreamResource(new ByteArrayInputStream(outputStream.toByteArray()));
    }
}
