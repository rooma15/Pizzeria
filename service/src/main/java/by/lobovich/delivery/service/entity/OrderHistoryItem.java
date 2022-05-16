package by.lobovich.delivery.service.entity;

import by.lobovich.delivery.entity.Dish;
import by.lobovich.delivery.entity.Order;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderHistoryItem {
    LocalDate date;
    Map<Dish, Integer> dishes;
    double totalPrice;

    public OrderHistoryItem(Order order){
        date = order.getDateTime().toLocalDate();
        dishes = new HashMap<>();
        List<Dish> dishList = order.getDishes();
        for (Dish dish : order.getDishes()) {
            dishes.put(dish, Collections.frequency(dishList, dish));
            totalPrice += dish.getPrice();
        }
    }

    public LocalDate getDate() {
        return date;
    }

    public Map<Dish, Integer> getDishes() {
        return dishes;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }
}
