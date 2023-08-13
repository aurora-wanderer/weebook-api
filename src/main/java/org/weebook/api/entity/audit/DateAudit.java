package org.weebook.api.entity.audit;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class DateAudit {
    @CreatedDate
    @Column(name = "\"createdAt\"")
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "\"updatedAt\"")
    private Instant updatedAt;
}