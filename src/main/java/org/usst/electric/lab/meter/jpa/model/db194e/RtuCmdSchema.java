package org.usst.electric.lab.meter.jpa.model.db194e;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public enum RtuCmdSchema
{
    QUERY_RELAY_STATUS((byte) 0x01),
    QUERY_SWITCH_STATUS((byte) 0x02),
    QUERY_REG_0((byte) 0x03),
    QUERY_REG_1((byte) 0x04),
    SET_SINGLE_RELAY((byte) 0x05),
    SET_MULTI_RELAY((byte) 0x0F),
    SET_REG((byte) 0x10);

    final byte _Cmd;

    RtuCmdSchema(byte cmd)
    {
        _Cmd = cmd;
    }

    public byte getCmd()
    {
        return _Cmd;
    }

    public static RtuCmdSchema select(byte cmd)
    {
        return switch (cmd)
        {
            case 0x01 -> QUERY_RELAY_STATUS;
            case 0x02 -> QUERY_SWITCH_STATUS;
            case 0x03 -> QUERY_REG_0;
            case 0x04 -> QUERY_REG_1;
            case 0x05 -> SET_SINGLE_RELAY;
            case 0x0F -> SET_MULTI_RELAY;
            case 0x10 -> SET_REG;
            default -> throw new IllegalStateException("Unexpected value: " + cmd);
        };
    }
}
