package com.tofas.yky.service.additional;
/* T40127 @ 16.10.2015. */

import com.tofas.yky.model.additional.IDiscardedPartAddableLoss;
import com.tofas.yky.model.dto.additional.IDiscardedPartAddableDto;

public interface IDiscardedPartAddableLossService {

    void addDiscardedParts(IDiscardedPartAddableDto dto, IDiscardedPartAddableLoss loss);

}
