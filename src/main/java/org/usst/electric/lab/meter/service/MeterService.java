package org.usst.electric.lab.meter.service;

import com.isahl.chess.king.base.cron.ScheduleHandler;
import com.isahl.chess.king.base.cron.TimeWheel;
import com.isahl.chess.king.base.cron.features.ICancelable;
import com.isahl.chess.pawn.endpoint.device.api.db.model.MessageEntity;
import com.isahl.chess.pawn.endpoint.device.api.db.repository.IMessageJpaRepository;
import com.isahl.chess.rook.storage.cache.config.EhcacheConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.usst.electric.lab.meter.jpa.model.DataEntity;
import org.usst.electric.lab.meter.jpa.model.MeterEntity;
import org.usst.electric.lab.meter.jpa.repository.IDataRepository;
import org.usst.electric.lab.meter.jpa.repository.IMeterRepository;

import javax.annotation.PostConstruct;
import javax.cache.CacheManager;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.MINUTES;

@Service
public class MeterService
        implements ICancelable
{
    private final IDataRepository       _DataRepository;
    private final IMeterRepository      _MeterRepository;
    private final IMessageJpaRepository _MessageRepository;
    private final CacheManager          _CacheManager;
    private final AtomicLong            _LastUpdated = new AtomicLong(Long.MIN_VALUE + 1);
    private final TimeWheel             _TimeWheel;
    ;
    private final ScheduleHandler<MeterService> _BatchUpdateHandler = new ScheduleHandler<>(Duration.ofSeconds(10),
                                                                                            true,
                                                                                            this::batchUpdate);

    @Autowired
    public MeterService(IDataRepository dataRepository,
                        IMeterRepository meterRepository,
                        IMessageJpaRepository messageRepository,
                        CacheManager cacheManager,
                        TimeWheel timeWheel)
    {
        _DataRepository = dataRepository;
        _MeterRepository = meterRepository;
        _MessageRepository = messageRepository;
        _CacheManager = cacheManager;
        _TimeWheel = timeWheel;
    }

    @PostConstruct
    void initialize() throws ClassNotFoundException, InstantiationException, IllegalAccessException
    {
        EhcacheConfig.createCache(_CacheManager, "meter_sn", String.class, Page.class, Duration.of(20, MINUTES));
        EhcacheConfig.createCache(_CacheManager,
                                  "meter_device",
                                  Long.class,
                                  MeterEntity.class,
                                  Duration.of(20, MINUTES));
        _TimeWheel.acquire(this, _BatchUpdateHandler);
    }

    public MeterEntity addMeter(MeterEntity entity)
    {
        return _MeterRepository.findBySn(entity.getSn())
                               .orElseGet((()->_MeterRepository.save(entity)));
    }

    public DataEntity addData(DataEntity entity)
    {
        return _DataRepository.save(entity);
    }

    private Page<DataEntity> findAllDataInRegion(long meterId,
                                                 LocalDateTime start,
                                                 LocalDateTime end,
                                                 Pageable pageable)
    {
        return _DataRepository.findAll((Specification<DataEntity>) (root, criteriaQuery, criteriaBuilder)->{
            return criteriaQuery.where(criteriaBuilder.equal(root.get("meterId"), meterId),
                                       criteriaBuilder.between(root.get("createdAt"), start, end))
                                .getRestriction();

        }, pageable);
    }

    @Cacheable(value = "meter_device",
               key = "#deviceId",
               unless = "#deviceId ==0",
               condition = "#result !=null")
    public MeterEntity findMeterByDeviceId(long deviceId)
    {
        return _MeterRepository.findByDeviceId(deviceId);
    }

    @Override
    public void cancel()
    {

    }

    void batchUpdate(MeterService self)
    {
        //TODO 通过shadow-device 只处理那些需要管理状态的device
        try {
            List<MessageEntity> batchUpdate = _MessageRepository.findAll((Specification<MessageEntity>) (root, criteriaQuery, criteriaBuilder)->{
                return criteriaQuery.where(criteriaBuilder.greaterThan(root.get("id"), _LastUpdated.get()))
                                    .getRestriction();
            });
            if(!batchUpdate.isEmpty()) {
                List<DataEntity> batchData = batchUpdate.stream()
                                                        .map(msg->{
                                                            long id = msg.getId();
                                                            if(id > _LastUpdated.get()) {
                                                                _LastUpdated.set(id);
                                                            }
                                                            MeterEntity meter = findMeterByDeviceId(msg.getOrigin());
                                                            if(meter != null) {
                                                                DataEntity data = new DataEntity();
                                                                data.setData(msg.getContent());
                                                                data.setMeter(meter);
                                                                data.setId(msg.getId());
                                                                data.setCreatedAt(msg.getCreatedAt());
                                                                data.setUpdatedAt(msg.getUpdatedAt());
                                                                return data;
                                                            }
                                                            return null;
                                                        })
                                                        .filter(Objects::nonNull)
                                                        .collect(Collectors.toList());
                if(!batchData.isEmpty()) {
                    _DataRepository.saveAll(batchData);
                }
            }
        }
        catch(Throwable e) {
            e.printStackTrace();
        }
    }

    @Cacheable(value = "meter_sn",
               key = "#meter",
               unless = "#meter == null || #result == null || #result.isEmpty()")
    public Page<DataEntity> pageQuery(String meter, int limit)
    {

        Optional<MeterEntity> meterOptional = _MeterRepository.findBySn(meter);
        if(meterOptional.isPresent()) {
            MeterEntity meterEntity = meterOptional.get();
            Page<DataEntity> source = _DataRepository.findAll((Specification<DataEntity>) (root, criteriaQuery, criteriaBuilder)->{
                return criteriaQuery.where(criteriaBuilder.equal(root.get("meter"), meterEntity))
                                    .orderBy(criteriaBuilder.desc(root.get("createdAt")))
                                    .getRestriction();
            }, PageRequest.of(0, limit));
            return source;
        }
        return null;
    }
}
