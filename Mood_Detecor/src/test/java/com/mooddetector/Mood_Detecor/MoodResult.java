package com.mooddetector.Mood_Detecor;

import java.util.Date;

public class MoodResult {
    private String mood;
    private double confidence;
    
    public MoodResult() {}
    
    public MoodResult(String mood, double confidence) {
        this.mood = mood;
        this.confidence = confidence;
    }
    
    public MoodResult(String string, double d, String string2, Date date) {
		// TODO Auto-generated constructor stub
	}

	// Getters and setters
    public String getMood() { return mood; }
    public void setMood(String mood) { this.mood = mood; }
    
    public double getConfidence() { return confidence; }
    public void setConfidence(double confidence) { this.confidence = confidence; }
}
