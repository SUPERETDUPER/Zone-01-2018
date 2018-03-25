/*
 * Copyright (c) [2018] [Jonathan McIntosh, Martin Staadecker, Ryan Zazo]
 */

package common.particles;

import common.mapping.SurfaceMap;
import lejos.robotics.navigation.Pose;
import org.jetbrains.annotations.NotNull;

/**
 * Immutable particle (position + weight)
 */
public final class Particle {
    private static final String LOG_TAG = Particle.class.getSimpleName();

    @NotNull
    private final Pose pose;
    public final float weight;

    public Particle(float x, float y, float heading, float weight) {
        this.pose = new Pose(x, y, heading);

        if (!SurfaceMap.contains((int) x, (int) y)) {
            throw new RuntimeException(pose.toString());
        }

        this.weight = weight;
    }

    public Particle(@NotNull Pose pose, float weight) {
        this(pose.getX(), pose.getY(), pose.getHeading(), weight); // Not directly this.pose = pose because want to keep object immutable
    }


    @NotNull
    public Pose getPose() {
        return new Pose(pose.getX(), pose.getY(), pose.getHeading()); //To make it immutable
    }

    @NotNull
    public Particle getParticleWithNewWeight(float weight) {
        return new Particle(pose, weight);
    }
}