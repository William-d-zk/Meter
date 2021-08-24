package org.usst.electric.lab.meter.api;

import com.isahl.chess.bishop.io.modbus.rtu.ModbusRtuProtocol;
import com.isahl.chess.king.base.log.Logger;
import com.isahl.chess.king.base.response.ZResponse;
import com.isahl.chess.king.base.util.CryptoUtil;
import com.isahl.chess.king.base.util.IoUtil;
import com.isahl.chess.king.config.Code;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.usst.electric.lab.meter.api.model.DataDo;
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
        Page<DataEntity> pageData = _Service.pageQuery(meter, limit);
        if(pageData != null && !pageData.isEmpty()) {
            return ZResponse.success(pageData.map(page->{
                                                 byte[] data = page.getData();
                                                 int length = data.length;
                                                 String raw = IoUtil.bin2Hex(data);
                                                 _Logger.info("data: %s", raw);
                                                 _Logger.info("length:%d", data[2] & 0xFF);
                                                 int crc16 = IoUtil.readShort(data, data.length - 2);
                                                 int c = CryptoUtil.crc16_modbus(data, 0, data.length - 2);
                                                 _Logger.info("%d:%d", crc16, c);
                                                 int pos = 3;
                                                 _Logger.info("data %d", data.length);
                                                 while(pos < data.length - 3) {
                                                     int input = IoUtil.readInt(data, pos);
                                                     _Logger.info("%#x, %f", input, Float.intBitsToFloat(input));
                                                     pos += 4;
                                                 }
                                                 DataDo dataDo = new DataDo();
                                                 dataDo.setMeter(meter);
                                                 dataDo.setHex(raw);
                                                 dataDo.setLength(length);
                                                 return dataDo;
                                             })
                                             .stream()
                                             .toList());
        }
        else {
            return ZResponse.error(Code.MISS.getCode(), "not found");
        }
    }
}
