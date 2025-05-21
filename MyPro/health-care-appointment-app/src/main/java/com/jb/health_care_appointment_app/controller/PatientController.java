package com.jb.health_care_appointment_app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jb.health_care_appointment_app.entity.Appointment;
import com.jb.health_care_appointment_app.entity.Patient;
import com.jb.health_care_appointment_app.service.impl.PatientServiceImpl;

@RestController
@RequestMapping("/api/patient")
public class PatientController {

    @Autowired
    private PatientServiceImpl patientService;

    @PostMapping("/register") //
    public ResponseEntity<Patient> register(@RequestBody Patient patient) {
        return ResponseEntity.ok(patientService.register(patient));
    }

    @PostMapping("/book") //
    public Appointment book(@RequestBody Appointment appointment) {
        return patientService.bookAppointment(
            appointment.getPatient().getId(),
            appointment.getDoctor().getId(),
            appointment.getAppointmentDate().toString()
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable Long id, @RequestBody Patient updatedPatient) {
        Patient patient = patientService.updatePatient(id, updatedPatient);
        return ResponseEntity.ok(patient);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.ok("Deleted");
    }

    

    @GetMapping("/history/{patientId}") //
    public List<Appointment> history(@PathVariable Long patientId) {
        return patientService.getAppointmentHistory(patientId);
    }
}
