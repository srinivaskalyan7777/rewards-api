package com.assignment.rewardsapi.service;

import com.assignment.rewardsapi.exception.RewardException;
import com.assignment.rewardsapi.model.CustomerRewardResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for RewardService.
 * Uses the in-memory seeded data from RewardService.
 */
class RewardServiceTest {

    private RewardService rewardService;

    @BeforeEach
    void setUp() {
        rewardService = new RewardService();
    }

    @Test
    void calculateRewards_forKnownCustomer_overThreeMonths_returnsExpectedTotal() {
        // CUST001 has three seeded transactions in RewardService's static data:
        // amounts: 120.00 (points 90), 75.00 (points 25), 200.00 (points 250)
        // total expected points = 90 + 25 + 250 = 365
        CustomerRewardResponse resp = rewardService.calculateRewards("CUST001", 3);

        assertNotNull(resp, "Response should not be null");
        assertEquals("CUST001", resp.getCustomerId());
        assertEquals(365, resp.getTotalRewards(), "Total reward points for CUST001 over 3 months should be 365");
        assertNotNull(resp.getTransactions(), "Transactions list should be present");
        assertTrue(resp.getTransactions().size() >= 1, "At least one transaction should be returned");
    }

    @Test
    void calculateRewards_monthsZero_throwsRewardException() {
        RewardException ex = assertThrows(RewardException.class, () -> {
            rewardService.calculateRewards("CUST001", 0);
        });
        assertTrue(ex.getMessage().toLowerCase().contains("months"), "Exception message should mention months");
    }

    @Test
    void calculateRewards_monthsMoreThanThree_throwsRewardException() {
        RewardException ex = assertThrows(RewardException.class, () -> {
            rewardService.calculateRewards("CUST001", 4);
        });
        assertTrue(ex.getMessage().toLowerCase().contains("not exceed") || ex.getMessage().toLowerCase().contains("cannot exceed"),
                "Exception message should indicate months cannot exceed 3");
    }

    @Test
    void calculateRewards_unknownCustomer_throwsRewardException() {
        RewardException ex = assertThrows(RewardException.class, () -> {
            rewardService.calculateRewards("UNKNOWN", 3);
        });
        assertTrue(ex.getMessage().toLowerCase().contains("customer"), "Exception message should mention customer not found");
    }
}
