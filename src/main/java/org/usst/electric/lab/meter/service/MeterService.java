package org.usst.electric.lab.meter.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.usst.electric.lab.meter.jpa.model.DataEntity;
import org.usst.electric.lab.meter.jpa.model.MeterEntity;
import org.usst.electric.lab.meter.jpa.repository.IMeterDataRepository;
import org.usst.electric.lab.meter.jpa.repository.IMeterRepository;

@Service
public class MeterService
{
    private final IMeterDataRepository _DataRepository;
    private final IMeterRepository     _MeterRepository;

    @Autowired
    public MeterService(IMeterDataRepository dataRepository,
                        IMeterRepository meterRepository)
    {
        _DataRepository = dataRepository;
        _MeterRepository = meterRepository;
    }

    public MeterEntity addMeter(MeterEntity entity)
    {
        return _MeterRepository.findBySn(entity.getSn())
                               .orElseGet((() -> _MeterRepository.save(entity)));
    }

    public DataEntity addData(DataEntity entity)
    {
        return _DataRepository.save(entity);
    }

    public Page<DataEntity> findAllData(long meterId, LocalDateTime start, LocalDateTime end, Pageable pageable)
    {
        return _DataRepository.findAll((Specification<DataEntity>) (root, criteriaQuery, criteriaBuilder) ->
        {
            return criteriaQuery.where(criteriaBuilder.equal(root.get("meter_id"), meterId),
                                       criteriaBuilder.between(root.get("created_at"), start, end))
                                .getRestriction();

        }, pageable);
    }

}
