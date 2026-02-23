package com.project.fitness.service;

import com.project.fitness.Model.Activity;
import com.project.fitness.Model.User;
import com.project.fitness.dto.ActivityRequest;
import com.project.fitness.dto.ActivityResponse;
import com.project.fitness.repository.ActivityRepository;
import com.project.fitness.repository.UserRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityService {
    private final ActivityRepository activityRepository;
    private final UserRepository userRepository;

    public ActivityService(ActivityRepository activityRepository, UserRepository userRepository) {
        this.activityRepository = activityRepository;
        this.userRepository = userRepository;
    }
    public ActivityResponse trackActivity(ActivityRequest request) {
        User user= userRepository.findById(request.getUserid())
                .orElseThrow(()-> new RuntimeException("Invalid user :"+request.getUserid()));
        Activity activity=Activity.builder()
                .user(user)
                .type(request.getType())
                .additionalMetrices(request.getAdditionalMetrices())
                .duration(request.getDuration())
                .caloriesBurned(request.getCaloriesBurned())
                .startTime(request.getStartTime())
                .build();
        Activity savedActivity=activityRepository.save(activity);
        return mapToResponse(savedActivity);
    }

    private ActivityResponse mapToResponse(Activity activity) {
        ActivityResponse response=new ActivityResponse();
         response.setId(activity.getId());
         response.setUserid(activity.getUser().getId());
         response.setType(activity.getType());
         response.setDuration(activity.getDuration());
         response.setCaloriesBurned(activity.getCaloriesBurned());
         response.setStartTime(activity.getStartTime());
         response.setAdditionalMetrices(activity.getAdditionalMetrices());
         response.setCreatedAt(activity.getCreatedAt());
         response.setUpdatedAt(activity.getUpdatedAt());
         return response;
    }

    public List<ActivityResponse> getUserActivities(String userid) {
        List<Activity> activityList=activityRepository.findAByUserId(userid);
        return activityList.stream().map(this::mapToResponse).collect(Collectors.toList());
    }
}
