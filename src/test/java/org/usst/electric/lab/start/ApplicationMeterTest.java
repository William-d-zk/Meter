package org.usst.electric.lab.start;

import com.isahl.chess.king.base.log.Logger;
import com.isahl.chess.player.api.controller.DeviceController;
import com.isahl.chess.player.api.service.MixOpenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class ApplicationMeterTest
{
    private final Logger           _Logger = Logger.getLogger("electric-dash.test." + getClass().getSimpleName());
    private       MockMvc          mvc;
    @Mock
    private       MixOpenService   service;
    @InjectMocks
    private       DeviceController deviceController;

    @BeforeEach
    public void setUp()
    {
        MockitoAnnotations.openMocks(this);
        // (1)构建mvc环境
        mvc = MockMvcBuilders.standaloneSetup(deviceController)
                             .build();
    }

    @Test
    void testRegister()
    {

    }
}