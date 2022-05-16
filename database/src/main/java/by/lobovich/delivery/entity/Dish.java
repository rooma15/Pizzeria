package by.lobovich.delivery.entity;

import lombok.*;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "dishes")
@Proxy
public class Dish extends BaseEntity {

    @Column(name = "price")
    private Double price;

    @Column(name = "sale")
    private Boolean isSale = false;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "category")
    @Enumerated(value = EnumType.STRING)
    private Category category;

    @ManyToMany(mappedBy = "dishes", fetch = FetchType.LAZY)
    private List<Order> orders;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "dish_ingredient",
            joinColumns = @JoinColumn(name = "dish_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    //@OneToMany(mappedBy = "dish", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    //private List<DishIngredient> dishIngredients;
    List<Ingredient> ingredients;
}
