package com.application.fumetti.db;

import com.application.fumetti.db.lookup.series.Frequency;
import com.application.fumetti.db.lookup.series.Genre;
import com.application.fumetti.db.lookup.series.Status;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;

@Entity
@Table(name = "serie")
public class Series extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_serie", nullable = false)
    public Long id;

    @Column(name = "nome", length = 30)
    public String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_status_serie")
    public Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_periodicita")
    public Frequency frequency;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_genere_serie")
    public Genre genre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_casa_editrice")
    public Editors editor;

    @Column(name = "note", columnDefinition = "TEXT")
    public String note;
}
