package com.tofas.yky.service;
/* T40127 @ 19.10.2015. */

import com.google.common.eventbus.EventBus;
import com.tofas.core.common.utility.TfAuthUtility;
import com.tofas.yky.enums.ObjectionStatus;
import com.tofas.yky.events.LossIsBeenObjectedEvent;
import com.tofas.yky.events.LossObjectionIsResolvedEvent;
import com.tofas.yky.model.VOpenObjection;
import com.tofas.yky.model.dto.ObjectionChangeDto;
import com.tofas.yky.model.dto.ObjectionResultDto;
import com.tofas.yky.model.losses.Loss;
import com.tofas.yky.model.losses.LossObjection;
import com.tofas.yky.model.losses.ObjectionType;
import com.tofas.yky.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class RefreeService {

    @Resource private LossObjectionRepository lossObjectionRepository;
    @Resource private ObjectionTypeRepository objectionTypeRepository;

    @Resource private ProductionLossRepository productionLossRepository;
    @Resource private LogisticsLossRepository logisticsLossRepository;
    @Resource private QualityLossRepository qualityLossRepository;
    @Resource private QualityLabLossRepository qualityLabLossRepository;

    @Resource private VOpenObjectionRepository vOpenObjectionRepository;

    @Autowired private EventBus eventBus;

    @Resource private CommonLossRepository commonLossRepository;

    public List<VOpenObjection> listOpenObjections() {
        //return getLossesFromObjections(lossObjectionRepository.findByObjectionStatus(ObjectionStatus.OPEN));
        return vOpenObjectionRepository.findAll();
    }

    public List<VOpenObjection> listMyOpenObjections() {
        //return getLossesFromObjections(lossObjectionRepository.findByObjectionStatusAndObjectionType_ResponsiblesIn(ObjectionStatus.OPEN, Arrays.asList(TfAuthUtility.getUsername())));
        return vOpenObjectionRepository.findByRawResponsiblesLike("%" + TfAuthUtility.getUsername() + "%");
    }

    private List<Loss> getLossesFromObjections(List<LossObjection> objections) {
        List<Loss> losses = new ArrayList<>();

        for(LossObjection objection : objections) {
            losses.add(objection.getLoss());
        }

        return losses;
    }

    public Long countTotalObjected() {
        return vOpenObjectionRepository.count();
    }

    public Long countMyTotalObjected() {
        //return lossObjectionRepository.countByObjectionStatusAndObjectionType_ResponsiblesIn(ObjectionStatus.OPEN, Arrays.asList(TfAuthUtility.getUsername()));
        return vOpenObjectionRepository.countByRawResponsiblesLike("%" + TfAuthUtility.getUsername() + "%");
    }


    public boolean changeObjection(ObjectionChangeDto dto) {
        LossObjection objection = lossObjectionRepository.findOne(dto.getObjectionInstanceId());
        objection.setObjectionResultDescription(dto.getDescription());
        int newStep = objection.getStep() + 1;
        objection.setObjectionStatus(ObjectionStatus.TRANSFER);

        LossObjection newObjection = new LossObjection();
        ObjectionType objectionType = objectionTypeRepository.findOne(dto.getObjectionId());
        newObjection.setObjectionType(objectionType);
        newObjection.setLoss(objection.getLoss());
        newObjection.setObjectionDescription(objection.getObjectionDescription());
        newObjection.setStep(newStep);

        lossObjectionRepository.save(objection);
        lossObjectionRepository.save(newObjection);

        objection.getLoss().getObjections().add(newObjection);
        eventBus.post(new LossIsBeenObjectedEvent(objection.getLoss()));


        return true;
    }

    public boolean setObejctionResult(ObjectionResultDto dto) {
        Loss loss = commonLossRepository.getLoss(dto.getLossId());
        LossObjection objection = loss.getCurrentObjection();

        objection.setObjectionResultDescription(dto.getDescription());

        if(dto.isStatus()) {
            objection.setObjectionStatus(ObjectionStatus.ACCEPT);
            objection.getLoss().cancelLoss(dto.getDescription());

            saveLossInstace(objection.getLoss());

        } else {

            objection.setObjectionStatus(ObjectionStatus.DENY);
            lossObjectionRepository.save(objection);

        }


        eventBus.post(new LossObjectionIsResolvedEvent(objection.getLoss()));

        return true;
    }

    private void saveLossInstace(Loss loss) {
        commonLossRepository.save(loss);
    }
}
