package com.assignment.rewardsapi.controller;

import com.assignment.rewardsapi.exception.RewardException;
import com.assignment.rewardsapi.model.CustomerRewardResponse;
import com.assignment.rewardsapi.service.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rewards")
public class RewardController {

    private final RewardService rewardService;

    @Autowired
    public RewardController(RewardService rewardService) {
        this.rewardService = rewardService;
    }

    /**
     * GET /api/rewards?customerId=...&months=...
     * months is optional and defaults to 3
     */
    @GetMapping
    public CustomerRewardResponse getRewards(
            @RequestParam String customerId,
            @RequestParam(required = false, defaultValue = "3") int months) {

        // Basic validation at controller level (service also validates)
        if (months <= 0) {
            throw new RewardException("Timeframe (months) must be greater than 0.");
        }
        if (months > 3) {
            throw new RewardException("Timeframe (months) must not exceed 3.");
        }

        return rewardService.calculateRewards(customerId, months);
    }
}
