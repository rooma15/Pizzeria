package by.lobovich.delivery.service.impl;

import by.lobovich.delivery.entity.Role;
import by.lobovich.delivery.entity.User;
import by.lobovich.delivery.repository.UserRepository;
import by.lobovich.delivery.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepo;

    public UserServiceImpl(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public User getById(Long id) {
        return userRepo.getOne(id);
    }

    @Override
    public User findByUsername(String name) {
        return userRepo.findByUsername(name);
    }

    @Override
    public List<User> findAll() {
        return userRepo.findAll();
    }

    @Override
    public User save(User user) {
        return userRepo.save(user);
    }

    @Override
    public User update(User user) {
        return userRepo.save(user);
    }

    @Override
    public void delete(Long id) {
        userRepo.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
    }

    @Transactional
    @Override
    public UserDetails getUserDetails() {
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public User getUserFromSecurityContext() {
        String username = getUserDetails().getUsername();
        return userRepo.findByUsername(username);
    }

    @Override
    public boolean isExists(User user) {
        return userRepo.findAll().stream()
                .map(User::getUsername)
                .anyMatch(name -> name.equals(user.getUsername()));
    }

    @Override
    public boolean deleteAdminRole(User user) {
        return user.getRoles().remove(Role.ADMIN);
    }

    @Override
    public boolean addAdminRole(User user) {
        return user.getRoles().add(Role.ADMIN);
    }
}
