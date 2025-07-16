package com.tofas.yky.controller;


import com.tofas.core.common.utility.TfStaticFileUtility;
import com.tofas.yky.service.TfAppConstantsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;

@Controller
public class LossAttachmentViewController {

    private @Autowired
    TfStaticFileUtility tfStaticFileUtility;

    private @Autowired
    TfAppConstantsService tfAppConstantsService;

    @RequestMapping(value = "/loss-attachment/{file_name:.+}", method = RequestMethod.GET)
    public void getFile(@PathVariable("file_name") String fileName, HttpServletResponse response) {
        tfStaticFileUtility.extractFile(tfAppConstantsService.getLossAttachmentPath(fileName), response);
    }
}
