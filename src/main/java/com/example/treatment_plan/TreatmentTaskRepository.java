package com.example.treatment_plan;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
interface TreatmentTaskRepository extends JpaRepository<TreatmentTask, Long> {
    List<TreatmentTask> findByStatus(String status);
    boolean existsByPatientReferenceAndTreatmentActionAndStartTime(String patientReference, String treatmentAction, LocalDateTime startTime);

}

