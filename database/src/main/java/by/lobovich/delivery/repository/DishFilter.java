package by.lobovich.delivery.repository;

import by.lobovich.delivery.entity.Category;
import by.lobovich.delivery.entity.Dish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.List;

@Repository
public class DishFilter {

    private final EntityManagerFactory entityManagerFactory;

    @Autowired
    public DishFilter(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public List<Dish> filter(Dish dish) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Dish> query = cb.createQuery(Dish.class);
        Root<Dish> dishRoot = query.from(Dish.class);
        Predicate[] predicates = new Predicate[3];

        predicates[0] = priceFilter(cb, dish, dishRoot);
        predicates[1] = titleFilter(cb, dish, dishRoot);
        predicates[2] = categoryFilter(cb, dish, dishRoot);

        if (predicates[0] != null) {
            query.select(dishRoot).where(predicates[0]);
        }
        if (predicates[1] != null) {
            query.select(dishRoot).where(predicates[1]);
        }
        if (predicates[2] != null) {
            query.select(dishRoot).where(predicates[2]);
        }
        return em.createQuery(query).getResultList();
    }

    private Predicate priceFilter(CriteriaBuilder cb, Dish dish, Root<Dish> root) {
        BigDecimal price = dish.getPrice();
        Path<BigDecimal> path = root.get("price");
        return price != null ? cb.lessThanOrEqualTo(path, price) : null;
    }

    private Predicate titleFilter(CriteriaBuilder cb, Dish dish, Root<Dish> root) {
        String title = dish.getTitle();
        Path<String> path = root.get("title");
        return (title != null && !title.isEmpty()) ? cb.equal(path, title) : null;
    }

    private Predicate categoryFilter(CriteriaBuilder cb, Dish dish, Root<Dish> root) {
        Category category = dish.getCategory();
        Path<Category> path = root.get("category");
        return (category != null) ? cb.equal(path, category) : null;
    }

}
