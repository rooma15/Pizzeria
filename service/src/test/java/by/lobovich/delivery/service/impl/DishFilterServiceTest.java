package by.lobovich.delivery.service.impl;

import by.lobovich.delivery.BaseTest;
import by.lobovich.delivery.entity.Dish;
import by.lobovich.delivery.service.DishFilterService;
import by.lobovich.delivery.service.DishService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

class DishFilterServiceTest extends BaseTest {

    @Autowired
    DishFilterService dishFilterService;
    @Autowired
    DishService dishService;

    @Test
    void filter() {
        Dish byId = dishService.getById(1L);
        System.out.println(byId.getTitle());
        List<Dish> filter = dishFilterService.filter(byId);
        System.out.println(filter.size());
        filter.forEach(System.out::println);
    }
}