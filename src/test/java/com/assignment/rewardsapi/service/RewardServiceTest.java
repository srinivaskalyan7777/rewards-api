package com.assignment.rewardsapi.service;

import com.assignment.rewardsapi.model.CustomerRewardResponse;
import com.assignment.rewardsapi.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RewardServiceTest {

    private RewardService rewardService;

    @BeforeEach
    void setup() {
        rewardService = new RewardService();
    }

    @Test
    void testCalculateRewardsWithinTimeframe() {
        List<Transaction> transactions = Arrays.asList(
                new Transaction("T1", LocalDate.now().minusDays(10), 120.0),
                new Transaction("T2", LocalDate.now().minusDays(20), 75.0),
                new Transaction("T3", LocalDate.now().minusDays(95), 200.0) // should be excluded (beyond 3 months)
        );

        CustomerRewardResponse response = rewardService.calculateRewards("CUST001", 3, transactions);

        assertNotNull(response);
        assertEquals("CUST001", response.getCustomerId());
        assertEquals(2, response.getTransactionCount()); // Only two valid transactions
        assertTrue(response.getTotalRewardPoints() > 0);
    }

    @Test
    void testCalculateRewardsPointsCalculationLogic() {
        // amount = 120 -> 50 + (20 * 2) = 90 points
        // amount = 75 -> 25 points
        // amount = 40 -> 0 points
        List<Transaction> transactions = Arrays.asList(
                new Transaction("T1", LocalDate.now().minusDays(10), 120.0),
                new Transaction("T2", LocalDate.now().minusDays(20), 75.0),
                new Transaction("T3", LocalDate.now().minusDays(5), 40.0)
        );

        CustomerRewardResponse response = rewardService.calculateRewards("CUST002", 3, transactions);

        assertEquals(115, response.getTotalRewardPoints());
    }

    @Test
    void testCalculateRewardsForEmptyTransactions() {
        List<Transaction> transactions = Arrays.asList();

        CustomerRewardResponse response = rewardService.calculateRewards("CUST003", 3, transactions);

        assertEquals(0, response.getTransactionCount());
        assertEquals(0, response.getTotalRewardPoints());
    }

    @Test
    void testNegativeMonthsThrowsException() {
        List<Transaction> transactions = Arrays.asList(
                new Transaction("T1", LocalDate.now(), 100.0)
        );

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            rewardService.calculateRewards("CUST004", -1, transactions);
        });

        assertEquals("Timeframe (months) must be greater than 0", exception.getMessage());
    }
}
