package by.lobovich.delivery.repository;

import by.lobovich.delivery.entity.CreditCard;
import by.lobovich.delivery.entity.PersonalInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
    CreditCard getByPersonalInfo(PersonalInfo pi);
}
