package org.usst.electric.lab.meter.jpa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.isahl.chess.queen.db.inf.IStorage;
import com.isahl.chess.rook.storage.jpa.model.AuditModelManual;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.io.Serial;

import static org.usst.electric.lab.meter.config.MeterConstants.DB_SERIAL_METER_DATA_ENTITY;

@Entity(name = "meter_data")
@Table(schema = "usst")
@TypeDef(name = "jsonb",
         typeClass = JsonBinaryType.class)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DataEntity
        extends AuditModelManual
        implements IStorage
{
    @Serial
    private static final long        serialVersionUID = -549007643252477946L;
    @Id
    private              long        id;
    @Column(name = "reg_addr")
    private              int         regAddr;
    @Column(name = "data")
    @Type(type = "org.hibernate.type.BinaryType")
    private              byte[]      data;
    @ManyToOne
    private              MeterEntity meter;

    @JsonIgnore
    @Transient
    private Operation mOperation = Operation.OP_NULL;

    @Override
    public int serial()
    {
        return DB_SERIAL_METER_DATA_ENTITY;
    }

    @Override
    public long primaryKey()
    {
        return id;
    }

    @Override
    public Operation operation()
    {
        return mOperation;
    }

    @JsonIgnore
    public void setOperation(Operation operation)
    {
        mOperation = operation;
    }

    @Override
    public Strategy strategy()
    {
        return Strategy.RETAIN;
    }

    public MeterEntity getMeter()
    {
        return meter;
    }

    public void setMeter(MeterEntity meter) {this.meter = meter;}

    public int getRegAddr()
    {
        return regAddr;
    }

    public void setRegAddr(int regAddr)
    {
        this.regAddr = regAddr;
    }

    public byte[] getData()
    {
        return data;
    }

    public void setData(byte[] data)
    {
        this.data = data;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }
}
