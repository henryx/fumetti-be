package com.application.fumetti.db;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;

@Entity
@Table(name = "case_editrici")
public class Editors extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_casa_editrice", nullable = false)
    public Long id;

    @Column(name = "nome", length = 45)
    public String name;

    @Column(name = "sede", length = 45)
    public String hq;

    @Column(name = "sito_web", length = 100)
    public String website;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_nazione")
    public Nations nation;
}