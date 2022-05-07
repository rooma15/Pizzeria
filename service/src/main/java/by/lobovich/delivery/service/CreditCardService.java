package by.lobovich.delivery.service;

import by.lobovich.delivery.entity.CreditCard;

public interface CreditCardService {

    CreditCard getById(Long id);

    CreditCard save(CreditCard creditCard);

    void delete(Long id);

    void deleteByCreditCard(CreditCard creditCard);
}
