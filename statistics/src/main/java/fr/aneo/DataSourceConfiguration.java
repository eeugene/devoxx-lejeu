package fr.aneo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * Created by eeugene on 02/04/2017.
 */
@Configuration
public class DataSourceConfiguration {
    @Bean
    @Primary
    @ConfigurationProperties("hero.datasource")
    public DataSourceProperties heroDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties("hero.datasource")
    public DataSource heroDataSource() {
        return heroDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean
    @ConfigurationProperties("arena.datasource")
    public DataSourceProperties arenaDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("arena.datasource")
    public DataSource arenaDataSource() {
        return arenaDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean(name = "jdbcHero")
    @Autowired
    public JdbcTemplate heroJdbcTemplate() {
        return new JdbcTemplate(heroDataSource());
    }

    @Bean(name = "jdbcArena")
    @Autowired
    public JdbcTemplate arenaJdbcTemplate() {
        return new JdbcTemplate(arenaDataSource());
    }
}
