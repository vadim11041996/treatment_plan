package com.example.treatment_plan;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "TREATMENT_PLAN")
class TreatmentPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "patient_reference", nullable = false)
    private String patientReference;

    @Column(name = "treatment_action", nullable = false)
    private String treatmentAction;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "recurrence_pattern")
    private String recurrencePattern;
}