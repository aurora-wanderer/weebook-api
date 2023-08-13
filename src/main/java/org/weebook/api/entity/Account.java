package org.weebook.api.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.weebook.api.entity.audit.DateAudit;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "accounts")
public class Account extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "type", length = Integer.MAX_VALUE)
    private String type;

    @Column(name = "provider", length = Integer.MAX_VALUE)
    private String provider;

    @Column(name = "scope", length = Integer.MAX_VALUE)
    private String scope;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "\"userId\"")
    @ToString.Exclude
    private User user;

}