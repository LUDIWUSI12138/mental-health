package com.mental.service;

import com.mental.entity.Warning;
import com.mental.mapper.WarningMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarningService {

    @Autowired
    private WarningMapper warningMapper;

    public List<Warning> findAll() {
        return warningMapper.findAll();
    }

    public List<Warning> findByUserId(Integer userId) {
        return warningMapper.findByUserId(userId);
    }

    public List<Warning> findPending() {
        return warningMapper.findByStatus("pending");
    }

    public Warning findById(Integer id) {
        return warningMapper.findById(id);
    }

    public boolean handle(Integer warningId, Integer handlerId, String status) {
        return warningMapper.updateStatus(warningId, status, handlerId) > 0;
    }

    public int countPending() {
        return warningMapper.countPending();
    }
}
