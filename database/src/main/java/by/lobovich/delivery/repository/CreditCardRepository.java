package by.lobovich.delivery.repository;

import by.lobovich.delivery.entity.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
}
