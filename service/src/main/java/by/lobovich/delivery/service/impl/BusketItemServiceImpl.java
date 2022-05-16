package by.lobovich.delivery.service.impl;

import by.lobovich.delivery.entity.BusketItem;
import by.lobovich.delivery.entity.Dish;
import by.lobovich.delivery.entity.User;
import by.lobovich.delivery.repository.BusketItemRepository;
import by.lobovich.delivery.service.BusketItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusketItemServiceImpl implements BusketItemService {
  private final BusketItemRepository busketItemRepository;

  @Autowired
  public BusketItemServiceImpl(BusketItemRepository busketItemRepository) {
    this.busketItemRepository = busketItemRepository;
  }

  @Override
  public BusketItem getById(Long id) {
    return null;
  }

  @Override
  public BusketItem addBusketItem(User user, Dish dish) {
    BusketItem existedBusketItem = findByUserDish(user, dish);
    if (existedBusketItem == null) {
      BusketItem busketItem = new BusketItem(user, dish, 1);
      return save(busketItem);
    } else {
      existedBusketItem.setAmount(existedBusketItem.getAmount() + 1);
      return save(existedBusketItem);
    }
  }

  @Override
  public BusketItem save(BusketItem busketItem) {
    return busketItemRepository.save(busketItem);
  }

  @Override
  public void delete(BusketItem busketItem) {
    busketItemRepository.delete(busketItem);
  }

  @Override
  public List<BusketItem> getBusketItems(User user) {
    return busketItemRepository.findAllByUser(user);
  }

  @Override
  public double getTotalPrice(User user) {
    return getBusketItems(user).stream()
        .mapToDouble((b) -> b.getAmount() * b.getDish().getPrice())
        .sum();
  }

  @Override
  public void deleteByUserDish(User user, Dish dish) {
    BusketItem existedBusketItem = findByUserDish(user, dish);
    if (existedBusketItem.getAmount() < 2) {
      busketItemRepository.deleteByUserAndDish(user, dish);
    } else {
      existedBusketItem.setAmount(existedBusketItem.getAmount() - 1);
      save(existedBusketItem);
    }
  }

  @Override
  public BusketItem findByUserDish(User user, Dish dish) {
    return busketItemRepository.findByUserAndDish(user, dish);
  }

  @Override
  public void clearBusket(User user) {
    busketItemRepository.deleteAllByUser(user);
  }
}
