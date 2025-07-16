package com.tofas.yky.service.press;
/* t40127 @ 26.04.2016. */

import com.tofas.yky.model.dto.press.detail.AbstractPressLossDetailDto;
import com.tofas.yky.service.press.upload.TfExcelError;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PressUploadComponent {

    private List<TfExcelError> errors = new ArrayList<>();

    private List<AbstractPressLossDetailDto> details = new ArrayList<>();

    public List<TfExcelError> getErrors() {
        return errors;
    }

    public List<AbstractPressLossDetailDto> getDetails() {
        return details;
    }


    public List<TfExcelError> returnErrors() {
        List<TfExcelError> errorsCopied = new ArrayList<>();
        errorsCopied.addAll(errors);

        errors.clear();

        return errorsCopied;
    }


    public List<AbstractPressLossDetailDto> returnDetails() {
        List<AbstractPressLossDetailDto> detailsReturned = new ArrayList<>();
        detailsReturned.addAll(details);

        details.clear();

        return detailsReturned;
    }

}
