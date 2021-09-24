package org.usst.electric.lab.meter.jpa.repository;

import com.isahl.chess.rook.storage.db.repository.BaseLongRepository;
import org.springframework.stereotype.Repository;
import org.usst.electric.lab.meter.jpa.model.MeterEntity;

import java.util.Optional;

@Repository
public interface IMeterRepository
        extends BaseLongRepository<MeterEntity>
{
    Optional<MeterEntity> findBySn(String sn);

    MeterEntity findByDeviceId(long deviceId);
}
