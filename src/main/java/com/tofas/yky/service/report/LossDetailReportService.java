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
import com.tofas.yky.model.report.LossDetailReportObject_;
import com.tofas.yky.model.report.MainReportObject;
import com.tofas.yky.repositories.LossDetailReportRepository;

@Service
public class LossDetailReportService {

	@Autowired
	private LossDetailReportRepository lossDetailReportRepository;
	
	@Autowired
    private  SupplierUserInfo supplierUserInfo;
	
	@Autowired
	private TfStringUtility stringUtility;
	
	public List<LossDetailReportObject> lossDetailReportObjectList(final MainReportSearchParamsDto searchParams){
		TfSpecifications<LossDetailReportObject> specs=TfSpecifications.getSpecification();
		System.out.println("----------------------------------------------------------------------");
		System.out.println(searchParams.toString());
		
		  if(searchParams.getLossType() != null && searchParams.getLossType().trim().length() > 0) {
	            specs.and((root, query, cb) -> cb.equal(root.get(LossDetailReportObject_.lossType), searchParams.getLossType()));
	        }
		
		  if(searchParams.getSupplierCode() != null && searchParams.getSupplierCode().trim().length() > 0) {
	            specs.and((root, query, cb)
	                    -> cb.equal(root.get(LossDetailReportObject_.supplierCode), searchParams.getSupplierCode()));
	        }
		  
		  if(searchParams.getSqpNo() != null && searchParams.getSqpNo().trim().length() > 0) {
	            specs.and((root, query, cb) -> cb.equal(root.get(LossDetailReportObject_.sqpNo), searchParams.getSqpNo()));
	        }

		
		 if(searchParams.getLossIds() != null && searchParams.getLossIds().trim().length() > 0) {
            String[] lossIdsStr = searchParams.getLossIds().split(",");
            final List<Long> lossIds = new ArrayList<>();
            for(String lossIdStr : lossIdsStr) {
                lossIds.add(stringUtility.parseLong(lossIdStr.trim(), -1L));
            }

            specs.and((root, query, cb) -> root.get(LossDetailReportObject_.id).in(lossIds));
        }
		
		
        //firma kendi verilerini goruyor
        // supplier user spec
        String username = TfAuthUtility.getUsername();
        if(username != null && username.trim().length() > 0 && username.charAt(0) == 'S') {
            specs.and((root, query, cb) -> cb.equal(root.get(LossDetailReportObject_.supplierCode), supplierUserInfo.getSupplier().getSapCode()));
        }
        List<LossDetailReportObject> reportResult =  lossDetailReportRepository.findAll(specs);
		return reportResult;
	}
}
