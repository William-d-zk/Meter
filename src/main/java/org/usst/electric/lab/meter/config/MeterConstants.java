package org.usst.electric.lab.meter.config;

import static com.isahl.chess.pawn.endpoint.device.db.PawnConstants.PAWN_END_SERIAL;

/**
 * @author william.d.zk
 */
public interface MeterConstants
{
    int DB_SERIAL_METER_ENTITY      = PAWN_END_SERIAL + 1000;
    int DB_SERIAL_METER_DATA_ENTITY = DB_SERIAL_METER_ENTITY + 1;


}
