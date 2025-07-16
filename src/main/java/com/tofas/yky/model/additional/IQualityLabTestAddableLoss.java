package com.tofas.yky.model.additional;

import com.tofas.yky.model.losses.qualitylab.QualityLabLossTest;

import java.math.BigDecimal;
import java.util.Set;

public interface IQualityLabTestAddableLoss {

    Set<QualityLabLossTest> getQualityLabTests();

    void setQualityLabTests(Set<QualityLabLossTest> directDurations);

    BigDecimal getTotalTestCost();

}
