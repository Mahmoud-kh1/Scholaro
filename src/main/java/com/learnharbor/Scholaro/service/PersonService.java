package com.learnharbor.Scholaro.service;

import com.learnharbor.Scholaro.constants.ScholaroConstants;
import com.learnharbor.Scholaro.model.Person;
import com.learnharbor.Scholaro.model.Roles;
import com.learnharbor.Scholaro.repository.PersonRepository;
import com.learnharbor.Scholaro.repository.RolesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private RolesRepository rolesRepository;


    public boolean createNewPerson(Person person) {
        boolean isSaved = false;
        Roles role = rolesRepository.getByRoleName(ScholaroConstants.STUDENT_ROLE);
        person.setRoles(role);
        personRepository.save(person);
        if(null != person && person.getPersonId() > 0){
            isSaved = true;
        }
        return isSaved;
    }


}
