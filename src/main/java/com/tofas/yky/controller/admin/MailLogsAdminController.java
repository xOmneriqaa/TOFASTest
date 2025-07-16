package com.tofas.yky.controller.admin;
/* T40127 @ 24.10.2015. */

import com.tofas.yky.model.MailLog;
import com.tofas.yky.model.dto.form.MailLogSearchParams;
import com.tofas.yky.service.MailLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/api/mail-logs")
public class MailLogsAdminController {

    @Autowired private MailLogService mailLogService;

    @RequestMapping("/list")
    public List<MailLog> listMailLogs(@RequestBody MailLogSearchParams params) {
        return mailLogService.listMailLogs(params);
    }

}
