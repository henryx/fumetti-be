package com.application.fumetti.db;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;

@Entity
@Table(name = "genere_serie")
public class SeriesGenre extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_genere_serie", nullable = false)
    public Long id;

    @Column(name = "descrizione")
    public String description;
}
