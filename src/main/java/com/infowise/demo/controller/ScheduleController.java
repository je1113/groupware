package com.infowise.demo.controller;

import lombok.RequiredArgsConstructor;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ScheduleController {
    private final Scheduler scheduler;



    @PostMapping("schedule/start")
    public ResponseEntity<String> startScheduler() {
        try {
            scheduler.start();
            return ResponseEntity.ok("Scheduler started successfully.");
        } catch (SchedulerException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to start scheduler.");
        }
    }

    @PostMapping("schedule/stop")
    public ResponseEntity<String> stopScheduler() {
        try {
            scheduler.shutdown();
            return ResponseEntity.ok("Scheduler stopped successfully.");
        } catch (SchedulerException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to stop scheduler.");
        }
    }
}
