package org.usst.electric.lab.meter.api.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.usst.electric.lab.meter.jpa.model.MeterEntity;

import java.io.Serial;
import java.io.Serializable;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MeterDo
        implements
        Serializable
{

    @Serial
    private static final long serialVersionUID = -7016552673190995325L;

    private String sn;
    private String client;
    private byte   address;
    private String type;

    public String getSn()
    {
        return sn;
    }

    public void setSn(String sn)
    {
        this.sn = sn;
    }

    public String getClient()
    {
        return client;
    }

    public void setClient(String client)
    {
        this.client = client;
    }

    public byte getAddress()
    {
        return address;
    }

    public void setAddress(byte address)
    {
        this.address = address;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public MeterEntity toEntity()
    {
        MeterEntity entity = new MeterEntity();
        entity.setSn(sn);
        entity.setType(type);
        entity.setAddr(address);
        return entity;
    }
}
