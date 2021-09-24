package org.usst.electric.lab.meter.api;

import com.isahl.chess.bishop.io.modbus.rtu.ModbusRtuProtocol;
import org.usst.electric.lab.meter.api.model.db194e.*;
import org.usst.electric.lab.meter.jpa.model.db194e.RtuCmdSchema;

public class RtuFactory
{
    public static ModbusRtuProtocol create(String type, byte addr, byte cmd, byte[] payload)
    {
        return switch (type)
        {
            case "db194e" -> switch (RtuCmdSchema.select(cmd))
                {
                    case QUERY_SWITCH_STATUS -> new QuerySwitchRtu(addr, cmd, payload);
                    case QUERY_RELAY_STATUS -> new QueryRelayRtu(addr, cmd, payload);
                    case QUERY_REG_0 -> new QueryReg0Rtu(addr, cmd, payload);
                    case QUERY_REG_1 -> new QueryReg1Rtu(addr, cmd, payload);
                    case SET_SINGLE_RELAY, SET_MULTI_RELAY -> new SetRelayRtu(addr, cmd, payload);
                    case SET_REG -> new SetMeterAddrRtu(addr, cmd, payload);
                };
            case "slt" -> null;
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };
    }

}
