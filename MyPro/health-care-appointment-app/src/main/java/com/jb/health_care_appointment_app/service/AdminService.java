package com.jb.health_care_appointment_app.service;


import java.util.List;
import com.jb.health_care_appointment_app.entity.Appointment;
import com.jb.health_care_appointment_app.entity.Doctor;

public interface AdminService {
    
    List<Appointment> getAllAppointments();

    Doctor addDoctor(Doctor doctor);
    Doctor updateDoctor(Long id, Doctor updated);
    void deleteDoctor(Long id);
    
}
