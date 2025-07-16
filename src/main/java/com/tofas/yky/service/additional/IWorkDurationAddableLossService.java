package com.tofas.yky.service.additional;

import com.tofas.yky.model.additional.IWorkDurationAddableLoss;
import com.tofas.yky.model.dto.additional.IWorkDurationAddableLossDto;

/**
 * Created by T40127 on 11.10.2015.
 */
public interface IWorkDurationAddableLossService {

    void addWorkDurations(IWorkDurationAddableLossDto dto, IWorkDurationAddableLoss loss);
}
