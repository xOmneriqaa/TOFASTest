package com.tofas.yky.service.additional;

import com.tofas.yky.model.additional.IQualityLabTestAddableLoss;
import com.tofas.yky.model.dto.additional.IQualityLabTestAddableDto;

/**
 * Created by T40127 on 11.10.2015.
 */
public interface IQualityLabTestAddableLossService {

    void addQualityLabTests(IQualityLabTestAddableDto dto, IQualityLabTestAddableLoss loss);

}
