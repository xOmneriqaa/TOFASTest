package com.tofas.yky.service;

import com.tofas.yky.model.Model;
import com.tofas.yky.model.decoratorbases.decorators.VPartDecorator;
import com.tofas.yky.model.decoratorbases.impl.VPart;
import com.tofas.yky.model.dto.DurationDto;
import com.tofas.yky.model.dto.StepDurationDto;
import com.tofas.yky.model.dto.form.DurationSearchDto;
import com.tofas.yky.model.losses.production.duration.Duration;
import com.tofas.yky.model.losses.production.duration.Step;
import com.tofas.yky.model.losses.production.duration.StepDuration;
import com.tofas.yky.model.losses.production.duration.StepDurationId;
import com.tofas.yky.repositories.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Service
public class DurationService {

    private @Resource
    DurationRepository durationRepository;

    private @Resource
    VPartsRepository vPartsRepository;

    private @Resource
    VTutRepository vTutRepository;

    private @Resource
    ModelRepository modelRepository;

    private @Resource
    StepRepository stepRepository;

    private @Resource StepDurationRepository stepDurationRepository;



    public List<Duration> list(DurationSearchDto durationSearchDto) {
        String disegnoLike = "%" + durationSearchDto.getDisegno() + "%";
        String modelLike = "%" + durationSearchDto.getModel() + "%";
        return durationRepository.findByPartDisegnoLikeAndModelsCodeLike(disegnoLike, modelLike);
    }

    public boolean delete(Long id) {
        Duration duration = durationRepository.findOne(id);
        durationRepository.delete(duration);
        return true;
    }

    public DurationDto getDuration(Long id) {
        DurationDto dto = new DurationDto(durationRepository.findOne(id));

        List<Step> allSteps = stepRepository.findAllActive();

        for (Step step : allSteps) {
            boolean stepFound = false;
            for (StepDurationDto stepDurationDto : dto.getStepDurations()) {
                if(stepDurationDto.getStepDto().getId().equals(step.getId())) {
                    stepFound = true;
                    break;
                }
            }

            // this step is newly added, so add it as 0
            if(!stepFound) {
                dto.getStepDurations().add(new StepDurationDto(step));
            }

        }

        return dto;
    }

    public DurationDto getNew() {
        DurationDto durationDto = new DurationDto();

        List<Step> allSteps = stepRepository.findAllActive();
        for(Step step : allSteps) {
            durationDto.getStepDurations().add(new StepDurationDto(step));
        }


        return durationDto;
    }

    @Transactional
    public boolean save(DurationDto durationDto) {
        if(durationRepository.countByPartDisegnoAndModels_CodeIn(durationDto.getDisegno(), durationDto.getModels()) > 0) {
            return false;
        } else {
            Duration duration = new Duration();
            updateDurationProperties(duration, durationDto, false);
            return true;
        }
    }

    @Transactional
    public boolean updateDuration(DurationDto durationDto) {
        Duration oldDuration = durationRepository.findOne(durationDto.getId());
        updateDurationProperties(oldDuration, durationDto, true);

        return false;
    }

    private void updateDurationProperties(Duration duration, DurationDto durationDto, boolean isUpdate) {
        VPart disegno = vPartsRepository.findByDisegno(durationDto.getDisegno());
        List<Model> models = modelRepository.findAll(durationDto.getModels());


        duration.setPart(new VPartDecorator(durationDto.getDisegno(), disegno));
        duration.setModels(new HashSet<>(models));

        for(StepDurationDto stepDurationDto : durationDto.getStepDurations()) {

            StepDuration stepDuration = null;

            if(duration.getStepDurations() != null) {
                for (StepDuration stepDurationDefined : duration.getStepDurations()) {
                    if(Objects.equals(stepDurationDefined.getStepDurationId().getStep().getId(), stepDurationDto.getStepDto().getId())) {
                        stepDuration = stepDurationDefined;
                        break;
                    }
                }
            } else {
                duration.setStepDurations(new HashSet<>());
            }


            if(stepDuration == null) {
                stepDuration = new StepDuration();
                Step step = stepRepository.findOne(stepDurationDto.getStepDto().getId());
                StepDurationId stepDurationId = new StepDurationId();
                stepDurationId.setStep(step);
                stepDurationId.setDurationDef(duration);
                stepDuration.setStepDurationId(stepDurationId);
                duration.getStepDurations().add(stepDuration);
                stepDuration.setDuration(new BigDecimal(stepDurationDto.getDuration()));

                if(isUpdate) {
                    stepDurationRepository.save(stepDuration);
                }
            } else {
                stepDuration.setDuration(new BigDecimal(stepDurationDto.getDuration()));
            }



        }

        durationRepository.save(duration);
    }
}
