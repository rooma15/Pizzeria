package by.lobovich.delivery.service;

import by.lobovich.delivery.entity.PersonalInfo;
import by.lobovich.delivery.entity.User;

public interface PersonalInfoService {
    PersonalInfo getById(Long id);

    PersonalInfo save(PersonalInfo personalInfo);

    void delete(Long id);

    PersonalInfo getByUser(User user);
}
