package org.usst.electric.lab.meter.api;

import com.isahl.chess.bishop.io.modbus.rtu.ModbusRtuProtocol;
import com.isahl.chess.king.base.log.Logger;
import com.isahl.chess.king.base.response.ZResponse;
import com.isahl.chess.king.base.util.IoUtil;
import com.isahl.chess.king.config.Code;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.usst.electric.lab.meter.jpa.model.DataEntity;
import org.usst.electric.lab.meter.service.MeterService;

@RestController
@RequestMapping("api/rtu")
public class ModbusRtuController
{
    private final Logger       _Logger = Logger.getLogger(getClass().getSimpleName());
    private final MeterService _Service;

    public ModbusRtuController(MeterService service)
    {
        _Service = service;
    }

    @GetMapping("in")
    public ZResponse<?> rtuIn(
            @RequestParam("addr")
                    String addrHex,
            @RequestParam("cmd")
                    String cmdHex,
            @RequestParam("payload")
                    String payloadHex)
    {
        byte[] bCmd = IoUtil.hex2bin(cmdHex);
        byte[] bAddr = IoUtil.hex2bin(addrHex);
        byte[] bPayload = IoUtil.hex2bin(payloadHex);
        ModbusRtuProtocol modbus = RtuFactory.create("db194e", bAddr[0], bCmd[0], bPayload);
        byte[] rtu = modbus.encode();
        _Logger.info("RTU:[%s]", IoUtil.bin2Hex(rtu));
        return ZResponse.success(modbus);
    }

    @GetMapping("list")
    public ZResponse<?> listData(
            @RequestParam("meter")
                    String meter,
            @RequestParam("limit")
                    int limit)
    {
        Page<DataEntity> pages = _Service.pageQuery(meter, limit);
        if(pages != null && !pages.isEmpty()) {
            return ZResponse.success(pages);
        }
        else {
            return ZResponse.error(Code.MISS.getCode(), "not found");
        }
    }
}
