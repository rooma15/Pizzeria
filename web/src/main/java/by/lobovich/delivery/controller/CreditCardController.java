package by.lobovich.delivery.controller;

import by.lobovich.delivery.entity.*;
import by.lobovich.delivery.service.BusketItemService;
import by.lobovich.delivery.service.CreditCardService;
import by.lobovich.delivery.service.OrderService;
import by.lobovich.delivery.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/creditCard")
public class CreditCardController {

  private final UserService userService;
  private final CreditCardService creditCardService;
  private final OrderService orderService;
  private final BusketItemService busketItemService;

  public CreditCardController(
      UserService userService,
      CreditCardService creditCardService,
      OrderService orderService,
      BusketItemService busketItemService) {
    this.userService = userService;
    this.creditCardService = creditCardService;
    this.orderService = orderService;
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
