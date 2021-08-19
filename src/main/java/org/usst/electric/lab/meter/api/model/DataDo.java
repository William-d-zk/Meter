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

    private long   meterId;
    private byte[] content;
    private String type;

    public byte[] getContent()
    {
        return content;
    }

    public void setContent(byte[] content)
    {
        this.content = content;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public long getMeterId()
    {
        return meterId;
    }

    public void setMeterId(long meterId)
    {
        this.meterId = meterId;
    }
}
