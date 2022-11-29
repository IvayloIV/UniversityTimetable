package bg.tuvarna.universitytimetable.service.impl;

import bg.tuvarna.universitytimetable.dto.data.UpdatePasswordData;
import bg.tuvarna.universitytimetable.entity.User;
import bg.tuvarna.universitytimetable.exception.ValidationException;
import bg.tuvarna.universitytimetable.mapper.UserMapper;
import bg.tuvarna.universitytimetable.repository.UserRepository;
import bg.tuvarna.universitytimetable.service.UserService;
import bg.tuvarna.universitytimetable.utils.ResourceBundleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    private final ResourceBundleUtil resourceBundleUtil;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(PasswordEncoder encoder,
                           UserRepository userRepository,
                           ResourceBundleUtil resourceBundleUtil,
                           UserMapper userMapper) {
        this.encoder = encoder;
        this.userRepository = userRepository;
        this.resourceBundleUtil = resourceBundleUtil;
        this.userMapper = userMapper;
    }

    public boolean updateUserPassword(UpdatePasswordData data, BindingResult bindingResult, User user) {
        if (bindingResult.hasErrors()) {
            return false;
        }

        if (user.getPasswordUpdatedDate() != null) {
            throwUpdateUserPasswordException("passwordUpdate.alreadyUpdatedPasswordValidation", data);
        }

        if (!encoder.matches(data.getOldPassword(), user.getPassword())) {
            throwUpdateUserPasswordException("passwordUpdate.oldPasswordValidation", data);
        }

        if (!data.getNewPassword().equals(data.getConfirmPassword())) {
            throwUpdateUserPasswordException("passwordUpdate.passwordsNotMatchValidation", data);
        }

        data.setNewPassword(encoder.encode(data.getNewPassword()));
        userMapper.updateEntityFromModel(data, user);
        userRepository.save(user);
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException(resourceBundleUtil.getMessage("loginUser.badCredentials")));
    }

    private void throwUpdateUserPasswordException(String messageKey, UpdatePasswordData updatePasswordData) {
        String message = resourceBundleUtil.getMessage(messageKey);
        Map<String, Object> models = Map.of("updatePasswordData", updatePasswordData);
        throw new ValidationException(message, "user/password", models);
    }
}
