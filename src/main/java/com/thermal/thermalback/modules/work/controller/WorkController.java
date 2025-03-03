package com.thermal.thermalback.modules.work.controller;

import com.thermal.thermalback.common.exception.work.WorkException;
import com.thermal.thermalback.modules.work.entity.Work;
import com.thermal.thermalback.modules.work.service.WorkService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("works")
public class WorkController {

    private final WorkService workService;

    @GetMapping("/get-all")
    public List<Work> getAllWorks() {
        return workService.getAllWorks();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteWorkById(@PathVariable UUID id) {
        workService.deleteWorkById(id);
    }

    @PostMapping("/edit/{id}")
    public Work editWork(@PathVariable UUID id, @RequestBody WorkDto workDto) throws WorkException {
        return workService.editWork(id, workDto);
    }

    @GetMapping("/get/{id}")
    public Work getWorkById(@PathVariable UUID id) throws WorkException {
        return workService.getWorkById(id);
    }

    @PostMapping("/save")
    public Work saveWork(@RequestBody WorkDto workDto) {
        return workService.saveWork(workDto);
    }
}
