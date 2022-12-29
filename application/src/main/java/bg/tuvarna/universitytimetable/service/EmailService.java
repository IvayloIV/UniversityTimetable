package bg.tuvarna.universitytimetable.service;

import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.List;

public interface EmailService {

    void sendScheduleNotifications(String language, List<String> emails, Resource attachment) throws IOException;
}
