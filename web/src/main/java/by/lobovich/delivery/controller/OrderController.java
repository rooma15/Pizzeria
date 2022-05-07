package by.lobovich.delivery.controller;

import by.lobovich.delivery.entity.Dish;
import by.lobovich.delivery.entity.Order;
import by.lobovich.delivery.entity.User;
import by.lobovich.delivery.service.DishService;
import by.lobovich.delivery.service.OrderService;
import by.lobovich.delivery.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

@Controller
@RequestMapping("order")
public class OrderController {

    private final UserService userService;
    private final OrderService orderService;
    private final DishService dishService;

    @Autowired
    public OrderController(UserService userService, OrderService orderService, DishService dishService) {
        this.userService = userService;
        this.orderService = orderService;
        this.dishService = dishService;
    }

    @ModelAttribute("currentUser")
    public User getCurrentUser() {
        return userService.getUserFromSecurityContext();
    }

    @ModelAttribute("currentOrder")
    public Order getCurrentOrder() {
        return orderService.getLastByUser(getCurrentUser());
        //return getCurrentUser().getOrder();
    }

    @ModelAttribute("newDish")
    public Dish getNewDish() {
        return new Dish();
    }

    @GetMapping
    public String getOrder(Model model) {
        BigDecimal totalPrice = orderService.getTotalPrice(getCurrentOrder());
        model.addAttribute("totalPrice", totalPrice);
        return "order";
    }

    @PostMapping("/add")
    public String addDish(@RequestParam("dishId") Long id) {
        Order order = orderService.getLastByUser(getCurrentUser());
        Dish dish = dishService.getById(id);
        order.getDishes().add(dish);
        orderService.save(order);
        return "redirect:/home/menu?category=" + dish.getCategory();
    }

    @PostMapping("/delete")
    public String deleteDish(@RequestParam Long id) {
        Order order = getCurrentOrder();
        Dish dish = order.getDishes().stream()
                .filter(d -> d.getId().equals(id))
                .findFirst()
                .get();
        order.getDishes().remove(dish);
        orderService.save(order);
        return "redirect:/order";
    }

    @PostMapping("/submit")
    public String submitOrder() {
        User user = getCurrentUser();
        if (orderService.getLastByUser(user).getDishes().isEmpty()) {
            return "redirect:/order";
        }


        Order order = Order.builder()
                .dishes(new ArrayList<>(Collections.emptyList()))
                .user(user)
                .dateTime(LocalDateTime.now())
                .build();


        orderService.save(order);
        user.setOrder(order);
        return "redirect:/home";
    }
}
