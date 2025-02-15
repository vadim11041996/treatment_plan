package com.example.treatment_plan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.transaction.annotation.Transactional;

@Service
class TreatmentTaskScheduler {
    private static final Logger logger = Logger.getLogger(TreatmentTaskScheduler.class.getName());

    @Autowired
    private TreatmentPlanRepository treatmentPlanRepository;

    @Autowired
    private TreatmentTaskRepository treatmentTaskRepository;

    @Scheduled(fixedRate = 60000) // Runs every minute
    @Transactional
    public void generateTreatmentTasks() {
        List<TreatmentPlan> plans = treatmentPlanRepository.findAll();
        for (TreatmentPlan plan : plans) {
            List<LocalDateTime> taskTimes = calculateTaskTimes(plan);
            for (LocalDateTime time : taskTimes) {
                if (!treatmentTaskRepository.existsByPatientReferenceAndTreatmentActionAndStartTime(plan.getPatientReference(), plan.getTreatmentAction(), time)) {
                    TreatmentTask task = new TreatmentTask();
                    task.setPatientReference(plan.getPatientReference());
                    task.setTreatmentAction(plan.getTreatmentAction());
//                    System.out.println("Checking plans at: " + LocalDateTime.now());
//                    System.out.println("Total plans found: " + plans.size());
                    task.setStartTime(time);
                    task.setStatus(TreatmentTask.TaskStatus.ACTIVE);
                    treatmentTaskRepository.save(task);
                }
            }
        }
    }

    private List<LocalDateTime> calculateTaskTimes(TreatmentPlan plan) {
        List<LocalDateTime> times = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        if (plan.getRecurrencePattern() != null) {
            String[] patterns = plan.getRecurrencePattern().split(",");
            for (String pattern : patterns) {
                try {
                    if (pattern.equalsIgnoreCase("Every minute")) {
                        times.add(now.plusMinutes(1));
                    } else if (pattern.matches("(?i)(Monday|Tuesday|Wednesday|Thursday|Friday|Saturday|Sunday)\\s+\\d{2}:\\d{2}")) {
                        String[] parts = pattern.trim().split("\\s+"); // Trim and split by any whitespace
                        DayOfWeek dayOfWeek = DayOfWeek.valueOf(parts[0].toUpperCase());
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                        LocalTime localTime = LocalTime.parse(parts[1], formatter);
                        LocalDateTime taskTime = LocalDateTime.of(now.toLocalDate(), localTime);

                        if (now.getDayOfWeek() == dayOfWeek && taskTime.isAfter(plan.getStartTime()) && (plan.getEndTime() == null || taskTime.isBefore(plan.getEndTime()))) {
                            times.add(taskTime);
                        }
                    } else {
                        logger.severe("Invalid recurrence pattern: " + pattern);
                    }
                } catch (Exception e) {
                    logger.severe("Error parsing recurrence pattern: " + pattern + " - " + e.getMessage());
                }
            }
        }
        return times;
    }
}
