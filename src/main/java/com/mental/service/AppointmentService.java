package com.mental.service;

import com.mental.entity.Appointment;
import com.mental.mapper.AppointmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentMapper appointmentMapper;

    public Appointment findById(Integer id) {
        return appointmentMapper.findById(id);
    }

    public List<Appointment> findByUserId(Integer userId) {
        return appointmentMapper.findByUserId(userId);
    }

    public List<Appointment> findAll() {
        return appointmentMapper.findAll();
    }

    public List<Appointment> findPending() {
        return appointmentMapper.findByStatus("pending");
    }

    public boolean save(Appointment appointment) {
        if (appointment.getStatus() == null) {
            appointment.setStatus("pending");
        }
        return appointmentMapper.insert(appointment) > 0;
    }

    public boolean updateStatus(Integer id, String status, String adminReply) {
        return appointmentMapper.updateStatus(id, status, adminReply) > 0;
    }

    public int countPending() {
        return appointmentMapper.countPending();
    }
}
