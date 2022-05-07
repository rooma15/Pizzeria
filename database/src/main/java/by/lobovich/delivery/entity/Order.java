package by.lobovich.delivery.entity;

import lombok.*;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "orders")
@Proxy
public class Order extends BaseEntity {

    @OneToOne(orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "date")
    private LocalDateTime dateTime;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "order_dish",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "dish_id")
    )
    private List<Dish> dishes;

    public String getAllDishes() {
        return dishes.stream()
                .map(Dish::getTitle)
                .collect(Collectors.joining(", "));
    }

    public BigDecimal getTotalPrice() {
        if (!dishes.isEmpty()) {
            return dishes.stream()
                    .map(Dish::getPrice)
                    .reduce(BigDecimal::add)
                    .get();
        }
        return new BigDecimal(0);
    }
}
