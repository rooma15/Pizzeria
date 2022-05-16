package by.lobovich.delivery.repository;

import by.lobovich.delivery.entity.BusketItem;
import by.lobovich.delivery.entity.Dish;
import by.lobovich.delivery.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BusketItemRepository extends JpaRepository<BusketItem, Long> {
    List<BusketItem> findAllByUser(User user);
    @Transactional
    BusketItem findByUserAndDish(User user, Dish dish);
    @Transactional
    void deleteByUserAndDish(User user, Dish dish);
    @Transactional
    void deleteAllByUser(User user);
}
