package com.application.fumetti.db.lookup.series;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;

@Entity
@Table(name = "status_serie")
public class Status extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_status_serie", nullable = false)
    public Long id;

    @Column(name = "descrizione")
    public String description;
}
