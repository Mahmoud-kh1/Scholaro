package com.learnharbor.Scholaro.repository;

import com.learnharbor.Scholaro.model.ScholaroClasses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRepository extends JpaRepository<ScholaroClasses,Integer> {

}
