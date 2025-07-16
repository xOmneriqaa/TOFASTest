package com.tofas.yky.repositories.press;
/* t40127 @ 19.05.2016. */

import com.tofas.core.common.repository.base.TfBaseRepository;
import com.tofas.yky.enums.LossState;
import com.tofas.yky.enums.LossType;
import com.tofas.yky.enums.PressLossType;
import com.tofas.yky.model.losses.press.PressLoss;

import java.util.List;

public interface PressLossRepository extends TfBaseRepository<PressLoss, Long> {

    List<PressLoss> findByLossTypeAndStateChanges_LossStateInAndStatusOrderByLossDate(
            LossType lossType, List<LossState> states, Integer status);

    List<PressLoss> findByPressLossType(PressLossType pressLossType);

}
