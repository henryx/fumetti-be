package com.application.fumetti.db;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;

@Entity
@Table(name = "nazioni")
public class Nations extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_nazione", nullable = false)
    public Long id;

    @Column(name = "nome", length = 20)
    public String name;

    @Column(name = "sigla", length = 4)
    public String sign;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_valuta")
    public Currencies currency;
}