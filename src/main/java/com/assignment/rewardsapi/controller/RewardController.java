package com.assignment.rewardsapi.controller;

import com.assignment.rewardsapi.model.CustomerRewardResponse;
import com.assignment.rewardsapi.service.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/rewards")
public class RewardController {

    private final RewardService rewardService;

    @Autowired
    public RewardController(RewardService rewardService) {
        this.rewardService = rewardService;
    }

    /**
     * GET /api/rewards?customerId=CUST001&startDate=2025-08-01&endDate=2025-10-31
     * If startDate or endDate are not provided, defaults to the last 3 months.
     */
    @GetMapping
    public CustomerRewardResponse getRewards(
            @RequestParam String customerId,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        return rewardService.calculateRewards(customerId, startDate, endDate);
    }
}
