package com.tofas.yky.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.tofas.yky.model.decoratorbases.impl.VPart;
import com.tofas.yky.model.dto.VPartDto;
import com.tofas.yky.repositories.VPartsRepository;
import org.hibernate.ejb.QueryHints;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Service
public class VPartService {

    private @Resource
    VPartsRepository vPartsRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Cacheable("parts_str")
    public String listAllPartsStr()  {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        try {
            return objectMapper.writeValueAsString(listAllParts());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Cacheable("parts")
    public List<VPartDto> listAllParts() {
        List<VPart> allParts = entityManager.createQuery("Select part from VPart part")
               .setHint(QueryHints.HINT_FETCH_SIZE, 300000)
                .getResultList();
        List<VPartDto> partDtos = new ArrayList<>();

        for(VPart part : allParts) {
            partDtos.add(new VPartDto(part.getDisegno(), part.getDescTr()));
        }

        return partDtos;
    }

    // Todo : Cache temizleme methodu.
    @CacheEvict(value = "parts_str",allEntries = true)
    public void cleanPartsCahce() {

    }
}
