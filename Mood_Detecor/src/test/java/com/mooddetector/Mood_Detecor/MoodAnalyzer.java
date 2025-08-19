package com.mooddetector.Mood_Detecor;

import java.util.*;

public class MoodAnalyzer {
    private Map<String, Set<String>> moodKeywords;
    
    public MoodAnalyzer() {
        initializeMoodKeywords();
    }
    
    private void initializeMoodKeywords() {
        moodKeywords = new HashMap<>();
        
        // Happy keywords
        Set<String> happyWords = new HashSet<>(Arrays.asList(
            "happy", "joy", "joyful", "excited", "thrilled", "delighted", "cheerful",
            "glad", "pleased", "content", "satisfied", "elated", "ecstatic", "blissful",
            "wonderful", "amazing", "fantastic", "great", "awesome", "brilliant",
            "love", "adore", "enjoy", "celebrate", "smile", "laugh", "fun"
        ));
        moodKeywords.put("happy", happyWords);
        
        // Sad keywords
        Set<String> sadWords = new HashSet<>(Arrays.asList(
            "sad", "depressed", "unhappy", "miserable", "gloomy", "melancholy",
            "sorrowful", "dejected", "downhearted", "blue", "down", "low",
            "cry", "tears", "weep", "mourn", "grieve", "hurt", "pain",
            "lonely", "empty", "hopeless", "despair", "disappointed"
        ));
        moodKeywords.put("sad", sadWords);
        
        // Angry keywords
        Set<String> angryWords = new HashSet<>(Arrays.asList(
            "angry", "mad", "furious", "rage", "enraged", "livid", "irate",
            "annoyed", "irritated", "frustrated", "upset", "outraged",
            "hate", "despise", "loathe", "disgusted", "fed up", "sick of",
            "stupid", "idiot", "damn", "hell", "terrible", "awful"
        ));
        moodKeywords.put("angry", angryWords);
        
        // Anxious keywords
        Set<String> anxiousWords = new HashSet<>(Arrays.asList(
            "anxious", "worried", "nervous", "stressed", "tense", "uneasy",
            "concerned", "troubled", "restless", "agitated", "panic", "fear",
            "scared", "afraid", "terrified", "overwhelmed", "pressure",
            "doubt", "uncertain", "insecure", "confused", "lost"
        ));
        moodKeywords.put("anxious", anxiousWords);
        
        // Excited keywords
        Set<String> excitedWords = new HashSet<>(Arrays.asList(
            "excited", "thrilled", "pumped", "energetic", "enthusiastic",
            "eager", "anticipating", "can't wait", "looking forward",
            "amazing", "incredible", "unbelievable", "wow", "yes", "yay"
        ));
        moodKeywords.put("excited", excitedWords);
        
        // Calm keywords
        Set<String> calmWords = new HashSet<>(Arrays.asList(
            "calm", "peaceful", "relaxed", "serene", "tranquil", "quiet",
            "still", "composed", "balanced", "centered", "zen", "meditation",
            "breathe", "rest", "comfortable", "at ease", "gentle", "soft"
        ));
        moodKeywords.put("calm", calmWords);
    }
    
    public MoodResult analyzeMood(String text) {
        if (text == null || text.trim().isEmpty()) {
            return new MoodResult("neutral", 0.0);
        }
        
        String[] words = text.toLowerCase().replaceAll("[^a-zA-Z\\s]", "").split("\\s+");
        Map<String, Integer> moodScores = new HashMap<>();
        
        // Initialize scores
        for (String mood : moodKeywords.keySet()) {
            moodScores.put(mood, 0);
        }
        
        // Count keyword matches
        for (String word : words) {
            for (Map.Entry<String, Set<String>> entry : moodKeywords.entrySet()) {
                if (entry.getValue().contains(word)) {
                    moodScores.put(entry.getKey(), moodScores.get(entry.getKey()) + 1);
                }
            }
        }
        
        // Find dominant mood
        String dominantMood = "neutral";
        int maxScore = 0;
        int totalMatches = 0;
        
        for (Map.Entry<String, Integer> entry : moodScores.entrySet()) {
            totalMatches += entry.getValue();
            if (entry.getValue() > maxScore) {
                maxScore = entry.getValue();
                dominantMood = entry.getKey();
            }
        }
        
        // Calculate confidence based on keyword density and dominance
        double confidence = 0.0;
        if (totalMatches > 0 && words.length > 0) {
            double keywordDensity = (double) totalMatches / words.length;
            double dominance = (double) maxScore / totalMatches;
            confidence = Math.min(0.95, keywordDensity * dominance * 2.0);
        }
        
        // If confidence is too low, return neutral
        if (confidence < 0.1) {
            dominantMood = "neutral";
            confidence = 0.5;
        }
        
        return new MoodResult(dominantMood, confidence);
    }
}

