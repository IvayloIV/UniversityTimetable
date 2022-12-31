package bg.tuvarna.universitytimetable.service.impl;

import bg.tuvarna.universitytimetable.dto.data.TeacherScheduleSearchData;
import bg.tuvarna.universitytimetable.dto.model.CourseScheduleModel;
import bg.tuvarna.universitytimetable.dto.model.TeacherScheduleModel;
import bg.tuvarna.universitytimetable.entity.enums.CourseWeek;
import bg.tuvarna.universitytimetable.service.PdfService;
import bg.tuvarna.universitytimetable.service.TeacherService;
import bg.tuvarna.universitytimetable.util.DayOfWeekUtil;
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
import java.util.List;
import java.util.Map;

@Service
public class PdfServiceImpl implements PdfService {

    private final TemplateEngine templateEngine;
    private final ApplicationContext applicationContext;
    private final TeacherService teacherService;

    @Autowired
    public PdfServiceImpl(TemplateEngine templateEngine,
                          ApplicationContext applicationContext,
                          TeacherService teacherService) {
        this.templateEngine = templateEngine;
        this.applicationContext = applicationContext;
        this.teacherService = teacherService;
    }

    public Resource generateStudentSchedule(CourseScheduleModel courseScheduleModel) {
        Context context = new Context();
        context.setVariable(ThymeleafEvaluationContext.THYMELEAF_EVALUATION_CONTEXT_CONTEXT_VARIABLE_NAME,
                new ThymeleafEvaluationContext(applicationContext, null));
        context.setVariable("scheduleCourse", courseScheduleModel);
        context.setVariable("disableEdit", true);
        context.setVariable("pdfView", true);
        context.setVariable("daysOfWeek", DayOfWeekUtil.getWorkDays());

        String html = templateEngine.process("schedule/studentItem", context);
        return generateSchedule(html);
    }

    public Resource generateTeacherSchedule(TeacherScheduleSearchData searchData, Map<String, List<TeacherScheduleModel>> schedule) {
        Context context = new Context();
        context.setVariable(ThymeleafEvaluationContext.THYMELEAF_EVALUATION_CONTEXT_CONTEXT_VARIABLE_NAME,
                new ThymeleafEvaluationContext(applicationContext, null));
        context.setVariable("academicYear", searchData.getAcademicYear());
        context.setVariable("semester", searchData.getSemester());
        context.setVariable("teacher", teacherService.getFilterModelById(searchData.getTeacherId()));
        context.setVariable("courseWeeks", List.of(CourseWeek.EVEN, CourseWeek.ODD));
        context.setVariable("daysOfWeek", DayOfWeekUtil.getWorkDays());
        context.setVariable("schedules", schedule);
        context.setVariable("pdfView", true);

        String html = templateEngine.process("schedule/teacherItem", context);
        return generateSchedule(html);
    }

    private Resource generateSchedule(String html) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter pdfWriter = new PdfWriter(outputStream);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        pdfDocument.setDefaultPageSize(new PageSize(945, 1336).rotate());
        ConverterProperties converterProperties = new ConverterProperties();
        HtmlConverter.convertToPdf(html, pdfDocument, converterProperties);

        return new InputStreamResource(new ByteArrayInputStream(outputStream.toByteArray()));
    }
}
