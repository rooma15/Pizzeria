package by.lobovich.delivery.controller;

import by.lobovich.delivery.entity.Order;
import by.lobovich.delivery.entity.PersonalInfo;
import by.lobovich.delivery.entity.Role;
import by.lobovich.delivery.entity.User;
import by.lobovich.delivery.service.OrderService;
import by.lobovich.delivery.service.PersonalInfoService;
import by.lobovich.delivery.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private final UserServiceImpl userService;
    private final OrderService orderService;
    private final PersonalInfoService personalInfoService;

    @Autowired
    public RegistrationController(UserServiceImpl userService, OrderService orderService, PersonalInfoService personalInfoService) {
        this.userService = userService;
        this.orderService = orderService;
        this.personalInfoService = personalInfoService;
    }

    @ModelAttribute("newUser")
    public User emptyUser() {
        return User.builder()
                .active(true)
                .roles(Collections.singleton(Role.USER))
                .build();
    }

    @ModelAttribute("personalInfo")
    public PersonalInfo emptyPersonalInfo() {
        return new PersonalInfo();
    }

    @GetMapping
    public String registration() {
        return "registration";
    }

    @PostMapping
    public String addUser(
            User newUser,
            PersonalInfo personalInfo,
            Model model
    ) {
        if (userService.isExists(newUser)) {
            model.addAttribute("message", "This username already exists");
            return "/registration";
        }

        newUser.setRoles(Collections.singleton(Role.USER));
        newUser.setActive(true);
        userService.save(newUser);
        personalInfo.setUser(newUser);
        personalInfoService.save(personalInfo);
        Order order = Order.builder()
                .dishes(new ArrayList<>())
                .user(newUser)
                .dateTime(LocalDateTime.now())
                .build();
        orderService.save(order);
        return "redirect:/login";
    }
}
