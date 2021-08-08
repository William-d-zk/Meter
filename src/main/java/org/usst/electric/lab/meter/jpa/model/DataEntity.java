package org.usst.electric.lab.meter.jpa.model;

import java.io.Serial;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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

@Entity(name = "meter_data")
@Table(schema = "usst")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DataEntity
        extends
        AuditModel
        implements
        IStorage
{
    @Serial
    private static final long serialVersionUID   = -549007643252477946L;
    private final static int  DATA_ENTITY_SERIAL = AUDIT_MODEL_SERIAL + 1001;
    @Id
    @GeneratedValue(generator = "ZDeviceGenerator")
    @GenericGenerator(name = "ZDeviceGenerator",
                      strategy = "com.isahl.chess.pawn.endpoint.device.jpa.generator.ZDeviceGenerator")
    private long              id;
    private int               regAddr;
    private float             data;
    @ManyToOne
    private MeterEntity       meter;

    @JsonIgnore
    @Transient
    private Operation mOperation = Operation.OP_NULL;

    @Override
    public int serial()
    {
        return DATA_ENTITY_SERIAL;
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

    public int getRegAddr() {
        return regAddr;
    }

    public void setRegAddr(int regAddr) {
        this.regAddr = regAddr;
    }

    public float getData() {
        return data;
    }

    public void setData(float data) {
        this.data = data;
    }
}
