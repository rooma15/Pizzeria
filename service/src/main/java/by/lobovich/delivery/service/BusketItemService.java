package by.lobovich.delivery.service;

import by.lobovich.delivery.entity.BusketItem;
import by.lobovich.delivery.entity.Dish;
import by.lobovich.delivery.entity.User;
import java.util.List;

public interface BusketItemService {
    BusketItem getById(Long id);

    BusketItem save(BusketItem order);

    void delete(BusketItem busketItem);

    void deleteByUserDish(User user, Dish dish);

    BusketItem addBusketItem(User user, Dish dish);

    List<BusketItem> getBusketItems(User user);

    void clearBusket(User user);

    double getTotalPrice(User user);

    BusketItem findByUserDish(User user, Dish dish);
}
