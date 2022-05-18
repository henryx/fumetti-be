package com.application.fumetti.db;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "valuta", schema = "public")
public class Currencies extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_valuta", nullable = false)
    public Long id;

    @Column(name = "nome", length = 20)
    public String name;

    @Column(name = "simbolo", length = 1)
    public String symbol;

    @Column(name = "valore_lire", precision = 6, scale = 2)
    public BigDecimal valueLire;

    @Column(name = "valore_euro", precision = 6, scale = 2)
    public BigDecimal valueEuro;
}