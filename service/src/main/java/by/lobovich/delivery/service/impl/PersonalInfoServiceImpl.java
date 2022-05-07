package by.lobovich.delivery.service.impl;

import by.lobovich.delivery.entity.PersonalInfo;
import by.lobovich.delivery.entity.User;
import by.lobovich.delivery.repository.PersonalInfoRepository;
import by.lobovich.delivery.service.PersonalInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonalInfoServiceImpl implements PersonalInfoService {

    private final PersonalInfoRepository personalInfoRepository;

    @Autowired
    public PersonalInfoServiceImpl(PersonalInfoRepository personalInfoRepository) {
        this.personalInfoRepository = personalInfoRepository;
    }

    @Override
    public PersonalInfo getById(Long id) {
        return personalInfoRepository.getOne(id);
    }

    @Override
    public PersonalInfo save(PersonalInfo personalInfo) {
        return personalInfoRepository.save(personalInfo);
    }

    @Override
    public void delete(Long id) {
        personalInfoRepository.deleteById(id);
    }

    @Override
    public PersonalInfo getByUser(User user) {
        return personalInfoRepository.findByUser(user);
    }
}
