INSERT INTO TREATMENT_PLAN (patient_reference, treatment_action, start_time, end_time, recurrence_pattern)
VALUES ('Patient123', 'ActionA', '2025-02-15 08:00:00', '2025-03-01 20:00:00', 'Monday 08:00,Wednesday 16:00');

INSERT INTO TREATMENT_TASK (patient_reference, treatment_action, start_time, status)
VALUES ('Patient123', 'ActionA', '2025-02-15 08:00:00', 'ACTIVE');

INSERT INTO TREATMENT_PLAN (patient_reference, treatment_action, start_time, end_time, recurrence_pattern)
VALUES ('PatientTest', 'ActionA', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + INTERVAL '10' MINUTE, 'Every minute');
