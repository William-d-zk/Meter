package org.usst.electric.lab.meter.jpa.repository;

import com.isahl.chess.rook.storage.jpa.repository.BaseLongRepository;
import org.springframework.stereotype.Repository;
import org.usst.electric.lab.meter.jpa.model.DataEntity;

@Repository
public interface IMeterDataRepository
        extends BaseLongRepository<DataEntity>
{

}
