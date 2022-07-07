package com.application.fumetti.db;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;

@Entity
@Table(name = "collane")
public class Collections extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_collana", nullable = false)
    public Long id;

    @Column(name = "nome", length = 30)
    public String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_casa_editrice")
    public Editors editor;
}

