package org.weebook.api.entity;

import jakarta.persistence.*;
import lombok.*;
import org.weebook.api.entity.audit.DateAudit;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "users")
public class User extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name", length = Integer.MAX_VALUE)
    private String name;

    @Column(name = "email", length = Integer.MAX_VALUE, unique = true)
    private String email;

    @Column(name = "\"emailVerified\"")
    private Instant emailVerified;

    @Column(name = "image", length = Integer.MAX_VALUE)
    private String image;

    @Column(name = "password", length = Integer.MAX_VALUE)
    private String password;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    @Builder.Default
    private Set<Review> reviews = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    @Builder.Default
    private Set<Account> accounts = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    @Builder.Default
    private Set<Role> roles = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    @Builder.Default
    private Set<Address> addresses = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    @Builder.Default
    private Set<Order> orders = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    @Builder.Default
    private Set<Favorite> favorites = new LinkedHashSet<>();
}