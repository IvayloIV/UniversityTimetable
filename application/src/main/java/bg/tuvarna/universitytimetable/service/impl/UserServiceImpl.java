package bg.tuvarna.universitytimetable.service.impl;

import bg.tuvarna.universitytimetable.repository.UserRepository;
import bg.tuvarna.universitytimetable.service.UserService;
import bg.tuvarna.universitytimetable.utils.ResourceBundleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ResourceBundleUtil resourceBundleUtil;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           ResourceBundleUtil resourceBundleUtil) {
        this.userRepository = userRepository;
        this.resourceBundleUtil = resourceBundleUtil;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException(resourceBundleUtil.getMessage("loginUser.badCredentials")));
    }
}
