package com.application.fumetti.db;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;

@Entity
@Table(name = "serie")
public class Series extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_serie", nullable = false)
    public Long id;

    @Column(name = "nome", length = 30)
    public String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_collana")
    public Collections collection;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_status_serie")
    public SeriesStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_periodicita")
    public SeriesFrequency frequency;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_genere_serie")
    public SeriesGenre genre;

    @Column(name = "note", columnDefinition = "TEXT")
    public String note;
}
