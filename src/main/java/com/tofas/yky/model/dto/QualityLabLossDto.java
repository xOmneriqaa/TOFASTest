package com.tofas.yky.model.dto;

import com.tofas.yky.model.dto.additional.IQualityLabTestAddableDto;
import com.tofas.yky.model.dto.additional.IWorkDurationAddableLossDto;

import java.util.List;

public class QualityLabLossDto extends AbstractLossDto implements IWorkDurationAddableLossDto, IQualityLabTestAddableDto {

    private String disegno;

    private String supplier;

    private List<QualityLabTestDto> qualityLabTests;

    private List<WorkDurationDto> workDurations;

    private Long sqpNo;

    private String requestNo;

    private String willSqpOpen;

    private Long approverRoleId;

    public String getDisegno() {
        return disegno;
    }

    public void setDisegno(String disegno) {
        this.disegno = disegno;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public List<QualityLabTestDto> getQualityLabTests() {
        return qualityLabTests;
    }

    public void setQualityLabTests(List<QualityLabTestDto> qualityLabTests) {
        this.qualityLabTests = qualityLabTests;
    }

    public List<WorkDurationDto> getWorkDurations() {
        return workDurations;
    }

    public void setWorkDurations(List<WorkDurationDto> workDurations) {
        this.workDurations = workDurations;
    }

    public Long getSqpNo() { return sqpNo; }

    public void setSqpNo(Long sqpNo) { this.sqpNo = sqpNo; }

    public String getRequestNo() {
        return requestNo;
    }

    public void setRequestNo(String requestNo) {
        this.requestNo = requestNo;
    }

    public String getWillSqpOpen() {
        return willSqpOpen;
    }

    public void setWillSqpOpen(String willSqpOpen) {
        this.willSqpOpen = willSqpOpen;
    }

    public Long getApproverRoleId() {
        return approverRoleId;
    }

    public void setApproverRole(Long approverRoleId) {
        this.approverRoleId = approverRoleId;
    }

    @Override
    public String toString() {
        return "QualityLabLossDto{" +
                "workDurations=" + workDurations +
                ", disegno='" + disegno + '\'' +
                ", supplier='" + supplier + '\'' +
                ", sqpNo='" + sqpNo + '\'' +
                ", requestNo='" + requestNo + '\'' +
                ", willSqpOpen='" + willSqpOpen + '\'' +
                ", approverRole='" + approverRoleId + '\'' +
                ", qualityLabTests=" + qualityLabTests +
                '}';
    }
}
