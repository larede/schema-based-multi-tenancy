package schema.multitenancy.service;

import schema.multitenancy.entity.Tenant;
import schema.multitenancy.repository.TenantRepository;
import org.flywaydb.core.Flyway;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
public class TenantService {
    private DataSource dataSource;
    private TenantRepository repository;

    public TenantService(DataSource dataSource, TenantRepository repository) {
        this.dataSource = dataSource;
        this.repository = repository;
    }

    public Tenant createTenant(Tenant tenant) {
        Tenant saved = repository.save(tenant);
        Flyway flyway = Flyway.configure()
                .locations("db/migration/tenants")
                .dataSource(dataSource)
                .schemas(tenant.getName())
                .load();
        flyway.migrate();

        return saved;
    }
}
