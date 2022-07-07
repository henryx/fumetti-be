package com.application.fumetti.db;

import javax.persistence.*;

@Entity
@Table(name = "periodicita")
public class SeriesFrequency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_periodicita", nullable = false)
    public Long id;

    @Column(name = "descrizione")
    public String description;
}
