package com.mooddetector.Mood_Detecor;

import java.time.LocalDateTime;

public class MoodEntry {
    private String text;
    private MoodResult result;
    private LocalDateTime timestamp;
    
    public MoodEntry(String text, MoodResult result, LocalDateTime timestamp) {
        this.text = text;
        this.result = result;
        this.timestamp = timestamp;
    }
    
    public String getText() {
        return text;
    }
    
    public MoodResult getResult() {
        return result;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}

