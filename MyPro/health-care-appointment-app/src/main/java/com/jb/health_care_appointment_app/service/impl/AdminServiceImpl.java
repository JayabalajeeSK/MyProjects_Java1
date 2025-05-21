package com.jb.health_care_appointment_app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jb.health_care_appointment_app.entity.Appointment;
import com.jb.health_care_appointment_app.entity.Doctor;
import com.jb.health_care_appointment_app.repository.AppointmentRepository;
import com.jb.health_care_appointment_app.repository.DoctorRepository;
import com.jb.health_care_appointment_app.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService{

    @Autowired private DoctorRepository doctorRepo;
    @Autowired private AppointmentRepository appointmentRepo;

    public List<Appointment> getAllAppointments() {
        return appointmentRepo.findAll();
    }

    public List<Appointment> getAppointmentsByDoctorId(Long doctorId) {
    return appointmentRepo.findByDoctorId(doctorId);
}

    //////////////////////////////////////////////////////////////////

    public Doctor addDoctor(Doctor doctor) {
        return doctorRepo.save(doctor);
    }

    public Doctor updateDoctor(Long id, Doctor updated) {
        Doctor doctor = doctorRepo.findById(id).orElseThrow();
        doctor.setName(updated.getName());
        doctor.setSpecialization(updated.getSpecialization());
        return doctorRepo.save(doctor);
    }

    public void deleteDoctor(Long id) {
        doctorRepo.deleteById(id);
    }

}
