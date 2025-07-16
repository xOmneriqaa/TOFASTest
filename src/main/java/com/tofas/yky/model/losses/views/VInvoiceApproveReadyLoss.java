package com.tofas.yky.model.losses.views;
/* T40127 @ 23.11.2015. */

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(schema = "TFS_YKY", name = "V_INVOICE_APPROVE_READY_LOSSES")
public class VInvoiceApproveReadyLoss extends VInvoicableLoss { }
