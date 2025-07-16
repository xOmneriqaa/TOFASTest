package com.tofas.yky.repositories;
/* T40127 @ 03.11.2015. */

import com.tofas.yky.model.losses.production.duration.StepDuration;
import com.tofas.yky.model.losses.production.duration.StepDurationId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StepDurationRepository extends JpaRepository<StepDuration, StepDurationId> { }
