package by.lobovich.delivery.service;

import by.lobovich.delivery.entity.CreditCard;
import by.lobovich.delivery.entity.PersonalInfo;

public interface CreditCardService {

    CreditCard getById(Long id);

    CreditCard save(CreditCard creditCard);

    void delete(Long id);

    void deleteByCreditCard(CreditCard creditCard);

    CreditCard getByPersonalInfo(PersonalInfo pi);
}
