package com.tofas.yky.service.additional.impl;

import com.tofas.yky.model.additional.IWorkDurationAddableLoss;
import com.tofas.yky.model.dto.WorkDurationDto;
import com.tofas.yky.model.dto.additional.IWorkDurationAddableLossDto;
import com.tofas.yky.model.losses.Loss;
import com.tofas.yky.model.losses.directdurations.LossDirectDuration;
import com.tofas.yky.model.losses.WorkType;
import com.tofas.yky.repositories.WorkTypeRepository;
import com.tofas.yky.service.additional.IWorkDurationAddableLossService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

@Service
public class WorkDurationAddableLossService implements IWorkDurationAddableLossService {

    @Resource
    private WorkTypeRepository workTypeRepository;

    @Override
    public void addWorkDurations(IWorkDurationAddableLossDto dto, IWorkDurationAddableLoss loss) {
        Set<LossDirectDuration> lossDirectDurations = new HashSet<>();

        for (WorkDurationDto workDurationDto : dto.getWorkDurations()) {
            WorkType workType = workTypeRepository.findOne(workDurationDto.getWorkType());

            LossDirectDuration directDuration = new LossDirectDuration();
            directDuration.setQty(workDurationDto.getQty());
            directDuration.setDuration(workDurationDto.getWorkDuration());
            directDuration.setWorkType(workType);
            directDuration.setLoss((Loss) loss);
            directDuration.setWorkTypeCached(workType.getName());
            directDuration.setWorkTypeWageCached(workType.getWageInMinutes());

            lossDirectDurations.add(directDuration);
        }

        loss.setDirectDurations(lossDirectDurations);
    }
}
