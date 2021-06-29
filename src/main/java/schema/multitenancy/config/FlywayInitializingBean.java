package schema.multitenancy.config;

import schema.multitenancy.repository.TenantRepository;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
@Order(2)
public class FlywayInitializingBean implements InitializingBean {
    private DataSource dataSource;
    private TenantRepository repository;

    public FlywayInitializingBean(DataSource dataSource, TenantRepository repository) {
        this.dataSource = dataSource;
        this.repository = repository;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        repository.findAll().forEach(tenant -> {
            String schema = tenant.getName();
            Flyway flyway = Flyway.configure()
                    .locations("db/migration/tenants")
                    .dataSource(dataSource)
                    .schemas(schema)
                    .load();
            flyway.migrate();
        });
    }
}
