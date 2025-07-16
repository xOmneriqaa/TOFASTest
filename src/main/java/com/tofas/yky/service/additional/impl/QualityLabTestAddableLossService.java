package com.tofas.yky.service.additional.impl;

import com.tofas.yky.model.additional.IQualityLabTestAddableLoss;
import com.tofas.yky.model.dto.QualityLabTestDto;
import com.tofas.yky.model.dto.additional.IQualityLabTestAddableDto;
import com.tofas.yky.model.losses.qualitylab.QualityLabLoss;
import com.tofas.yky.model.losses.qualitylab.QualityLabLossTest;
import com.tofas.yky.model.losses.qualitylab.QualityLabTest;
import com.tofas.yky.repositories.QualityLabTestRepository;
import com.tofas.yky.service.additional.IQualityLabTestAddableLossService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

@Service
public class QualityLabTestAddableLossService implements IQualityLabTestAddableLossService {

    @Resource
    private QualityLabTestRepository qualityLabTestRepository;

    @Override
    public void addQualityLabTests(IQualityLabTestAddableDto dto, IQualityLabTestAddableLoss loss) {
        Set<QualityLabLossTest> labTests = new HashSet<>();

        for (QualityLabTestDto qualityLabTestDto : dto.getQualityLabTests()) {
            QualityLabTest qualityLabTest = qualityLabTestRepository.findOne(qualityLabTestDto.getTestId());

            QualityLabLossTest qualityLabLossTest = new QualityLabLossTest();
            qualityLabLossTest.setQualityLabTest(qualityLabTest);
            qualityLabLossTest.setLoss((QualityLabLoss) loss);
            qualityLabLossTest.setQualityTestNameCached(qualityLabTest.getName());
            qualityLabLossTest.setQualityTestPriceCached(qualityLabTest.getPrice());
            qualityLabLossTest.setQty(qualityLabTestDto.getQty());

            labTests.add(qualityLabLossTest);
        }

        loss.setQualityLabTests(labTests);

    }
}
