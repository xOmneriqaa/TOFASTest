package com.tofas.yky.repositories;
/* T40127 @ 16.10.2015. */

import com.tofas.core.common.utility.TfAuthUtility;
import com.tofas.yky.model.decoratorbases.impl.VSupplier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class SupplierUserRepositoryImpl {

    @PersistenceContext private EntityManager em;

    @Resource private VSupplierRepository vSupplierRepository;


    public VSupplier getSupplierFromUser() {
        String strUsername = TfAuthUtility.getUsername();
        Query query = em.createNativeQuery("select TFS_SUPP.API_IDM_SUPPLIER_USERS.firma_kodu_al(:username) from dual");
        query.setParameter("username", strUsername);
        Object supplierId = query.getSingleResult();

        if(supplierId != null) {
            return vSupplierRepository.findByCode(Long.parseLong(supplierId.toString()));
        } else {
            return null;
        }
    }

    public String[] getSupplierQualityRespMails(VSupplier supplier) {
        Query query = em.createNativeQuery("select e_mail, e_mail2 from tfs_firma.firma_tofas_sorumlulari where firma_kodu = :firma and sorumlu = 1");
        query.setParameter("firma", supplier.getCode());
        Object qualityRespMails = query.getSingleResult();

        List<String> mails = new ArrayList<>();
        if(qualityRespMails != null) {
            Object[] qualityRespMailArr = (Object[]) qualityRespMails;

            for(Object mail : qualityRespMailArr) {
                if(mail != null) {
                    mails.add(mail.toString());
                }
            }
        }

        return mails.toArray(new String[mails.size()]);
    }

}
