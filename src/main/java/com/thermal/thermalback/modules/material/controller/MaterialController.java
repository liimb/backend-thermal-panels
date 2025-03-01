package com.thermal.thermalback.modules.material.controller;

import com.thermal.thermalback.common.exception.material.MaterialException;
import com.thermal.thermalback.modules.material.entity.Material;
import com.thermal.thermalback.modules.material.service.MaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("materials")
public class MaterialController {

    private final MaterialService materialService;

    @GetMapping("/get-all")
    public List<Material> getAllMaterials() {
        return materialService.getAllMaterials();
    }

    @PostMapping("/delete/{id}")
    public void deleteMaterialById(@PathVariable UUID id) {
        materialService.deleteMaterialById(id);
    }

    @PostMapping("/edit/{id}")
    public Material editMaterial(@PathVariable UUID id, @RequestBody MaterialDto materialDto) throws MaterialException {
        return materialService.editMaterial(id, materialDto);
    }

    @GetMapping("/get/{id}")
    public Material getAllMaterials(@PathVariable UUID id) throws MaterialException {
        return materialService.getMaterialById(id);
    }

    @PostMapping("/save")
    public Material editMaterial(@RequestBody MaterialDto materialDto) {
        return materialService.saveMaterial(materialDto);
    }
}
