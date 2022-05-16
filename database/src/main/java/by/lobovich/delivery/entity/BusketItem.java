package by.lobovich.delivery.entity;

import lombok.*;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "busket")
@Proxy
public class BusketItem extends BaseEntity{
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
    @OneToOne(fetch = FetchType.EAGER)
    private Dish dish;

    @Column(name = "amount")
    Integer amount;

    @PreRemove
    public void parentRemove(){
        user = null;
    }
}
