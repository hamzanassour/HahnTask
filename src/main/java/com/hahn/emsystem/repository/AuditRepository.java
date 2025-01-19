package com.hahn.emsystem.repository;

import com.hahn.emsystem.entity.Audit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditRepository extends JpaRepository<Audit, Long> {
}
