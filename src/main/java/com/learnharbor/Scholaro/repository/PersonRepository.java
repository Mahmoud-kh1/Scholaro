package com.learnharbor.Scholaro.repository;

import com.learnharbor.Scholaro.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Person readByEmail(String email);
}
