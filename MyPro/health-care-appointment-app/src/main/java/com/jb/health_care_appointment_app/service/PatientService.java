package com.jb.health_care_appointment_app.service;

import com.jb.health_care_appointment_app.entity.Appointment;
import com.jb.health_care_appointment_app.entity.Patient;

import java.util.List;

public interface PatientService {
    
    List<Appointment> getAppointmentHistory(Long patientId);

    Patient register(Patient patient);
    Appointment bookAppointment(Long patientId, Long doctorId, String date);
    
    Patient updatePatient(Long id, Patient updatedPatient);
    void deletePatient(Long id);
}
