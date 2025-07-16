package com.tofas.yky.service.report;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tofas.core.common.utility.TfAuthUtility;
import com.tofas.core.common.utility.TfSpecifications;
import com.tofas.core.common.utility.TfStringUtility;
import com.tofas.yky.components.SupplierUserInfo;
import com.tofas.yky.model.dto.report.MainReportSearchParamsDto;
import com.tofas.yky.model.report.LossDetailReportObject;
import com.tofas.yky.model.report.MainReportObject;
import com.tofas.yky.model.report.SqpReportObject;
import com.tofas.yky.model.report.SqpReportObject_;
import com.tofas.yky.repositories.LossDetailReportRepository;
import com.tofas.yky.repositories.SqpReportRepository;


@Service
public class SqpReportService {

	@Autowired
	private SqpReportRepository sqpReportRepository;
	
	@Autowired
    private  SupplierUserInfo supplierUserInfo;
	
	@Autowired
	private TfStringUtility stringUtility;
	
	public List<SqpReportObject> sqpReportObjectList(final MainReportSearchParamsDto searchParams){
		TfSpecifications<SqpReportObject> specs=TfSpecifications.getSpecification();
		System.out.println("----------------------------------------------------------------------");
		System.out.println(searchParams.toString());
		
//		  if(searchParams.getLossType() != null && searchParams.getLossType().trim().length() > 0) {
//	            specs.and((root, query, cb) -> cb.equal(root.get(SqpReportObject_.lossType), searchParams.getLossType()));
//	        }
//		
//		  if(searchParams.getSupplierCode() != null && searchParams.getSupplierCode().trim().length() > 0) {
//	            specs.and((root, query, cb)
//	                    -> cb.equal(root.get(SqpReportObject_.supplierCode), searchParams.getSupplierCode()));
//	        }
//		  
		  if(searchParams.getSqpNo() != null && searchParams.getSqpNo().trim().length() > 0) {
	            specs.and((root, query, cb) -> cb.equal(root.get(SqpReportObject_.sqp), searchParams.getSqpNo()));
	        }
		  if(searchParams.getLossDateStart() != null){
	            specs.and(
	                    (root, query, cb) -> cb.greaterThanOrEqualTo(root.get(SqpReportObject_.lossDate), searchParams.getLossDateStart()));
	        }

	        if(searchParams.getLossDateEnd() != null) {
	            specs.and(
	                    (root, query, cb) -> cb.lessThanOrEqualTo(root.get(SqpReportObject_.lossDate), searchParams.getLossDateEnd()));
	        }
	        if(searchParams.getModel() != null && searchParams.getModel().trim().length() > 0) {
	            specs.and((root, query, cb) -> cb.equal(root.get(SqpReportObject_.modelCode), searchParams.getModel()));
	        }
	        if(searchParams.getDisegno() != null && searchParams.getDisegno().trim().length() > 0) {
	            specs.and(
	                    (root, query, cb) -> cb.like(root.get(SqpReportObject_.disegno), ("%" + searchParams.getDisegno() + "%")));
	        }
//
//		
//		 if(searchParams.getLossIds() != null && searchParams.getLossIds().trim().length() > 0) {
//            String[] lossIdsStr = searchParams.getLossIds().split(",");
//            final List<Long> lossIds = new ArrayList<>();
//            for(String lossIdStr : lossIdsStr) {
//                lossIds.add(stringUtility.parseLong(lossIdStr.trim(), -1L));
//            }
//
//            specs.and((root, query, cb) -> root.get(SqpReportObject_.id).in(lossIds));
//        }
//		
		
        //firma kendi verilerini goruyor
        // supplier user spec
//        String username = TfAuthUtility.getUsername();
//        if(username != null && username.trim().length() > 0 && username.charAt(0) == 'S') {
//            specs.and((root, query, cb) -> cb.equal(root.get(SqpReportObject_.supplierCode), supplierUserInfo.getSupplier().getSapCode()));
//        }
        // TODO SPEC GELNCE BUNU YAZ List<SqpReportObject> reportResult =  sqpReportRepository.findAll(specs);
//		  Double totalCost;
        List<SqpReportObject> reportResult =  sqpReportRepository.findAll(specs);
//        for (int i = 0; i < reportResult.size(); i++) { //TODO sqp bazında ilk listi al sonra icinde line 1 leri pas geç. TOTAL 
//			if(reportResult.)
//		}
		return reportResult;
	}
}
