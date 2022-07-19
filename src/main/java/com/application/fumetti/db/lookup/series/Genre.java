package com.application.fumetti.db.lookup.series;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;

@Entity
@Table(name = "genere_serie")
public class Genre extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_genere_serie", nullable = false)
    public Long id;

    @Column(name = "descrizione")
    public String description;
}
