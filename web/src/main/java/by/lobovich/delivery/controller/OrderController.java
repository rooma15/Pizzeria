package by.lobovich.delivery.controller;

import by.lobovich.delivery.entity.*;
import by.lobovich.delivery.service.*;
import by.lobovich.delivery.service.entity.OrderHistoryItem;
import by.lobovich.delivery.service.impl.AddressServiceImpl;
import by.lobovich.delivery.service.impl.CreditCardServiceImpl;
import by.lobovich.delivery.service.impl.PersonalInfoServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final PersonalInfoService personalInfoService;
    private final AddressService addressService;
    private final CreditCardService creditCardService;

    public OrderController(
            UserService userService,
            OrderService orderService,
            DishService dishService,
            BusketItemService busketItemService,
            PersonalInfoServiceImpl personalInfoService,
            AddressService addressService,
            CreditCardService creditCardService) {
        this.userService = userService;
        this.orderService = orderService;
        this.dishService = dishService;
        this.busketItemService = busketItemService;
        this.personalInfoService = personalInfoService;
        this.addressService = addressService;
        this.creditCardService = creditCardService;
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

    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ExceptionHandler(IllegalStateException.class)
    public void handler(){

    }

    @PostMapping("/submit")
    public String submitOrder() {
        User user = getCurrentUser();
        List<BusketItem> busketItems = busketItemService.getBusketItems(user);
        if (busketItems.isEmpty()) {
            return "redirect:/order";
        }
        PersonalInfo pi = personalInfoService.getByUser(getCurrentUser());
        Address address = addressService.getByPersonalInfo(pi);
        CreditCard creditCard = creditCardService.getByPersonalInfo(pi);
        if(address == null || creditCard == null){
            throw new IllegalStateException();
        }

        if (orderService.createOrder(busketItems, user) != null) {
            busketItemService.clearBusket(user);
        }
        return "redirect:/home";
    }
}
