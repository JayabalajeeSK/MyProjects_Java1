package com.jb.health_care_appointment_app.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jb.health_care_appointment_app.entity.Appointment;
import com.jb.health_care_appointment_app.entity.Patient;
import com.jb.health_care_appointment_app.repository.AppointmentRepository;
import com.jb.health_care_appointment_app.repository.DoctorRepository;
import com.jb.health_care_appointment_app.repository.PatientRepository;

@Service
public class PatientServiceImpl {

    @Autowired private PatientRepository patientRepo;
    @Autowired private DoctorRepository doctorRepo;
    @Autowired private AppointmentRepository appointmentRepo;

    public List<Appointment> getAppointmentHistory(Long patientId) {
        return appointmentRepo.findByPatientId(patientId);
    }

///////////////////////////////////////////////////////

    public Patient register(Patient patient) {
        return patientRepo.save(patient);
    }

    public Appointment bookAppointment(Long patientId, Long doctorId, String date) {
        Appointment appointment = new Appointment();

        appointment.setAppointmentDate(LocalDate.parse(date));
        appointment.setStatus("BOOKED");
        appointment.setPatient(patientRepo.findById(patientId).orElseThrow());
        appointment.setDoctor(doctorRepo.findById(doctorId).orElseThrow());

        appointment = appointmentRepo.save(appointment);

        // Optional: reload with full patient & doctor (if lazy loading issue)
        appointment.setPatient(appointment.getPatient());
        appointment.setDoctor(appointment.getDoctor());

        return appointment;
    }


    public Patient updatePatient(Long id, Patient updatedPatient) {
        Patient existing = patientRepo.findById(id).orElseThrow();
        existing.setName(updatedPatient.getName());
        existing.setEmail(updatedPatient.getEmail());
        existing.setPhone(updatedPatient.getPhone());
        existing.setPassword(updatedPatient.getPassword());
        return patientRepo.save(existing);
    }

    
    public void deletePatient(Long id) {
        patientRepo.deleteById(id);
    }



}
