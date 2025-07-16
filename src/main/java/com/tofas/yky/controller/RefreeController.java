package com.tofas.yky.controller;
/* T40127 @ 19.10.2015. */

import com.tofas.yky.model.VOpenObjection;
import com.tofas.yky.model.dto.ObjectionChangeDto;
import com.tofas.yky.model.dto.ObjectionResultDto;
import com.tofas.yky.service.RefreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/refree/api")
public class RefreeController {

    @Autowired
    RefreeService refreeService;

    @RequestMapping("/total-objected")
    public Long countTotalObjected() {
        return refreeService.countTotalObjected();
    }

    @RequestMapping("/total-my-objected")
    public Long countMyTotalObjected() {
        return refreeService.countMyTotalObjected();
    }

    @RequestMapping("/list-open-objections")
    public List<VOpenObjection> listOpenObjections() {
        return refreeService.listOpenObjections();
    }

    @RequestMapping("/list-open-my-objections")
    public List<VOpenObjection> listMyOpenObjections() {
        return refreeService.listMyOpenObjections();
    }

    @RequestMapping("/change-objection")
    public boolean changeObjection(@RequestBody ObjectionChangeDto dto) {
        return refreeService.changeObjection(dto);
    }

    @RequestMapping("/set-objection-result")
    public boolean setObjectionResult(@RequestBody ObjectionResultDto dto) {
        return refreeService.setObejctionResult(dto);
    }

}
