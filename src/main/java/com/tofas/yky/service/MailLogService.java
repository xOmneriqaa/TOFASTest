package com.tofas.yky.service;
/* T40127 @ 24.10.2015. */

import com.tofas.core.common.utility.TfSpecifications;
import com.tofas.yky.model.MailLog;
import com.tofas.yky.model.MailLog_;
import com.tofas.yky.model.dto.form.MailLogSearchParams;
import com.tofas.yky.repositories.MailLogRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;
import java.util.List;

@Service
public class MailLogService {

    @Resource
    MailLogRepository mailLogRepository;

    public List<MailLog> listMailLogs(final MailLogSearchParams params) {
        TfSpecifications<MailLog> specifications = TfSpecifications.getSpecification();

        if(params.getStartDate() != null) {
            specifications.and((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get(MailLog_.mailDate), new Timestamp(params.getStartDate().getTime())));
        }

        if(params.getEndDate() != null) {
            specifications.and((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get(MailLog_.mailDate), new Timestamp(params.getEndDate().getTime())));
        }

        if(params.getCcAddress() != null) {
            specifications.and(
                    (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get(MailLog_.cc), params.getCcAddress()));
        }

        if(params.getToAddress() != null) {
            specifications.and(
                    (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get(MailLog_.to), params.getToAddress()));
        }

        if(params.getSubject() != null) {
            specifications.and(
                    (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get(MailLog_.subject), params.getSubject()));
        }

        if(params.getBody() != null) {
            specifications.and(
                    (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get(MailLog_.body), params.getBody()));
        }

        return mailLogRepository.findAll(specifications);
    }
}
