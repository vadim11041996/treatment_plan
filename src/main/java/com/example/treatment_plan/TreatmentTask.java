package com.example.treatment_plan;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "TREATMENT_TASK")
class TreatmentTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "patient_reference", nullable = false)
    private String patientReference;

    @Column(name = "treatment_action", nullable = false)
    private String treatmentAction;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TaskStatus status;

    public enum TaskStatus {
        ACTIVE,
        COMPLETED
    }
}