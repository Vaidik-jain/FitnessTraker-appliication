package com.project.fitness.controller;

import com.project.fitness.Model.Activity;
import com.project.fitness.Model.Recommendations;
import com.project.fitness.dto.RecommendationsRequest;
import com.project.fitness.service.RecommendationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommendation")
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }


    @PostMapping("/generate")
    public ResponseEntity<Recommendations> generateRecommendation(@RequestBody RecommendationsRequest request){
        Recommendations recommendation= recommendationService.generateRecommendation(request);
        return ResponseEntity.ok(recommendation);
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Recommendations>> getUserRecommendation(@PathVariable String userId){
        List<Recommendations> userRecommendation=recommendationService.getUserRecommendation(userId);
        return ResponseEntity.ok(userRecommendation);
    }
    @GetMapping("/activity/{activityId}")
    public ResponseEntity<List<Recommendations>> getActivityRecommendation(@PathVariable String activityId){
        List<Recommendations> activityRecommendation= recommendationService.getActivityRecommendation(activityId);
        return ResponseEntity.ok(activityRecommendation);
    }
}
