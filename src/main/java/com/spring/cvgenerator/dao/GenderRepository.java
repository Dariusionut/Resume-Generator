package com.spring.cvgenerator.dao;

import com.spring.cvgenerator.model.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GenderRepository extends JpaRepository<Gender, Long> {

    @Query("SELECT g FROM Gender g WHERE g.id = ?1")
    Gender find(@Param("id") Long id);
}
