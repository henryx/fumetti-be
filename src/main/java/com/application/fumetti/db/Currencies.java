package com.application.fumetti.db;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "valuta")
public class Currencies extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_valuta", nullable = false)
    public Long id;

    @Column(name = "nome", length = 30)
    public String name;

    @Column(name = "simbolo", length = 1)
    public String symbol;

    @Column(name = "valore_lire", precision = 12, scale = 7)
    public BigDecimal valueLire;

    @Column(name = "valore_euro", precision =12, scale = 7)
    public BigDecimal valueEuro;
}
