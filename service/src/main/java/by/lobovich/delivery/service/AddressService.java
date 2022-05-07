package by.lobovich.delivery.service;

import by.lobovich.delivery.entity.Address;

public interface AddressService {

    Address getById(Long id);

    Address save(Address address);

    void delete(Long id);

    void deleteByAddress(Address address);
}
