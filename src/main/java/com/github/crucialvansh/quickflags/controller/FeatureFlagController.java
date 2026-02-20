package com.github.crucialvansh.quickflags.controller;

import com.github.crucialvansh.quickflags.models.FeatureFlag;
import com.github.crucialvansh.quickflags.models.FeatureFlagRepository;
import com.github.crucialvansh.quickflags.service.FeatureFlagService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flags")
public class FeatureFlagController {
    private final FeatureFlagService featureFlagService;
    private final FeatureFlagRepository flagRepository;

    public FeatureFlagController(FeatureFlagService featureFlagService, FeatureFlagRepository flagRepository) {
        this.featureFlagService = featureFlagService;
        this.flagRepository = flagRepository;
    }

    @PostMapping
    public FeatureFlag createNewFlag(@RequestBody FeatureFlag featureFlag) {
        return flagRepository.save(featureFlag);
    }

    @GetMapping
    public List<FeatureFlag> getAllFlags() {
        return flagRepository.findAll();
    }

    @CacheEvict(value = "flags", allEntries = true)
    @PutMapping("/{name}")
    public FeatureFlag updateFlag(@PathVariable String name, @RequestBody FeatureFlag updatedFlag) {
        return flagRepository.findById(name).map(flag -> {
                flag.setRolloutPercentage(updatedFlag.getRolloutPercentage());
                flag.setDescription(updatedFlag.getDescription());
                flag.setUpdatedAt(updatedFlag.getUpdatedAt());
                return flagRepository.save(flag);
        }).orElseGet(() -> flagRepository.save(updatedFlag));

    }

    @GetMapping("/{name}/check")
    public boolean checkFlag(@PathVariable String name, @RequestParam(value = "userId") String userId) {
        return featureFlagService.isFeatureEnabled(name, userId);
    }

}
