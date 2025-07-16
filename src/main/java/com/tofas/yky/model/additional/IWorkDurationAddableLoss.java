package com.tofas.yky.model.additional;

import com.tofas.yky.model.losses.directdurations.LossDirectDuration;

import java.util.Set;

public interface IWorkDurationAddableLoss {

    Set<LossDirectDuration> getDirectDurations();

    void setDirectDurations(Set<LossDirectDuration> directDurations);
}
