package com.application.fumetti.db;

import javax.persistence.*;

@Entity
@Table(name = "status_serie")
public class SeriesStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_status_serie", nullable = false)
    public Long id;

    @Column(name = "descrizione")
    public String description;
}
