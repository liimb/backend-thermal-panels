package com.thermal.thermalback.modules.works.controller;

import com.thermal.thermalback.common.exception.material.MaterialException;
import com.thermal.thermalback.common.exception.work.WorkException;
import com.thermal.thermalback.modules.works.entity.Work;
import com.thermal.thermalback.modules.works.service.WorkService;
import lombok.RequiredArgsConstructor;
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

    @PostMapping("/delete/{id}")
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
    public Work editWork(@RequestBody WorkDto workDto) {
        return workService.saveMaterial(workDto);
    }
}
