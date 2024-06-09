package com.addrsharingtool.addrsharingtool.dao.mysql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.addrsharingtool.addrsharingtool.dao.mysql.entity.NotificationAudit;

@Repository
public interface NotificationAuditRepository extends JpaRepository<NotificationAudit, Long> {
}