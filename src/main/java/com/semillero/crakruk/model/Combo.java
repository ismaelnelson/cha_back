package com.semillero.crakruk.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "combos")
@SQLDelete(sql = "UPDATE combos SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Combo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ElementCollection(targetClass=String.class)
    private List<String> description;
    private String price;
    private String link;
    @OneToOne
    private Image image;


    @Column(name = "deleted", nullable = false, columnDefinition = "boolean default false")
    private Boolean deleted = Boolean.FALSE;

    @CreationTimestamp
    private LocalDate created;

    @UpdateTimestamp
    private LocalDate updated;
}
