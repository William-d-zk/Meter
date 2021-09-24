package org.usst.electric.lab.meter.service;

import com.isahl.chess.bishop.io.mqtt.command.X113_QttPublish;
import com.isahl.chess.king.base.log.Logger;
import com.isahl.chess.pawn.endpoint.device.api.db.model.MessageEntity;
import com.isahl.chess.pawn.endpoint.device.api.db.repository.IMessageJpaRepository;
import com.isahl.chess.pawn.endpoint.device.spi.IHandleHook;
import com.isahl.chess.queen.io.core.features.model.content.IControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MeterPlugin
        implements IHandleHook
{

    private final Logger _Logger = Logger.getLogger("usst.meter." + getClass().getSimpleName());

    private final IMessageJpaRepository _MessageJpaRepository;

    @Autowired
    public MeterPlugin(IMessageJpaRepository messageJpaRepository)
    {
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
                    _Logger.warning("Error saving message", e);
                }
            }
        }
    }
}
