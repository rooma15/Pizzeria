package by.lobovich.delivery.repository;

import by.lobovich.delivery.entity.PersonalInfo;
import by.lobovich.delivery.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface PersonalInfoRepository extends JpaRepository<PersonalInfo, Long> {

    PersonalInfo findByUser(User user);
}
