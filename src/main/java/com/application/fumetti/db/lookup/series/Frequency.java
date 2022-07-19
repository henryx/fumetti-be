package com.application.fumetti.db.lookup.series;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;

@Entity
@Table(name = "periodicita")
public class Frequency extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_periodicita", nullable = false)
    public Long id;

    @Column(name = "descrizione")
    public String description;
}
