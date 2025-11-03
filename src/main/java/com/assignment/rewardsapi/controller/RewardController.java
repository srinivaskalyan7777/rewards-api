package com.assignment.rewardsapi.controller;

import com.assignment.rewardsapi.model.CustomerRewardResponse;
import com.assignment.rewardsapi.model.Transaction;
import com.assignment.rewardsapi.service.RewardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rewards")
public class RewardController {

    private static final Logger logger = LoggerFactory.getLogger(RewardController.class);

    @Autowired
    private RewardService rewardService;

    @PostMapping("/calculate")
    public ResponseEntity<CustomerRewardResponse> calculateRewards(
            @RequestParam String customerId,
            @RequestParam(defaultValue = "3") int months,
            @RequestBody List<Transaction> transactions) {

        logger.info("Calculating rewards for customerId={} for last {} months", customerId, months);

        if (months <= 0) {
            throw new IllegalArgumentException("Timeframe (months) must be greater than 0");
        }

        CustomerRewardResponse response = rewardService.calculateRewards(customerId, months, transactions);
        return ResponseEntity.ok(response);
    }
}
