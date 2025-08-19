package com.mooddetector.Mood_Detecor;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MoodController {
    
    @Autowired
    private MoodAnalysisService moodAnalysisService;
    
    @GetMapping("/")
    public String home() {
        return "index";
    }
    
    @PostMapping("/analyze")
    @ResponseBody
    public MoodResult analyzeMood(@RequestParam("text") String text) {
        return moodAnalysisService.analyzeMood(text);
    }
    
    @GetMapping("/history")
    public String history(Model model) {
        model.addAttribute("analyses", moodAnalysisService.getRecentAnalyses());
        return "history";
    }
}
