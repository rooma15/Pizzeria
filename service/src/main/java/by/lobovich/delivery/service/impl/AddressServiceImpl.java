package by.lobovich.delivery.service.impl;

import by.lobovich.delivery.entity.Address;
import by.lobovich.delivery.repository.AddressRepository;
import by.lobovich.delivery.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public Address getById(Long id) {
        return addressRepository.getOne(id);
    }

    @Override
    public Address save(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public void delete(Long id) {
        addressRepository.deleteById(id);
    }

    @Override
    public void deleteByAddress(Address address) {
        addressRepository.delete(address);
    }
}
