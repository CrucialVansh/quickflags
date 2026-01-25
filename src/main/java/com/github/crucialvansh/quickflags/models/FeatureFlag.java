package com.github.crucialvansh.quickflags.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "feature_flags")
public class FeatureFlag {

    @Id
    @Column(nullable = false, unique = true)
    private String name; // The key, e.g., "new-checkout-flow"

    @Column(nullable = false)
    private String description; // e.g., "Replaces old checkout with React version"

    @Column(nullable = false)
    private boolean isEnabled; // The master "Kill Switch". If false, nobody gets it.

    @Column(nullable = false)
    private int rolloutPercentage; // 0 to 100. (0 = Nobody, 100 = Everyone)

    // Timestamps for audit (Enterprise requirement!)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;


    // Getters, Setters, Constructors...
}