package by.lobovich.delivery.service.impl;

import by.lobovich.delivery.entity.Dish;
import by.lobovich.delivery.entity.Order;
import by.lobovich.delivery.entity.User;
import by.lobovich.delivery.repository.OrderRepository;
import by.lobovich.delivery.service.OrderService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

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
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public void delete(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public BigDecimal getTotalPrice(Order order) {
        List<Dish> dishes = order.getDishes();
        if (!dishes.isEmpty()) {
            return dishes.stream()
                    .map(Dish::getPrice)
                    .reduce(BigDecimal::add)
                    .get();
        }
        return new BigDecimal(0);
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
                .get();

    }
}
