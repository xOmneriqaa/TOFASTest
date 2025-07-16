package com.tofas.yky.controller.admin;

import com.tofas.yky.model.dto.DurationDto;
import com.tofas.yky.model.dto.form.DurationSearchDto;
import com.tofas.yky.model.losses.production.duration.Duration;
import com.tofas.yky.service.DurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/api/durations")
public class DurationsController {

    private @Autowired
    DurationService durationService;

    @RequestMapping("/save")
    public boolean save(@RequestBody DurationDto durationDto) {
        return durationService.save(durationDto);
    }

    @RequestMapping("/")
    public List<Duration> list(@RequestBody DurationSearchDto durationSearchDto) {
        return durationService.list(durationSearchDto);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public boolean delete(@PathVariable("id") Long id){
        return durationService.delete(id);
    }

    @RequestMapping("/new")
    public DurationDto getNewDuration() {
        return durationService.getNew();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public DurationDto getDurationDetail(@PathVariable("id") Long id) {
        return durationService.getDuration(id);
    }

    @RequestMapping("/update")
    public boolean update(@RequestBody DurationDto durationDto) {
        return durationService.updateDuration(durationDto);
    }

}
