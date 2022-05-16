package by.lobovich.delivery.controller;

import by.lobovich.delivery.entity.BusketItem;
import by.lobovich.delivery.entity.Dish;
import by.lobovich.delivery.entity.User;
import by.lobovich.delivery.service.BusketItemService;
import by.lobovich.delivery.service.DishService;
import by.lobovich.delivery.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("busket")
public class BusketController {

  private final BusketItemService busketItemService;
  private final UserService userService;
  private final DishService dishService;

  public BusketController(
      BusketItemService busketItemService, UserService userService, DishService dishService) {
    this.busketItemService = busketItemService;
    this.userService = userService;
    this.dishService = dishService;
  }

  @ModelAttribute("currentUser")
  public User getCurrentUser() {
    return userService.getUserFromSecurityContext();
  }

  @ModelAttribute("currentBusket")
  public List<BusketItem> getCurrentBusketItems() {
    return busketItemService.getBusketItems(getCurrentUser());
  }

  @ModelAttribute("currentBusketSize")
  public Integer getCurrentBusketSize() {
    return busketItemService.getBusketItems(getCurrentUser()).stream()
        .mapToInt(BusketItem::getAmount)
        .sum();
  }

  @GetMapping
  String getBusketPage(Model model) {
    model.addAttribute("totalPrice", busketItemService.getTotalPrice(getCurrentUser()));
    return "order";
  }

  @PostMapping
  String addBusketItem(@RequestParam(name = "dishId") Long id, Model model) {
    Dish dish = dishService.getById(id);
    if (dish != null) {
      busketItemService.addBusketItem(getCurrentUser(), dish);
      model.addAttribute("currentBusketSize", getCurrentBusketSize());
    }
    return "home";
  }

  @PostMapping("delete")
  String deleteBusketItem(@RequestParam(name = "dishId") Long id, Model model) {
    Dish dish = dishService.getById(id);
    if (dish != null) {
      busketItemService.deleteByUserDish(getCurrentUser(), dish);
      model.addAttribute("totalPrice", busketItemService.getTotalPrice(getCurrentUser()));
      model.addAttribute("currentBusket", getCurrentBusketItems());
      model.addAttribute("currentBusketSize", getCurrentBusketSize());
    }
    return "order";
  }
}
