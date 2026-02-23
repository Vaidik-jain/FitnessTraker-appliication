package com.project.fitness.service;

import com.project.fitness.Model.Activity;
import com.project.fitness.Model.Recommendations;
import com.project.fitness.Model.User;
import com.project.fitness.dto.RecommendationsRequest;
import com.project.fitness.repository.ActivityRepository;
import com.project.fitness.repository.RecommendationReository;
import com.project.fitness.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final UserRepository userRepository;
    private final ActivityRepository activityRepository;
    private final RecommendationReository recommendationReository;

    public Recommendations generateRecommendation(RecommendationsRequest request) {
        User user= userRepository.findById(request.getUserId())
                .orElseThrow(()->new RuntimeException("user not found"+request.getUserId()));
        Activity activity= activityRepository.findById(request.getActivityId())
                .orElseThrow(()->new RuntimeException("Activity not found"+request.getActivityId()));
      Recommendations recommendations = Recommendations.builder()
              .user(user)
              .activity(activity)
              .improvements(request.getImprovements())
              .suggestions(request.getSuggestions())
              .safety(request.getSafety())
              .build();

      Recommendations savedRecommendatios= recommendationReository.save(recommendations);
       return savedRecommendatios;
    }

    public List<Recommendations> getUserRecommendation(String userId) {
        return recommendationReository.findByUserId(userId);
    }

    public List<Recommendations> getActivityRecommendation(String activityId) {
        return recommendationReository.findByActivityId(activityId);
    }
}
