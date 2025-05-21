package com.jb.health_care_appointment_app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jb.health_care_appointment_app.entity.Appointment;
import com.jb.health_care_appointment_app.repository.AppointmentRepository;

@Service
public class DoctorServiceImpl {

    @Autowired private AppointmentRepository appointmentRepo;

    public List<Appointment> getAppointmentsForDoctor(Long doctorId) {
        return appointmentRepo.findByDoctorId(doctorId);
    }
}
