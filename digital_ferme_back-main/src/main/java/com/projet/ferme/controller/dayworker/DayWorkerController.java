package com.projet.ferme.controller.dayworker;

import com.projet.ferme.entity.dayworker.DayWorker;
import com.projet.ferme.service.dayworker.DayworkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//import org.springframework.web.bind.annotation.;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/dayworker")
public class DayWorkerController {

    @Autowired
    private DayworkerService dayworkerService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> addDayWorker(@RequestBody DayWorker dayWorker) {
        Map<String, Object> response = dayworkerService.addDayWorker(dayWorker);
        HttpStatus status = (Boolean) response.get("success") ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(response, status);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateDayWorker( @RequestBody DayWorker dayWorker) {

        Map<String, Object> response = dayworkerService.updateDayWorker(dayWorker);
        HttpStatus status = (Boolean) response.get("success") ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(response, status);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> findAllDayWorker() {
        Map<String, Object> response = dayworkerService.findAllDayWorker();
        HttpStatus status = (Boolean) response.get("success") ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(response, status);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> findDayWorkerById(@PathVariable Long id) {
        Map<String, Object> response = dayworkerService.findDayWorkerById(id);
        HttpStatus status = (Boolean) response.get("success") ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(response, status);
    }

}
