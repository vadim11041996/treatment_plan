package com.example.treatment_plan;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/treatment")
class TreatmentController {
    @Autowired
    private TreatmentTaskRepository treatmentTaskRepository;

    @Autowired
    private TreatmentPlanRepository treatmentPlanRepository;

    @GetMapping("/tasks")
    public List<TreatmentTask> getAllTasks() {
        return treatmentTaskRepository.findAll();
    }

    @GetMapping("/plans")
    public List<TreatmentPlan> getAllPlans() {
        return treatmentPlanRepository.findAll();
    }

    @PutMapping("/tasks/{taskId}/complete")
    public @ResponseBody String completeTask(@PathVariable Long taskId) {
        Optional<TreatmentTask> optionalTask = treatmentTaskRepository.findById(taskId);
        if (optionalTask.isPresent()) {
            TreatmentTask task = optionalTask.get();
            task.setStatus(TreatmentTask.TaskStatus.COMPLETED);
            treatmentTaskRepository.save(task);
            return "Task marked as Completed.";
        } else {
            return "Task not found.";
        }
    }
}

