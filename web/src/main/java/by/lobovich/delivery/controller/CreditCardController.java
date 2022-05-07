package by.lobovich.delivery.controller;

import by.lobovich.delivery.entity.CreditCard;
import by.lobovich.delivery.entity.Order;
import by.lobovich.delivery.entity.PersonalInfo;
import by.lobovich.delivery.entity.User;
import by.lobovich.delivery.service.CreditCardService;
import by.lobovich.delivery.service.OrderService;
import by.lobovich.delivery.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/creditCard")
public class CreditCardController {

    private final UserService userService;
    private final CreditCardService creditCardService;
    private final OrderService orderService;

    @Autowired
    public CreditCardController(UserService userService, CreditCardService creditCardService, OrderService orderService) {
        this.userService = userService;
        this.creditCardService = creditCardService;
        this.orderService = orderService;
    }

    @ModelAttribute("currentUser")
    public User getCurrentUser() {
        return userService.getUserFromSecurityContext();
    }

    @ModelAttribute("currentOrder")
    public Order getCurrentOrder() {
        return orderService.getLastByUser(getCurrentUser());
    }

    @ModelAttribute("newCreditCard")
    public CreditCard getNewCreditCard() {
        return new CreditCard();
    }

    @GetMapping("/add")
    public String addCreditCard() {
        return "creditCardAdd";
    }

    @PostMapping("/add")
    public String addCreditCard(CreditCard newCreditCard) {
        PersonalInfo personalInfo = getCurrentUser().getPersonalInfo();
        newCreditCard.setPersonalInfo(personalInfo);
        creditCardService.save(newCreditCard);
        return "redirect:/user/" + getCurrentUser().getId();
    }

    @PostMapping("/delete")
    public String deleteCreditCard(@RequestParam Long id) {
        creditCardService.delete(id);
        return "redirect:/user/" + getCurrentUser().getId();
    }
}

