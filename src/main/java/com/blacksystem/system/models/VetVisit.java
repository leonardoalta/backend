package com.blacksystem.system.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "vet_visits")
@Getter
@Setter
public class VetVisit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String visitDate;
    private String reason;

    @Column(length = 1000)
    private String diagnosis;

    @Column(length = 1000)
    private String treatment;

    @Column(length = 2000)
    private String notes;

    private String vetName;
    private String clinic;

    private boolean wantsReminders;
    private String nextVisitDate;

    private String photoUrl;

    // 🔗 RELACIÓN CORRECTA
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;
}
