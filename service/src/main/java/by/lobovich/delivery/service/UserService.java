package by.lobovich.delivery.service;

import by.lobovich.delivery.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    User getById(Long id);

    User findByUsername(String name);

    List<User> findAll();

    User save(User user);

    User update(User user);

    void delete(Long id);

    UserDetails getUserDetails();

    User getUserFromSecurityContext();

    boolean isExists(User user);

    boolean deleteAdminRole(User user);

    boolean addAdminRole(User user);
}
