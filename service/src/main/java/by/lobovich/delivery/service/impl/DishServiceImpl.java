package by.lobovich.delivery.service.impl;

import by.lobovich.delivery.entity.*;
import by.lobovich.delivery.repository.DishRepository;
import by.lobovich.delivery.service.DishService;
import by.lobovich.delivery.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class DishServiceImpl implements DishService {

    private final DishRepository dishRepository;
    private final OrderService orderService;

    @Autowired
    public DishServiceImpl(DishRepository dishRepository, OrderService orderService) {
        this.dishRepository = dishRepository;
        this.orderService = orderService;
    }

    @Override
    public Dish getById(Long id) {
        return dishRepository.getOne(id);
    }

    @Override
    public List<Dish> findAll() {
        return dishRepository.findAll();
    }

    @Override
    public Dish save(Dish dish) {
        return dishRepository.save(dish);
    }

    @Override
    public Dish update(Dish dish) {
        return dishRepository.save(dish);
    }

    @Override
    public void delete(Long id) {
        dishRepository.deleteById(id);
    }

    @Override
    public List<Dish> findAllByCategory(Category category) {
        return dishRepository.findAllByCategory(category);
    }

    @Override
    public boolean existsById(Long id) {
        return dishRepository.existsById(id);
    }

    private static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor)
    {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    private int numberOfOccurences(List<Ingredient> o1, List<Ingredient> o2){
        int numOfOccurences = 0;
        for (int i = 0; i < o1.size(); i++) {
            for (int j = 0; j < o2.size(); j++) {
                if(o1.get(i).getId().equals(o2.get(j).getId())){
                    numOfOccurences++;
                }
            }
        }
        return numOfOccurences;
    }

    @Override
    public List<Dish> getRecomendations(User user) {
        List<Order> orders = orderService.getAllByUser(user);
        List<Ingredient> ingredients = new ArrayList<>();
        for (Order order : orders) {
            for (Dish dish : order.getDishes()) {
                ingredients.addAll(dish.getIngredients());
            }
        }
        List<Ingredient> topIngredients = ingredients.stream()
                .sorted((o1, o2) -> Integer.compare(
                        Collections.frequency(ingredients, o2),
                        Collections.frequency(ingredients, o1)))
                .filter(distinctByKey(Ingredient::getId))
                .limit(3)
                .collect(Collectors.toList());
        List<Dish> allDishes = dishRepository.findAllByCategory(Category.PIZZA);
        List<Dish> topDishes = new ArrayList<>();
        for(int i = 0; i < 3; i++){
            int max = 0;
            Dish topDish = allDishes.get(0);
            for (Dish dish : allDishes) {
                int numOfOccurs = numberOfOccurences(topIngredients, dish.getIngredients());
                if(max < numOfOccurs){
                    max = numOfOccurs;
                    topDish = dish;
                }
            }
            allDishes.remove(topDish);
            topDishes.add(topDish);
        }
        return topDishes;
    }
}
