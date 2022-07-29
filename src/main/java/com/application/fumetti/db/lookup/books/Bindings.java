package com.application.fumetti.db.lookup.books;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;

@Entity
@Table(name = "rilegatura")
public class Bindings extends PanacheEntityBase {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_rilegatura")
    public Long id;

    @Column(name = "descrizione")
    public String description;
}
