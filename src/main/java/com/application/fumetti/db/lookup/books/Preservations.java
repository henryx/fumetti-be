package com.application.fumetti.db.lookup.books;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;

@Entity
@Table(name = "stato_conservazione")
public class Preservations extends PanacheEntityBase {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_stato_conservazione")
    public Long id;

    @Column(name = "descrizione")
    public String description;
}
