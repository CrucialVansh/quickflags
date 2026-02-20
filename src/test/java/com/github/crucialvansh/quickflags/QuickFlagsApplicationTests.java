package com.github.crucialvansh.quickflags;

import com.github.crucialvansh.quickflags.models.FeatureFlag;
import com.github.crucialvansh.quickflags.models.FeatureFlagRepository;
import com.github.crucialvansh.quickflags.service.FeatureFlagService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FeatureFlagServiceTest {

    @Mock
    private FeatureFlagRepository flagRepository;

    @InjectMocks
    private FeatureFlagService featureFlagService;

    @Test
    void shouldReturnFalse_WhenFlagDoesNotExist() {
        when(flagRepository.findById("ghost-flag")).thenReturn(Optional.empty());

        boolean result = featureFlagService.isFeatureEnabled("ghost-flag", "user123");

        assertFalse(result, "Expected false because the flag does not exist");
    }

    @Test
    void shouldReturnTrue_WhenFlagExists() {
        FeatureFlag activeFlag = new FeatureFlag();
        activeFlag.setName("new-checkout");
        activeFlag.setEnabled(true);
        activeFlag.setRolloutPercentage(100);

        when(flagRepository.findById("new-checkout")).thenReturn(Optional.of(activeFlag));

        boolean result = featureFlagService.isFeatureEnabled("new-checkout", "user123");

        assertTrue(result, "Expected true because the flag is enabled with 100% rollout");
    }

    @Test
    void shouldBeDeterministic_WhenRolloutIs50Percent() {
        FeatureFlag halfRolloutFlag = new FeatureFlag();
        halfRolloutFlag.setName("beta-feature");
        halfRolloutFlag.setEnabled(true);
        halfRolloutFlag.setRolloutPercentage(50);

        when(flagRepository.findById("beta-feature")).thenReturn(Optional.of(halfRolloutFlag));

        boolean attempt1 = featureFlagService.isFeatureEnabled("beta-feature", "user-999");
        boolean attempt2 = featureFlagService.isFeatureEnabled("beta-feature", "user-999");
        boolean attempt3 = featureFlagService.isFeatureEnabled("beta-feature", "user-999");

        assertEquals(attempt1, attempt2, "Attempt 1 and 2 should match");
        assertEquals(attempt2, attempt3, "Attempt 2 and 3 should match");
    }
}
