package org.usst.electric.lab.meter.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration("meter_config")
@ConfigurationProperties(prefix = "usst.lab.meter")
@PropertySource("classpath:meter.properties")

public class MeterConfig
{
    private String meterType;

    public String getMeterType()
    {
        return meterType;
    }

    public void setMeterType(String meterType)
    {
        this.meterType = meterType;
    }

}
