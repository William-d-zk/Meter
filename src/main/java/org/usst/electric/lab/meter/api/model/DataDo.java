package org.usst.electric.lab.meter.api.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author william.d.zk
 */
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DataDo
        implements Serializable
{
    @Serial
    private static final long serialVersionUID = -8539499424596064151L;

    private String meter;
    private int    length;
    private String hex;
    private String type;

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getHex()
    {
        return hex;
    }

    public void setHex(String hex)
    {
        this.hex = hex;
    }

    public String getMeter()
    {
        return meter;
    }

    public void setMeter(String meter)
    {
        this.meter = meter;
    }

    public int getLength()
    {
        return length;
    }

    public void setLength(int length)
    {
        this.length = length;
    }
}
