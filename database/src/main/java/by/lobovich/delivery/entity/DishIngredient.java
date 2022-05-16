package by.lobovich.delivery.entity;

import lombok.*;
import org.hibernate.annotations.Proxy;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "dish_ingredient")
@Proxy
public class DishIngredient extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "dish_id")
    Dish dish;

    @ManyToOne
    @JoinColumn(name = "ingredient_id")
    Ingredient ingredient;
}
