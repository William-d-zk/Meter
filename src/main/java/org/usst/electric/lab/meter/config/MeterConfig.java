package org.usst.electric.lab.meter.config;

import com.isahl.chess.rook.storage.jpa.config.BaseJpaConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration("meter_config")
@ConfigurationProperties(prefix = "usst.lab.meter")
@PropertySource("classpath:meter.properties")
@EnableJpaRepositories(basePackages = { "org.usst.electric.lab.meter.jpa.repository",
                                        "com.isahl.chess.pawn.endpoint.device.api.jpa.repository"
},
                       entityManagerFactoryRef = "meter-entity-manager",
                       transactionManagerRef = "meter-transaction-manager")
public class MeterConfig
        extends BaseJpaConfig
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

    @Bean("meter-entity-manager")
    public LocalContainerEntityManagerFactoryBean createSecurityEntityManager(
            @Qualifier("primary-data-source")
                    DataSource dataSource,
            @Qualifier("primary-jpa-properties")
                    JpaProperties jpaProperties,
            @Qualifier("primary-jpa-hibernate-properties")
                    HibernateProperties hibernateProperties)
    {
        return getEntityManager(dataSource,
                                jpaProperties,
                                hibernateProperties,
                                "com.isahl.chess.pawn.endpoint.device.api.jpa.model",
                                "org.usst.electric.lab.meter.jpa.model");
    }

    @Bean("meter-transaction-manager")
    public PlatformTransactionManager createSecurityTransactionManager(
            @Qualifier("meter-entity-manager")
                    LocalContainerEntityManagerFactoryBean factory)
    {
        JpaTransactionManager tm = new JpaTransactionManager();
        tm.setEntityManagerFactory(factory.getObject());
        return tm;
    }
}
