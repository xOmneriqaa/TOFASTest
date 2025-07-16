package com.tofas.yky.model.losses.press.pos;
/* t40127 @ 25.04.2016. */

import com.tofas.yky.model.decoratorbases.decorators.VPartDecorator;
import com.tofas.yky.model.decoratorbases.decorators.VSupplierDecorator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(schema = "TFS_YKY", name = "V_PRESS_ROLL_DETAIL")
public class VPosRollDetail {

    @Id
    @Column(name = "ROLL_NO")
    private String rollNo;

    @Column(name = "SHIP_NAME")
    private String shipName;

    @Column(name = "ROLL_WEIGHT")
    private BigDecimal rollWeight;

    @Column(name = "INVOICE_NO")
    private String invoiceNo;

    @Column(name = "INVOICE_DATE")
    private Date invoiceDate;

    @Column(name = "FACTORY_ENTRANCE_DATE")
    private Date factoryEntranceDate;

    @Embedded
    private VPartDecorator part;

    @ManyToOne
    @JoinColumn(name = "DISEGNO")
    @NotFound(action = NotFoundAction.IGNORE)
    private VPosDisegnoDetail disegnoDetail;

    @Embedded
    private VSupplierDecorator supplier;

    public VPosDisegnoDetail getDisegnoDetail() {
        return disegnoDetail;
    }

    public void setDisegnoDetail(VPosDisegnoDetail disegnoDetail) {
        this.disegnoDetail = disegnoDetail;
    }

    public String getRollNo() {
        return rollNo;
    }


    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public BigDecimal getRollWeight() {
        return rollWeight == null ? BigDecimal.ZERO : rollWeight;
    }

    public void setRollWeight(BigDecimal rollWeight) {
        this.rollWeight = rollWeight;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Date getFactoryEntranceDate() {
        return factoryEntranceDate;
    }

    public void setFactoryEntranceDate(Date factoryEntranceDate) {
        this.factoryEntranceDate = factoryEntranceDate;
    }

    public VPartDecorator getPart() {
        return part;
    }

    public void setPart(VPartDecorator part) {
        this.part = part;
    }

    public VSupplierDecorator getSupplier() {
        return supplier;
    }

    public void setSupplier(VSupplierDecorator supplier) {
        this.supplier = supplier;
    }
}
