package com.thermal.thermalback.modules.material.controller;

import com.thermal.thermalback.modules.material.entity.Material;
import com.thermal.thermalback.modules.material.service.MaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("material")
public class MaterialController {

    private final MaterialService materialService;

    @GetMapping("/get-all-materials")
    public List<Material> getAllMaterials() {
        return materialService.getAllMaterials();
    }
}
