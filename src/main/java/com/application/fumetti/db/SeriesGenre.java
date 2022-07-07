package com.application.fumetti.db;

import javax.persistence.*;

@Entity
@Table(name = "genere_serie")
public class SeriesGenre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_genere_serie", nullable = false)
    public Long id;

    @Column(name = "descrizione")
    public String description;
}
