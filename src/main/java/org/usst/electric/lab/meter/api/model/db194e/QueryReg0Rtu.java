package org.usst.electric.lab.meter.api.model.db194e;

import com.isahl.chess.bishop.io.modbus.rtu.ModbusRtuProtocol;
import com.isahl.chess.queen.io.core.inf.ICommand;
import com.isahl.chess.queen.io.core.inf.IContext;

public class QueryReg0Rtu
        extends ModbusRtuProtocol
        implements ICommand
{
    final static int SERIAL = COMMAND_SERIAL + 0x1000;

    private long mMsgId;

    public QueryReg0Rtu(byte addr, byte cmd, byte[] payload)
    {
        super(addr, cmd, payload);
    }

    public QueryReg0Rtu()
    {
    }

    @Override
    public long getMsgId()
    {
        return mMsgId;
    }

    @Override
    public void setMsgId(long msgId)
    {
        mMsgId = msgId;
    }

    @Override
    public <C extends IContext> C context()
    {
        return null;
    }

    @Override
    public void putCtrl(byte ctrl)
    {
        mCtrl = ctrl;
    }

    @Override
    public void putPayload(byte[] payload)
    {
        mPayload = payload;
    }

    @Override
    public byte[] payload()
    {
        return mPayload;
    }

    @Override
    public byte ctrl()
    {
        return mCtrl;
    }

    @Override
    public boolean isCtrl()
    {
        return true;
    }

    @Override
    public void reset()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public int serial()
    {
        return SERIAL;
    }

    @Override
    public Level getLevel()
    {
        return Level.ALMOST_ONCE;
    }
}
