package com.tofas.yky.repositories;

import com.tofas.yky.enums.LossState;
import com.tofas.yky.enums.LossType;
import com.tofas.yky.model.losses.Loss;
import com.tofas.yky.model.losses.logistics.LogisticsLoss;
import com.tofas.yky.model.losses.press.PressLoss;
import com.tofas.yky.model.losses.production.ProductionLoss;
import com.tofas.yky.model.losses.quality.QualityLoss;
import com.tofas.yky.model.losses.qualitylab.QualityLabLoss;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Repository
@Transactional
public class CommonLossRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public String getLossType(Long lossId) {
        Query query = entityManager.createNativeQuery("SELECT LOSS_TYPE FROM TFS_YKY.YKY_LOSSES WHERE ID = :lossId");
        query.setParameter("lossId", lossId);
        List result = query.getResultList();

        if(result.size() == 1) {
            return result.get(0).toString();
        } else {
            return "";
        }
    }

    //itiraz mail listesi
    public String getEmailOfUser(String username) {
        if(username != null) {
            Query query;
            if(username.startsWith("S")) {
                query = entityManager.createNativeQuery("select e_mail from tfs_supp.idm_supplier_users where user_name = :username");
            } else {
                query = entityManager.createNativeQuery("select email from tfs_lib.vw_employees where pernr = :username");
                username = "000" + username.substring(1);
            }

            query.setParameter("username", username);
            List result = query.getResultList();

            if(result.size() == 1) {
                return result.get(0).toString();
            }
        }

        return null;
    }

    public List<String> getEmailsOfUsers(Collection<String> users) {
        List<String> mails = new ArrayList<>();
        for (String user : users) {
            String email = getEmailOfUser(user);
            if(email != null) {
                mails.add(email);
            }
        }
        return mails;
    }

    public List<String> getEmailsOfBuyers(String disegno) {
        Query query = entityManager.createNativeQuery("select ('T' || trim(to_char(det.buyer, '00000'))) username  from TFS_LIB.vw_product_line_details det\n" +
                "    where (det.urun_sektor, det.urun_aile) in " +
                "       (select pr.urun_sektor, pr.urun_aile from TFS_LIB.vw_product_line_parts pr where pr.resim_no = :disegno)");
        query.setParameter("disegno", disegno);

        List resultList = query.getResultList();
        return getEmailsOfUsers(resultList);
    }

    public Loss getLoss(Long lossId) {
        String lossTypeStr = getLossType(lossId);

        try {
            LossType lossType = LossType.valueOf(lossTypeStr);

            return getLoss(lossId, lossType);
        } catch (Exception e) {
            //e.printStackTrace();
        }



        return null;
    }

    public Loss getLoss(Long lossId, LossType lossType) {
        switch (lossType) {
            case LOGISTICS:
                return entityManager.find(LogisticsLoss.class, lossId);
            case PRODUCTION:
                return entityManager.find(ProductionLoss.class, lossId);
            case QUALITY:
                return entityManager.find(QualityLoss.class, lossId);
            case QUALITY_LAB:
                return entityManager.find(QualityLabLoss.class, lossId);
            case PRESS:
                return entityManager.find(PressLoss.class, lossId);
        }

        return null;
    }

    public Long getSupplierCodeOfLoss(Long lossId, LossType lossType) {
        Query supplierQuery = null;
        switch (lossType) {
            case LOGISTICS:
                supplierQuery = entityManager.createQuery("Select s.code from VSupplier s where s.sapCode in (select l.supplier.sapCode from LogisticsLoss l where l.id = :id)", Long.class);
                break;
            case PRODUCTION:
                supplierQuery = entityManager.createQuery("Select s.code from VSupplier s where s.sapCode in (select l.supplier.sapCode from ProductionLoss l where l.id = :id)", Long.class);
                break;
            case QUALITY:
                supplierQuery = entityManager.createQuery("Select s.code from VSupplier s where s.sapCode in (select l.supplier.sapCode from QualityLoss l where l.id = :id)", Long.class);
                break;
            case QUALITY_LAB:
                supplierQuery = entityManager.createQuery("Select s.code from VSupplier s where s.sapCode in (select l.supplier.sapCode from QualityLabLoss l where l.id = :id)", Long.class);
                break;
        }

        if(supplierQuery == null) {
            return null;
        } else {
            supplierQuery.setParameter("id", lossId);
            return (Long) supplierQuery.getSingleResult();
        }
    }

    @Transactional
    public void save(Loss loss) {
        entityManager.merge(loss);
    }

    public boolean cancelLoss(Long lossId, String cancelDesc) {
        Loss loss = getLoss(lossId);
        if(loss != null) {
            loss.cancelLoss(cancelDesc);

            save(loss);
        }

        return true;
    }

    @Transactional
    public boolean pauseLoss(Long lossId) {
        Loss loss = getLoss(lossId);

        if(loss != null) {
            LossState currentState = loss.getCurrentState();
            if(currentState == LossState.APPROVED
                    || currentState == LossState.INVOICE_APPROVED) {
                loss.pauseLoss();

                save(loss);

                return true;
            } else if(currentState == LossState.PAUSED) {
                loss.approveLoss();

                save(loss);

                return true;
            }
        }

        return false;
    }
}
