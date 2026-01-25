package com.github.crucialvansh.quickflags.controller;

import com.github.crucialvansh.quickflags.models.FeatureFlag;
import com.github.crucialvansh.quickflags.models.FeatureFlagRepository;
import com.github.crucialvansh.quickflags.service.FeatureFlagService;
import org.springframework.web.bind.annotation.*;

@RestController
public class FeatureFlagController {
    private final FeatureFlagService featureFlagService;
    private final FeatureFlagRepository flagRepository;

    public FeatureFlagController(FeatureFlagService featureFlagService, FeatureFlagRepository flagRepository) {
        this.featureFlagService = featureFlagService;
        this.flagRepository = flagRepository;
    }

    @PostMapping("/api/flags")
    public FeatureFlag createNewFlag(@RequestBody FeatureFlag featureFlag) {

        return flagRepository.save(featureFlag);
    }

    @GetMapping("/{name}/check")
    public boolean checkFlag(@PathVariable String name, @RequestParam(value = "userId") String userId) {
        return featureFlagService.isFeatureEnabled(userId, name);
    }

}
