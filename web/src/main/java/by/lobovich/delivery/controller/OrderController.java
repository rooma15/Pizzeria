package by.lobovich.delivery.controller;

import by.lobovich.delivery.entity.BusketItem;
import by.lobovich.delivery.entity.Dish;
import by.lobovich.delivery.entity.Order;
import by.lobovich.delivery.entity.User;
import by.lobovich.delivery.service.BusketItemService;
import by.lobovich.delivery.service.DishService;
import by.lobovich.delivery.service.OrderService;
import by.lobovich.delivery.service.UserService;
import by.lobovich.delivery.service.entity.OrderHistoryItem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("order")
public class OrderController {

    private final UserService userService;
    private final OrderService orderService;
    private final DishService dishService;
    private final BusketItemService busketItemService;

    public OrderController(
            UserService userService,
            OrderService orderService,
            DishService dishService,
            BusketItemService busketItemService) {
        this.userService = userService;
        this.orderService = orderService;
        this.dishService = dishService;
        this.busketItemService = busketItemService;
    }

    @ModelAttribute("currentUser")
    public User getCurrentUser() {
        return userService.getUserFromSecurityContext();
    }

    @ModelAttribute("currentOrder")
    public Order getCurrentOrder() {
        return orderService.getLastByUser(getCurrentUser());
    }

    @ModelAttribute("currentBusket")
    public List<BusketItem> getCurrentBusket() {
        return busketItemService.getBusketItems(getCurrentUser());
    }

    @ModelAttribute("currentBusketSize")
    public Integer getCurrentBusketSize() {
        return busketItemService.getBusketItems(getCurrentUser())
                .stream()
                .mapToInt(BusketItem::getAmount)
                .sum();
    }

    @ModelAttribute("orders")
    public List<OrderHistoryItem> getOrders() {
        return orderService.getAllByUser(getCurrentUser())
                .stream()
                .map(OrderHistoryItem::new)
                .collect(Collectors.toList());
    }

    @ModelAttribute("newDish")
    public Dish getNewDish() {
        return new Dish();
    }

    @GetMapping
    public String getOrder(Model model) {
        return "orderhistory";
    }

    @PostMapping("/submit")
    public String submitOrder() {
        User user = getCurrentUser();
        List<BusketItem> busketItems = busketItemService.getBusketItems(user);
        if (busketItems.isEmpty()) {
            return "redirect:/order";
        }
        if (orderService.createOrder(busketItems, user) != null) {
            busketItemService.clearBusket(user);
        }
        return "redirect:/home";
    }
}
