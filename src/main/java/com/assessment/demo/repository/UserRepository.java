package com.assessment.demo.repository;

import javax.transaction.Transactional;

import com.assessment.demo.model.UserInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

public interface UserRepository extends JpaRepository<UserInfo, Long> {
    
    @Transactional
    @Modifying
    public void deleteById(Long id);
}
