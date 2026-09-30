package com.project.creditservice.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeatureContribution {

    private String feature;
    private Double contribution;
}