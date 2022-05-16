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
@Table(name = "ingredients")
@Proxy
public class Ingredient extends BaseEntity{
    @ManyToMany(mappedBy = "ingredients", fetch = FetchType.LAZY)
    private List<Dish> dishes;

    @Column(name = "ingredient")
    private String name;

}
