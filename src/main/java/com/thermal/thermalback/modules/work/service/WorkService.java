package com.thermal.thermalback.modules.work.service;

import com.thermal.thermalback.common.exception.work.WorkErrorCodeEnum;
import com.thermal.thermalback.common.exception.work.WorkException;
import com.thermal.thermalback.modules.work.controller.WorkDto;
import com.thermal.thermalback.modules.work.entity.Work;
import com.thermal.thermalback.modules.work.repository.WorkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class WorkService {
    private final WorkRepository workRepository;

    public List<Work> getAllWorks() {
        return workRepository.findAll();
    }

    public void deleteWorkById(UUID id) {
        workRepository.deleteById(id);
    }

    public Work getWorkById(UUID id) throws WorkException {
        return workRepository.findById(id).orElseThrow(() -> new WorkException(WorkErrorCodeEnum.WORK_NOT_FOUND));
    }

    public Work editWork(UUID id, WorkDto workDto) throws WorkException {
        Work work = workRepository.findById(id).orElseThrow(() -> new WorkException(WorkErrorCodeEnum.WORK_NOT_FOUND));

        work.name(workDto.name());
        work.unit(workDto.unit().getValue());
        work.price(workDto.price());

        return workRepository.save(work);
    }

    public Work saveWork(WorkDto workDto) {
        Work work = new Work();

        work.id(UUID.randomUUID());
        work.name(workDto.name());
        work.unit(workDto.unit().getValue());
        work.price(workDto.price());

        return workRepository.save(work);
    }
}
