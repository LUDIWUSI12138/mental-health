package com.mental.service;

import com.mental.entity.Announcement;
import com.mental.mapper.AnnouncementMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AnnouncementService {

    @Autowired
    private AnnouncementMapper announcementMapper;

    public Announcement findById(Integer id) {
        return announcementMapper.findById(id);
    }

    public List<Announcement> findActive() {
        return announcementMapper.findActive();
    }

    public List<Announcement> findAll() {
        return announcementMapper.findAll();
    }

    public boolean save(Announcement announcement) {
        if (announcement.getId() == null) {
            if (announcement.getStatus() == null) announcement.setStatus(1);
            return announcementMapper.insert(announcement) > 0;
        } else {
            return announcementMapper.update(announcement) > 0;
        }
    }

    public boolean delete(Integer id) {
        return announcementMapper.deleteById(id) > 0;
    }
}
