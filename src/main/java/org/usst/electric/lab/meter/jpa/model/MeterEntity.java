package org.usst.electric.lab.meter.jpa.model;

import java.io.Serial;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.TypeDef;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.isahl.chess.queen.db.inf.IStorage;
import com.isahl.chess.rook.storage.jpa.model.AuditModel;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;

@Entity(name = "meter_device")
@Table(schema = "usst", indexes = {@Index(name = "meter_device_idx_sn", columnList = "sn")})
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MeterEntity
        extends
        AuditModel
        implements
        IStorage
{
    @Serial
    private static final long serialVersionUID    = -549007643252477946L;
    private final static int  METER_ENTITY_SERIAL = AUDIT_MODEL_SERIAL + 1000;
    @Id
    @GeneratedValue(generator = "ZDeviceGenerator")
    @GenericGenerator(name = "ZDeviceGenerator",
                      strategy = "com.isahl.chess.pawn.endpoint.device.jpa.generator.ZDeviceGenerator")
    private long              id;
    @Column(updatable = false, nullable = false)
    private long              r485Id;
    private byte              addr;
    private String            type;
    private String            note;
    @Column(updatable = false, nullable = false, unique = true)
    private String            sn;

    @JsonIgnore
    @Transient
    private IStorage.Operation mOperation = IStorage.Operation.OP_NULL;

    @Override
    public int serial()
    {
        return METER_ENTITY_SERIAL;
    }

    @Override
    public long primaryKey()
    {
        return id;
    }

    @Override
    public IStorage.Operation operation()
    {
        return mOperation;
    }

    @JsonIgnore
    public void setOperation(IStorage.Operation operation)
    {
        mOperation = operation;
    }

    @Override
    public IStorage.Strategy strategy()
    {
        return IStorage.Strategy.RETAIN;
    }

    public long getR485Id()
    {
        return r485Id;
    }

    public void setR485Id(long r485Id)
    {
        this.r485Id = r485Id;
    }

    public byte getAddr()
    {
        return addr;
    }

    public void setAddr(byte addr)
    {
        this.addr = addr;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getNote()
    {
        return note;
    }

    public void setNote(String note)
    {
        this.note = note;
    }

    public String getSn()
    {
        return sn;
    }

    public void setSn(String sn)
    {
        this.sn = sn;
    }
}
