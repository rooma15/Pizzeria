package by.lobovich.delivery.controller;

import by.lobovich.delivery.entity.*;
import by.lobovich.delivery.service.AddressService;
import by.lobovich.delivery.service.BusketItemService;
import by.lobovich.delivery.service.OrderService;
import by.lobovich.delivery.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/address")
public class AddressController {

  private final UserService userService;
  private final AddressService addressService;
  private final OrderService orderService;
  private final BusketItemService busketItemService;

  public AddressController(
      UserService userService,
      AddressService addressService,
      OrderService orderService,
      BusketItemService busketItemService) {
    this.userService = userService;
    this.addressService = addressService;
    this.orderService = orderService;
    this.busketItemService = busketItemService;
  }

  @ModelAttribute("currentUser")
  public User getCurrentUser() {
    return userService.getUserFromSecurityContext();
  }

  @ModelAttribute("newAddress")
  public Address newAddress() {
    return new Address();
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

  @GetMapping("/edit/{id}")
  public String editAddress(@PathVariable Long id, Model model) {
    Address address = addressService.getById(id);
    model.addAttribute("currentAddress", address);
    return "addressEdit";
  }

  @GetMapping("/add")
  public String addAddress() {
    return "addressAdd";
  }

  @PostMapping("/add")
  public String addAddress(@RequestParam String city, Address newAddress) {
    PersonalInfo personalInfo = getCurrentUser().getPersonalInfo();
    newAddress.setCityName(city);
    newAddress.setPersonalInfo(personalInfo);
    addressService.save(newAddress);
    return "redirect:/user/" + getCurrentUser().getId();
  }

  @PostMapping("/edit")
  public String updateAddress(
      @RequestParam Long id,
      @RequestParam String city,
      @RequestParam(required = false) String street,
      @RequestParam(required = false) String house,
      @RequestParam(required = false) String flat,
      @RequestParam(required = false) String stage,
      @RequestParam(required = false) String entrance) {
    Address address = addressService.getById(id);
    address.setCityName(city);
    address.setStreetName(street);
    address.setHouseNumber(house);
    address.setFlatNumber(flat);
    address.setStageNumber(stage);
    address.setEntranceNumber(entrance);
    addressService.save(address);
    return "redirect:/user/" + getCurrentUser().getId();
  }

  @PostMapping("/delete")
  public String deleteAddress(@RequestParam Long id) {
    Address address = addressService.getById(id);
    addressService.deleteByAddress(address);
    return "redirect:/user/" + getCurrentUser().getId();
  }
}
