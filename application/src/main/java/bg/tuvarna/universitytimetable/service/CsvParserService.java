package bg.tuvarna.universitytimetable.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CsvParserService {

    List<String> getEmails(MultipartFile multipartFile) throws IOException;
}
