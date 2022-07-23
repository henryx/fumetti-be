package com.application.fumetti.db;

import com.application.fumetti.db.lookup.books.Bindings;
import com.application.fumetti.db.lookup.books.Preservations;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "albi")
public class Books extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_albo", nullable = false)
    public Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_serie")
    public Series series;

    @Column(name = "numero_albo")
    public Long number;

    @Column(name = "data_pubblicazione")
    public LocalDate published;

    @Column(name = "prezzo_copertina")
    public BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_valuta")
    public Currencies currency;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_rilegatura")
    public Bindings binding;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_stato_conservazione")
    public Preservations preservation;

    @Column(name = "note")
    public String note;
}
