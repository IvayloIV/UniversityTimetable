package bg.tuvarna.universitytimetable.service;

import bg.tuvarna.universitytimetable.dto.data.UpdatePasswordData;
import bg.tuvarna.universitytimetable.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;

public interface UserService extends UserDetailsService {

    boolean updateUserPassword(UpdatePasswordData updatePasswordData, BindingResult bindingResult, User user);
}
