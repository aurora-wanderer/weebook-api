package org.weebook.api.entity;

import jakarta.persistence.*;
import lombok.*;
import org.weebook.api.entity.audit.DateAudit;

import java.util.LinkedList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "series")
public class Series extends DateAudit {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = Integer.MAX_VALUE)
    private String name;

    @Column(name = "subscribes")
    private Long subscribes;

    @Column(name = "\"newestEpisode\"")
    private Integer newestEpisode;

    @OneToMany(mappedBy = "series")
    @ToString.Exclude
    @Builder.Default
    private List<Book> books = new LinkedList<>();

}