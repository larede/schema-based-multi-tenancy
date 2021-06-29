package schema.multitenancy.repository;

import schema.multitenancy.entity.Tenant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TenantRepository extends CrudRepository<Tenant, Long> {
    Optional<Tenant> findByName(String name);
}
