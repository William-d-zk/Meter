package org.usst.electric.lab.meter.api;

import com.isahl.chess.king.base.log.Logger;
import com.isahl.chess.king.base.response.ZResponse;
import com.isahl.chess.king.config.Code;
import com.isahl.chess.pawn.endpoint.device.jpa.remote.postgres.model.DeviceEntity;
import com.isahl.chess.player.api.service.MixOpenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.usst.electric.lab.meter.api.model.MeterDo;
import org.usst.electric.lab.meter.jpa.model.MeterEntity;
import org.usst.electric.lab.meter.service.MeterService;

@RestController
@RequestMapping("api/meter")
public class MeterController
{
    private final Logger         _Logger = Logger.getLogger(getClass().getSimpleName());
    private final MeterService   _Service;
    private final MixOpenService _OpenService;

    @Autowired
    public MeterController(MeterService service, MixOpenService openService)
    {
        _Service = service;
        _OpenService = openService;
    }

    @PostMapping("setup")
    public ZResponse<?> setup(
            @RequestBody
                    MeterDo meter)
    {
        MeterEntity entity = meter.toEntity();
        String client = meter.getClient();
        DeviceEntity device = _OpenService.findByToken(client);
        if(device == null) {return ZResponse.error(Code.MISS.getCode(), Code.MISS.format(client));}
        entity.setDeviceId(device.getId());
        entity = _Service.addMeter(entity);
        return ZResponse.success(entity);
    }

    @GetMapping("query")
    public ZResponse<?> query(
            @RequestParam("device")
                    long deviceId)
    {
        MeterEntity meter = _Service.findMeterByDeviceId(deviceId);
        return meter != null ? ZResponse.success(meter) : ZResponse.error(Code.MISS.getCode(), "not found meter");
    }

}
