package com.mental.service;

import com.mental.entity.MoodDiary;
import com.mental.mapper.MoodDiaryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MoodDiaryService {

    @Autowired
    private MoodDiaryMapper moodDiaryMapper;

    public MoodDiary findById(Integer id) {
        return moodDiaryMapper.findById(id);
    }

    public List<MoodDiary> findByUserId(Integer userId) {
        return moodDiaryMapper.findByUserId(userId);
    }

    public List<MoodDiary> findAll() {
        return moodDiaryMapper.findAll();
    }

    public boolean save(MoodDiary diary) {
        // 自动设置心情emoji
        if (diary.getMoodEmoji() == null || diary.getMoodEmoji().isEmpty()) {
            switch (diary.getMoodScore()) {
                case 1: diary.setMoodEmoji("😢"); break;
                case 2: diary.setMoodEmoji("😞"); break;
                case 3: diary.setMoodEmoji("😐"); break;
                case 4: diary.setMoodEmoji("😊"); break;
                case 5: diary.setMoodEmoji("😄"); break;
                default: diary.setMoodEmoji("😐");
            }
        }
        return moodDiaryMapper.insert(diary) > 0;
    }

    public boolean delete(Integer id) {
        return moodDiaryMapper.deleteById(id) > 0;
    }

    public int countByUserId(Integer userId) {
        return moodDiaryMapper.countByUserId(userId);
    }
}
