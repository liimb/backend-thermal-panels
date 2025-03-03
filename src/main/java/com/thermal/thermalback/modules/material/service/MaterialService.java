package com.thermal.thermalback.modules.material.service;

import com.thermal.thermalback.common.exception.material.MaterialErrorCodeEnum;
import com.thermal.thermalback.common.exception.material.MaterialException;
import com.thermal.thermalback.modules.material.controller.MaterialDto;
import com.thermal.thermalback.modules.material.entity.Material;
import com.thermal.thermalback.modules.material.repository.MaterialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class MaterialService {
    private final MaterialRepository materialRepository;

    public List<Material> getAllMaterials() {
        return materialRepository.findAll();
    }

    public void deleteMaterialById(UUID id) {
        materialRepository.deleteById(id);
    }

    public Material getMaterialById(UUID id) throws MaterialException {
        return materialRepository.findById(id).orElseThrow(() -> new MaterialException(MaterialErrorCodeEnum.MATERIAL_NOT_FOUND));
    }

    public Material editMaterial(UUID id, MaterialDto materialDto) throws MaterialException {
        Material mat = materialRepository.findById(id).orElseThrow(() -> new MaterialException(MaterialErrorCodeEnum.MATERIAL_NOT_FOUND));

        mat.name(materialDto.name());
        mat.unit(materialDto.unit().getValue());
        mat.price(materialDto.price());

        return materialRepository.save(mat);
    }

    public Material saveMaterial(MaterialDto materialDto) {
        Material mat = new Material();

        mat.id(UUID.randomUUID());
        mat.name(materialDto.name());
        mat.unit(materialDto.unit().getValue());
        mat.price(materialDto.price());

        return materialRepository.save(mat);
    }
}
