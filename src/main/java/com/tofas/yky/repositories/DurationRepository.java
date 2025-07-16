package com.tofas.yky.repositories;

import com.tofas.core.common.repository.base.TfBaseRepository;
import com.tofas.yky.model.Model;
import com.tofas.yky.model.losses.production.duration.Duration;

import java.util.List;

public interface DurationRepository extends TfBaseRepository<Duration, Long> {

    List<Duration> findByPartDisegnoAndModelsIn(String disegno, Model model);

    List<Duration> findByPartDisegnoLikeAndModelsCodeLike(String disegno, String model);

    Long countByPartDisegnoAndModels_CodeIn(String disegno, List<String> modelCodes);

}
