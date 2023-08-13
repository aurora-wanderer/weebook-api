package org.weebook.api.entity;

import jakarta.persistence.*;
import lombok.*;
import org.weebook.api.entity.audit.DateAudit;

import java.util.LinkedHashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "categories")
public class Category extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", length = Integer.MAX_VALUE)
    private String name;

    @Column(name = "\"subCategory\"", length = Integer.MAX_VALUE)
    private String subCategory;

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    @OneToMany(mappedBy = "category")
    @ToString.Exclude
    @Builder.Default
    private Set<Book> books = new LinkedHashSet<>();
}