package org.usst.electric.lab.meter.jpa.config;

import com.isahl.chess.king.base.log.Logger;
import com.isahl.chess.rook.storage.jpa.config.BaseJpaConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.sql.init.DatabaseInitializationSettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * @author william.d.zk
 */
@Configuration("meter-jpa-config")
@EnableJpaRepositories(basePackages = { "org.usst.electric.lab.meter.jpa.repository",
                                        "com.isahl.chess.pawn.endpoint.device.api.jpa.repository"
},
                       entityManagerFactoryRef = "meter-entity-manager",
                       transactionManagerRef = "meter-transaction-manager")
public class MeterJpaConfig
        extends BaseJpaConfig
{

    private final Logger _Logger = Logger.getLogger("app.meter." + getClass().getSimpleName());

    @Bean("meter-entity-manager")
    public LocalContainerEntityManagerFactoryBean createSecurityEntityManager(
            @Qualifier("primary-data-source")
                    DataSource dataSource,
            @Qualifier("primary-jpa-properties")
                    JpaProperties jpaProperties,
            @Qualifier("primary-jpa-hibernate-properties")
                    HibernateProperties hibernateProperties,
            @Qualifier("primary-sql-init-settings")
                    DatabaseInitializationSettings initializationSettings)
    {
        _Logger.info(initializationSettings);
        return getEntityManager(dataSource,
                                jpaProperties,
                                hibernateProperties,
                                initializationSettings,
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
