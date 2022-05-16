package by.lobovich.delivery.service.impl;

import by.lobovich.delivery.entity.BusketItem;
import by.lobovich.delivery.entity.Dish;
import by.lobovich.delivery.entity.Order;
import by.lobovich.delivery.entity.User;
import by.lobovich.delivery.repository.OrderRepository;
import by.lobovich.delivery.service.OrderService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order getById(Long id) {
        return orderRepository.getOne(id);
    }

    @Override
    public Order createOrder(List<BusketItem> busketItems, User user) {
        List<Dish> dishes = busketItems.stream()
                .map(item -> {
                    List<Dish> itemDishes = new ArrayList<>();
                    for (int i = 0; i < item.getAmount(); i++) {
                        itemDishes.add(item.getDish());
                    }
                    return itemDishes;
                })
                .flatMap(List::stream).collect(Collectors.toList());

        Order order = Order.builder()
                .user(user)
                .dateTime(LocalDateTime.now())
                .dishes(dishes)
                .build();
        return orderRepository.save(order);
    }

    @Override
    public void delete(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public Double getTotalPrice(Order order) {
        List<Dish> dishes = order.getDishes();
        if (!dishes.isEmpty()) {
            return dishes.stream()
                    .mapToDouble(Dish::getPrice)
                    .sum();
        }
        return (double) 0;
    }

    @Override
    public List<Order> getAllByUser(User user) {
        return orderRepository.findAllByUser(user);
    }

    @Override
    public Order getLastByUser(User user) {
        List<Order> allByUser = orderRepository.findAllByUser(user);
        return allByUser.stream()
                .max(Comparator.comparing(Order::getDateTime))
                .orElse(null);

    }
}
