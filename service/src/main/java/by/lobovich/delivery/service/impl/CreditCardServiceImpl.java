package by.lobovich.delivery.service.impl;

import by.lobovich.delivery.entity.CreditCard;
import by.lobovich.delivery.entity.PersonalInfo;
import by.lobovich.delivery.repository.CreditCardRepository;
import by.lobovich.delivery.service.CreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreditCardServiceImpl implements CreditCardService {

    private final CreditCardRepository creditCardRepository;

    @Autowired
    public CreditCardServiceImpl(CreditCardRepository creditCardRepository) {
        this.creditCardRepository = creditCardRepository;
    }

    @Override
    public CreditCard getById(Long id) {
        return creditCardRepository.getOne(id);
    }

    @Override
    public CreditCard save(CreditCard creditCard) {
        return creditCardRepository.save(creditCard);
    }

    @Override
    public void delete(Long id) {
        creditCardRepository.deleteById(id);
    }

    @Override
    public void deleteByCreditCard(CreditCard creditCard) {
        creditCardRepository.delete(creditCard);
    }

    public CreditCard getByPersonalInfo(PersonalInfo pi){
        return creditCardRepository.getByPersonalInfo(pi);
    }
}
