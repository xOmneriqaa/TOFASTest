package com.tofas.yky.repositories;

import com.tofas.core.common.repository.base.TfBaseRepository;
import com.tofas.yky.model.losses.Loss;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface LossRepository<T extends Loss> extends TfBaseRepository<T, Long> {
}
