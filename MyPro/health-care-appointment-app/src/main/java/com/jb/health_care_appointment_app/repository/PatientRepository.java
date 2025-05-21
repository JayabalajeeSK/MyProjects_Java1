package com.jb.health_care_appointment_app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jb.health_care_appointment_app.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByEmail(String email);
}