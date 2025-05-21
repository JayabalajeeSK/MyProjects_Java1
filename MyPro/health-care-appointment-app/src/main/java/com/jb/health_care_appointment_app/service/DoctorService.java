package com.jb.health_care_appointment_app.service;

import java.util.List;

import com.jb.health_care_appointment_app.entity.Appointment;

public interface DoctorService {
    
    List<Appointment> getAppointmentsForDoctor(Long doctorId);
}