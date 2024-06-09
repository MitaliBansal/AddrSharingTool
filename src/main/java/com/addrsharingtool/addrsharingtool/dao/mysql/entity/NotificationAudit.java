package com.addrsharingtool.addrsharingtool.dao.mysql.entity;

import com.addrsharingtool.addrsharingtool.constant.NotificationChannel;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity
@Table(name = "notification_audit")
public class NotificationAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "notified_user_id")
    private Long notifiedUserId;

    @Column(name = "notification_accepted")
    private Boolean notificationAccepted;

    @Enumerated(EnumType.STRING)
    @Column(name = "notification_acceptance_channel")
    private NotificationChannel notificationAcceptanceChannel;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
}