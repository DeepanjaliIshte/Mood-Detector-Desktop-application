package com.mooddetector.Mood_Detecor;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.stereotype.Service;

@Service
public class MoodAnalysisService {
    
    private final Queue<MoodResult> recentAnalyses = new ConcurrentLinkedQueue<>();
    private final int MAX_HISTORY = 50;
    
    // Simple keyword-based mood analysis
    private final Map<String, String> positiveWords = Map.ofEntries(
    	    Map.entry("happy", "joy"),
    	    Map.entry("excited", "joy"),
    	    Map.entry("love", "love"),
    	    Map.entry("great", "joy"),
    	    Map.entry("awesome", "joy"),
    	    Map.entry("wonderful", "joy"),
    	    Map.entry("amazing", "joy"),
    	    Map.entry("fantastic", "joy"),
    	    Map.entry("excellent", "joy"),
    	    Map.entry("perfect", "joy"),
    	    Map.entry("brilliant", "joy"),
    	    Map.entry("good", "contentment")
    	);
    
    private final Map<String, String> negativeWords = Map.ofEntries(
    	    Map.entry("sad", "sadness"),
    	    Map.entry("angry", "anger"),
    	    Map.entry("hate", "anger"),
    	    Map.entry("terrible", "sadness"),
    	    Map.entry("awful", "sadness"),
    	    Map.entry("horrible", "sadness"),
    	    Map.entry("bad", "sadness"),
    	    Map.entry("worst", "anger"),
    	    Map.entry("frustrated", "anger"),
    	    Map.entry("disappointed", "sadness"),
    	    Map.entry("depressed", "sadness")
    	);

    	private final Map<String, String> anxiousWords = Map.ofEntries(
    	    Map.entry("worried", "anxiety"),
    	    Map.entry("nervous", "anxiety"),
    	    Map.entry("scared", "fear"),
    	    Map.entry("afraid", "fear"),
    	    Map.entry("anxious", "anxiety"),
    	    Map.entry("stress", "anxiety"),
    	    Map.entry("panic", "anxiety"),
    	    Map.entry("overwhelmed", "anxiety")
    	);
    
    public MoodResult analyzeMood(String text) {
        if (text == null || text.trim().isEmpty()) {
            return new MoodResult("neutral", 0.0, "No text provided", new Date());
        }
        
        String lowerText = text.toLowerCase();
        String[] words = lowerText.split("\\s+");
        
        Map<String, Integer> moodCounts = new HashMap<>();
        moodCounts.put("joy", 0);
        moodCounts.put("sadness", 0);
        moodCounts.put("anger", 0);
        moodCounts.put("anxiety", 0);
        moodCounts.put("contentment", 0);
        
        // Count mood indicators
        for (String word : words) {
            if (positiveWords.containsKey(word)) {
                String mood = positiveWords.get(word);
                moodCounts.put(mood, moodCounts.get(mood) + 1);
            } else if (negativeWords.containsKey(word)) {
                String mood = negativeWords.get(word);
                moodCounts.put(mood, moodCounts.get(mood) + 1);
            } else if (anxiousWords.containsKey(word)) {
                String mood = anxiousWords.get(word);
                moodCounts.put(mood, moodCounts.get(mood) + 1);
            }
        }
        
        // Determine dominant mood
        String dominantMood = "neutral";
        int maxCount = 0;
        for (Map.Entry<String, Integer> entry : moodCounts.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                dominantMood = entry.getKey();
            }
        }
        
        // Calculate confidence based on word count ratio
        double confidence = Math.min(1.0, (double) maxCount / Math.max(1, words.length) * 10);
        
        String description = generateDescription(dominantMood, confidence);
        
        MoodResult result = new MoodResult(dominantMood, confidence, description, new Date());
        
        // Add to history
        recentAnalyses.offer(result);
        if (recentAnalyses.size() > MAX_HISTORY) {
            recentAnalyses.poll();
        }
        
        return result;
    }
    
    private String generateDescription(String mood, double confidence) {
        String confidenceLevel = confidence > 0.7 ? "high" : confidence > 0.4 ? "moderate" : "low";
        
        switch (mood) {
            case "joy":
                return String.format("Positive emotions detected with %s confidence. The text expresses happiness or excitement.", confidenceLevel);
            case "sadness":
                return String.format("Sadness detected with %s confidence. The text contains melancholic or disappointed sentiments.", confidenceLevel);
            case "anger":
                return String.format("Anger detected with %s confidence. The text shows frustration or irritation.", confidenceLevel);
            case "anxiety":
                return String.format("Anxiety detected with %s confidence. The text indicates worry or nervousness.", confidenceLevel);
            case "contentment":
                return String.format("Contentment detected with %s confidence. The text shows satisfaction or peace.", confidenceLevel);
            default:
                return "Neutral mood detected. The text doesn't contain strong emotional indicators.";
        }
    }
    
    public List<MoodResult> getRecentAnalyses() {
        return new ArrayList<>(recentAnalyses);
    }
}
