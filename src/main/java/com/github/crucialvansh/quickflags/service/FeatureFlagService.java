package com.github.crucialvansh.quickflags.service;

import com.github.crucialvansh.quickflags.models.FeatureFlag;

import com.github.crucialvansh.quickflags.models.FeatureFlagRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class FeatureFlagService {
    private final FeatureFlagRepository flagRepository;

    public FeatureFlagService(FeatureFlagRepository flagRepository) {
        this.flagRepository = flagRepository;
    }

    @Cacheable("flags")
    public boolean isFeatureEnabled(String featureName, String userId) {
        if (userId == null) return false;
        var optionalFeatureFlag = this.flagRepository.findById(featureName);
        if (optionalFeatureFlag.isEmpty()) {
            return false;
        }
        FeatureFlag feature = optionalFeatureFlag.get();
        if (!feature.isEnabled()) return false;
        if (feature.getRolloutPercentage() == 100) return true;
        else return (Math.abs((userId + featureName).hashCode()) % 100) < feature.getRolloutPercentage();
    }
}
