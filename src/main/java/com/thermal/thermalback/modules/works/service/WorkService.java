package com.thermal.thermalback.modules.works.service;

import com.thermal.thermalback.common.exception.material.MaterialErrorCodeEnum;
import com.thermal.thermalback.common.exception.material.MaterialException;
import com.thermal.thermalback.modules.works.controller.WorkDto;
import com.thermal.thermalback.modules.works.entity.Work;
import com.thermal.thermalback.modules.works.repository.WorkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class WorkService {
    private final WorkRepository materialRepository;

    public List<Work> getAllMaterials() {
        return materialRepository.findAll();
    }

    public void deleteMaterialById(UUID id) {
        materialRepository.deleteById(id);
    }

    public Work getMaterialById(UUID id) throws MaterialException {
        return materialRepository.findById(id).orElseThrow(() -> new MaterialException(MaterialErrorCodeEnum.MATERIAL_NOT_FOUND));
    }

    public Work editMaterial(UUID id, WorkDto materialDto) throws MaterialException {
        Work mat = materialRepository.findById(id).orElseThrow(() -> new MaterialException(MaterialErrorCodeEnum.MATERIAL_NOT_FOUND));

        mat.name(materialDto.name());
        mat.unit(materialDto.unit());
        mat.price(materialDto.price());

        return materialRepository.save(mat);
    }

    public Work saveMaterial(WorkDto materialDto) {
        Work mat = new Work();

        mat.id(UUID.randomUUID());
        mat.name(materialDto.name());
        mat.unit(materialDto.unit());
        mat.price(materialDto.price());

        return materialRepository.save(mat);
    }
}
