package com.addrsharingtool.addrsharingtool.dao.mysql.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.addrsharingtool.addrsharingtool.dao.mysql.entity.UserAddressMapping;

@Repository
public interface UserAddressMappingRepository extends JpaRepository<UserAddressMapping, Long> {

    Optional<UserAddressMapping> findByAddressUniqueCode(String addressUniqueCode);
    
    UserAddressMapping findByUserId(Long userId);

}