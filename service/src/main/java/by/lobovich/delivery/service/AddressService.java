package by.lobovich.delivery.service;

import by.lobovich.delivery.entity.Address;
import by.lobovich.delivery.entity.PersonalInfo;

public interface AddressService {

    Address getById(Long id);

    Address save(Address address);

    void delete(Long id);

    void deleteByAddress(Address address);

    Address getByPersonalInfo(PersonalInfo pi);
}
