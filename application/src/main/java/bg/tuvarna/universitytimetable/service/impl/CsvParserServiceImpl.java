package bg.tuvarna.universitytimetable.service.impl;

import bg.tuvarna.universitytimetable.service.CsvParserService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class CsvParserServiceImpl implements CsvParserService {

    public List<String> getEmails(MultipartFile multipartFile) throws IOException {
        List<String> emails = new ArrayList<>();

        try (InputStream is = multipartFile.getInputStream();
             XSSFWorkbook workbook = new XSSFWorkbook(is)) {
            XSSFSheet sheet = workbook.getSheetAt(0);

            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Cell cell = row.getCell(0);
                emails.add(cell.getStringCellValue());
            }
        }

        return emails;
    }
}
