package com.github.crucialvansh.quickflags.models;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FeatureFlagRepository extends JpaRepository<FeatureFlag, String> {

}