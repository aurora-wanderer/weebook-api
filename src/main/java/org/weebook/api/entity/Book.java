package org.weebook.api.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.weebook.api.entity.audit.DateAudit;
import org.weebook.api.entity.embed.BookDescribe;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "books")
public class Book extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", length = Integer.MAX_VALUE)
    private String name;

    @Column(name = "price", precision = 10, scale = 0)
    private BigDecimal price;

    @Column(name = "discount")
    private Integer discount;

    @Column(name = "episode", length = Integer.MAX_VALUE)
    private String episode;

    @Column(name = "gallery")
    private List<String> gallery;

    @Embedded
    private BookDescribe bookDescribe;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "\"skuCode\"")
    @ToString.Exclude
    private Inventory skuCode;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "\"categoryId\"")
    @ToString.Exclude
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"seriesId\"")
    @ToString.Exclude
    private Series series;

    @OneToMany(mappedBy = "book")
    @ToString.Exclude
    @Builder.Default
    private Set<Review> reviews = new LinkedHashSet<>();

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    @ToString.Exclude
    @Builder.Default
    private Set<Genre> genres = new LinkedHashSet<>();

    @OneToMany(mappedBy = "book")
    @ToString.Exclude
    @Builder.Default
    private Set<OrderItem> orderItems = new LinkedHashSet<>();

    @OneToMany(mappedBy = "book")
    @ToString.Exclude
    @Builder.Default
    private Set<Favorite> favorites = new LinkedHashSet<>();
}