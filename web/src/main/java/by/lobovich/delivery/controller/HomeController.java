package by.lobovich.delivery.controller;

import by.lobovich.delivery.entity.BusketItem;
import by.lobovich.delivery.entity.Category;
import by.lobovich.delivery.entity.User;
import by.lobovich.delivery.service.BusketItemService;
import by.lobovich.delivery.service.DishService;
import by.lobovich.delivery.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final UserService userService;
    private final DishService dishService;
    private final BusketItemService busketItemService;

    @Autowired
    public HomeController(UserService userService, DishService dishService, BusketItemService busketItemService) {
        this.userService = userService;
        this.dishService = dishService;
        this.busketItemService = busketItemService;
    }

    @ModelAttribute("currentUser")
    public User getCurrentUser() {
        return userService.getUserFromSecurityContext();
    }

    @ModelAttribute("currentBusket")
    public List<BusketItem> getCurrentBusket() {
        return busketItemService.getBusketItems(getCurrentUser());
    }

    @ModelAttribute("currentBusketSize")
    public Integer getCurrentBusketSize() {
        return busketItemService.getBusketItems(getCurrentUser()).stream()
                .mapToInt(BusketItem::getAmount)
                .sum();
    }

    @GetMapping
    public String homeView() {
        return "home";
    }

    @GetMapping("/menu")
    public String getDishes(
            @RequestParam(required = false) String category,
            Model model) {
        if (category == null || category.isEmpty()) {
            model.addAttribute("dishes", dishService.findAllByCategory(Category.PIZZA));
        } else {
            if (category.equals("REC")) {
                model.addAttribute("dishes", dishService.getRecomendations(getCurrentUser()));
            } else {
                model.addAttribute("dishes", dishService.findAllByCategory(Category.valueOf(category.toUpperCase())));
            }
        }
        return "menu";
    }


}
