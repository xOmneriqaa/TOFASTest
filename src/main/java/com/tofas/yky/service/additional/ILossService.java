package com.tofas.yky.service.additional;

import com.tofas.yky.model.losses.Loss;

/**
 * Created by mndeveci
 */
public interface ILossService<T extends Loss> {

    T getLoss(Long id);

}
