package org.usst.electric.lab.meter.service;

import com.isahl.chess.bishop.io.mqtt.command.X113_QttPublish;
import com.isahl.chess.pawn.endpoint.device.api.jpa.model.MessageEntity;
import com.isahl.chess.pawn.endpoint.device.api.jpa.repository.IMessageJpaRepository;
import com.isahl.chess.pawn.endpoint.device.spi.IHandleHook;
import com.isahl.chess.queen.io.core.inf.IControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.usst.electric.lab.meter.jpa.repository.IMeterDataRepository;

import java.util.List;

@Component
public class MeterPlugin
        implements IHandleHook
{
    private final IMeterDataRepository  _DataRepository;
    private final IMessageJpaRepository _MessageJpaRepository;

    @Autowired
    public MeterPlugin(IMeterDataRepository dataRepository, IMessageJpaRepository messageJpaRepository)
    {
        _DataRepository = dataRepository;
        _MessageJpaRepository = messageJpaRepository;
    }

    @Override
    public void handle(IControl content, List<? extends IControl> pushList)
    {
        if(content.serial() == X113_QttPublish.COMMAND) {
            X113_QttPublish x113 = (X113_QttPublish) content;
            if(!x113.isDuplicate() && x113.session() != null) {
                MessageEntity messageEntity = new MessageEntity();
                messageEntity.setOrigin(x113.session()
                                            .getIndex());
                messageEntity.setTopic(x113.getTopic());
                messageEntity.setContent(x113.payload());
                try {
                    _MessageJpaRepository.save(messageEntity);
                }
                catch(Throwable e) {

                }
            }
        }
    }
}
