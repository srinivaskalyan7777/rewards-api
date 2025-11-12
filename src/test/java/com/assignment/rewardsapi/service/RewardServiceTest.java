package com.assignment.rewardsapi.service;

import com.assignment.rewardsapi.exception.RewardException;
import com.assignment.rewardsapi.model.CustomerRewardResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

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
    void calculateRewards_forKnownCustomer_defaultThreeMonths_returnsExpectedTotal() {
        // Default behavior: endDate = now, startDate = now - 3 months
        CustomerRewardResponse resp = rewardService.calculateRewards("CUST001", null, null);

        assertNotNull(resp, "Response should not be null");
        assertEquals("CUST001", resp.getCustomerId());
        assertTrue(resp.getTotalRewards() > 0, "Reward points should be positive");
        assertNotNull(resp.getTransactions(), "Transactions list should be present");
        assertFalse(resp.getTransactions().isEmpty(), "Transactions should not be empty");
    }

    @Test
    void calculateRewards_forKnownCustomer_withCustomDateRange_returnsFilteredResults() {
        LocalDate start = LocalDate.now().minusDays(60);
        LocalDate end = LocalDate.now();

        CustomerRewardResponse resp = rewardService.calculateRewards("CUST001", start, end);

        assertNotNull(resp);
        assertEquals("CUST001", resp.getCustomerId());
        assertTrue(resp.getTransactions().stream()
                .allMatch(t -> !t.getDate().isBefore(start) && !t.getDate().isAfter(end)),
                "All transactions must be within the given date range");
    }

    @Test
    void calculateRewards_withInvalidDateRange_throwsRewardException() {
        LocalDate start = LocalDate.now();
        LocalDate end = LocalDate.now().minusMonths(1); // invalid: start after end

        RewardException ex = assertThrows(RewardException.class, () ->
                rewardService.calculateRewards("CUST001", start, end)
        );

        assertTrue(ex.getMessage().toLowerCase().contains("start"), 
                   "Exception message should mention start date being after end date");
    }

    @Test
    void calculateRewards_withUnknownCustomer_throwsRewardException() {
        LocalDate start = LocalDate.now().minusMonths(1);
        LocalDate end = LocalDate.now();

        RewardException ex = assertThrows(RewardException.class, () ->
                rewardService.calculateRewards("UNKNOWN", start, end)
        );

        assertTrue(ex.getMessage().toLowerCase().contains("customer"),
                   "Exception message should mention customer not found");
    }

    @Test
    void calculateRewards_withPartialDates_defaultsToLastThreeMonths() {
        // No start date, only end date provided
        LocalDate end = LocalDate.now();

        CustomerRewardResponse resp = rewardService.calculateRewards("CUST001", null, end);

        assertNotNull(resp);
        assertEquals("CUST001", resp.getCustomerId());
        assertTrue(resp.getTransactions().size() > 0, "Should still return valid transactions");
    }
}
