package com.tofas.yky.service;

import com.google.common.eventbus.EventBus;
import com.tofas.core.common.utility.TfAuthUtility;
import com.tofas.yky.events.LossIsCommentedEvent;
import com.tofas.yky.model.dto.LossCommentDto;
import com.tofas.yky.repositories.LossCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class LossCommentService {


    @Resource
    private LossCommentRepository lossCommentRepository;

    @Autowired
    EventBus eventBus;

    public boolean addComment(LossCommentDto commentDto) {
        lossCommentRepository.insertComment(commentDto.getLossId(), commentDto.getMessage(), new Date(), TfAuthUtility.getUsername());
        eventBus.post(new LossIsCommentedEvent(commentDto));
        return true;
    }
}
