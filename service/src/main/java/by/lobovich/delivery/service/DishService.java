package by.lobovich.delivery.service;

import by.lobovich.delivery.entity.Category;
import by.lobovich.delivery.entity.Dish;
import by.lobovich.delivery.entity.User;

import java.util.List;

public interface DishService {

    Dish getById(Long id);

    List<Dish> findAll();

    Dish save(Dish dish);

    Dish update(Dish dish);

    void delete(Long id);

    List<Dish> findAllByCategory(Category category);

    boolean existsById(Long id);

    List<Dish> getRecomendations(User user);
}
