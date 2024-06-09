package com.addrsharingtool.addrsharingtool.dao.mysql.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity
@Table(name = "user_address_mapping")
public class UserAddressMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "user_id", updatable = false)
    private long userId;

    @Column(name = "address_line1")
    private String addressLine1; //flat No./house No., floor No., Building Name
    
    @Column(name = "address_line2")
    private String addressLine2; //street Address

    @Column(name = "landmark")
    private String landmark;

    @Column(name = "city")
    private String city;

    @Column(name = "zipCode")
    private String zipCode;

    @Column(name = "state")
    private String state;

    @Column(name = "country")
    private String country;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "address_unique_code")
    private String addressUniqueCode;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
}