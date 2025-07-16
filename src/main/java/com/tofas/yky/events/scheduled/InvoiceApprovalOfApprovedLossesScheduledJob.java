package com.tofas.yky.events.scheduled;
/* T40127 @ 15.11.2015. */

import com.google.common.eventbus.EventBus;
import com.tofas.yky.events.DiscardedPartDoNotHaveBasePriceEvent;
import com.tofas.yky.events.LossIsCanceledDueToNotAnsweredObjectionEvent;
import com.tofas.yky.events.LossIsInvoiceApprovedEvent;
import com.tofas.yky.events.scheduled.returntype.LossInvoiceEventReturn;
import com.tofas.yky.model.additional.ISupplierReferencedLoss;
import com.tofas.yky.model.losses.Loss;
import com.tofas.yky.model.losses.views.VInvoiceApproveReadyLoss;
import com.tofas.yky.repositories.CommonLossRepository;
import com.tofas.yky.repositories.VInvoiceApproveReadyLossRepository;
import com.tofas.yky.service.TfAppConstantsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class InvoiceApprovalOfApprovedLossesScheduledJob extends AbstractLoggableScheduledJob {

	private final VInvoiceApproveReadyLossRepository vInvoiceApproveReadyLossRepository;

	private final CommonLossRepository commonLossRepository;

	private final EventBus eventBus;

	private final TfAppConstantsService tfAppConstantsService;

	@Autowired
	public InvoiceApprovalOfApprovedLossesScheduledJob(
			VInvoiceApproveReadyLossRepository vInvoiceApproveReadyLossRepository,
			CommonLossRepository commonLossRepository, EventBus eventBus, TfAppConstantsService tfAppConstantsService) {
		super("Onayli Kayiplari Faturaliya Cevirme");
		this.vInvoiceApproveReadyLossRepository = vInvoiceApproveReadyLossRepository;
		this.commonLossRepository = commonLossRepository;
		this.eventBus = eventBus;
		this.tfAppConstantsService = tfAppConstantsService;
	}

	@Scheduled(cron = "0 00 8 * * *")
	public void run() {
		if (!canRun())
			return;

		logStartTime();

		List<VInvoiceApproveReadyLoss> invoiceApproveReadyLosses = vInvoiceApproveReadyLossRepository.findAll();

		for (VInvoiceApproveReadyLoss invoiceReadyLoss : invoiceApproveReadyLosses) {
			try {
				Loss loss = commonLossRepository.getLoss(invoiceReadyLoss.getId(), invoiceReadyLoss.getLossType());

				LossInvoiceEventReturn eventReturn = loss.processInvoiceApproveEvent(
						tfAppConstantsService.getNumOfDaysForExtraSerieSupplier(),
						tfAppConstantsService.getNumOfDaysForInternalSupplier(),
						tfAppConstantsService.getNumOfDaysNotAnsweredObjections(),
						tfAppConstantsService.getNumOfDaysDeniedObjections());

				switch (eventReturn) {
				case CANCEL_DUE_TO_NOT_ANSWERED_OBJECTION:
					cancelLossDueToNotAnsweredObjection(loss);
					break;
				case INVOICE_APPROVE:
					invoiceApprove(loss);
					break;
				case SAVE:
					saveLoss(loss);
					break;
				case DISCARDED_PART_UNDEFINED_BASE_PRICE:
					discardedPartUndefinedBasePrice(loss);
					break;
				case DO_NOTHING:
					// do nothing
				}
			} catch (Exception e) {
				System.out.println("----------------------------------------------------------------------------------------------------------");
				System.out.println(invoiceReadyLoss.toString());
				System.out.println(invoiceReadyLoss.getId());
			}
		}

		logEndTime();
	}

	private void cancelLossDueToNotAnsweredObjection(Loss loss) {
		loss.cancelLoss("SYS: Itiraz cevabi olmadigi icin iptal edilmistir!");
		saveLoss(loss);

		eventBus.post(new LossIsCanceledDueToNotAnsweredObjectionEvent(loss.getId()));
	}

	private void invoiceApprove(Loss loss) {
		loss.invoiceApprove();
		//	TODO statu invoiced_approved olurken o gunki kur degeri Loss objesi eur değerine atılmalı

		boolean isPoloFirm = false;
		if (loss instanceof ISupplierReferencedLoss) {
			String poloFlag = ((ISupplierReferencedLoss) loss).getSupplier().getPoloFlag();
			isPoloFirm = poloFlag != null && poloFlag.trim().length() > 0;

			// all firms that their codes starting with 4 must not be invoiced
			// from SAP
			isPoloFirm |= ((ISupplierReferencedLoss) loss).getSupplier().getSapCode().startsWith("4");
		}

		if (isPoloFirm) {
			loss.externalFirmNotInivoiced();
		} else {
			eventBus.post(new LossIsInvoiceApprovedEvent(loss.getId()));
		}

		saveLoss(loss);
	}

	private void saveLoss(Loss loss) {
		commonLossRepository.save(loss);
	}

	private void discardedPartUndefinedBasePrice(Loss loss) {
		eventBus.post(new DiscardedPartDoNotHaveBasePriceEvent(loss.getId()));
	}

}
