package com.learnharbor.Scholaro.repository;

import com.learnharbor.Scholaro.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface RolesRepository extends JpaRepository<Roles, Integer> {

    public Roles getByRoleName(String roleName);
    
}
