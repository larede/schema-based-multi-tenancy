based on https://github.com/anarsultanov/examples/tree/master/schema-based-multi-tenancy

$ docker-compose up -d


# Create tenants

$ curl -i -X POST -H "Content-Type: application/json" -d "{\"name\":\"tenant-1\"}" http://localhost:8080/api/multitenancy/tenants

$ curl -i -X POST -H "Content-Type: application/json" -d "{\"name\":\"tenant-2\"}" http://localhost:8080/api/multitenancy/tenants


# Test with notes API

$ curl -i -X POST -H "Content-Type: application/json" -H "X-TenantID: tenant-1" -d "{\"text\":\"My first note on tenant-1\"}" http://localhost:8080/api/multitenancy/notes

$ curl -i -X POST -H "Content-Type: application/json" -H "X-TenantID: tenant-2" -d "{\"text\":\"My first note on tenant-2\"}" http://localhost:8080/api/multitenancy/notes

$ curl -i -X POST -H "Content-Type: application/json" -H "X-TenantID: tenant-1" -d "{\"text\":\"My second note on tenant-1\"}" http://localhost:8080/api/multitenancy/notes

$ curl -i -X POST -H "Content-Type: application/json" -H "X-TenantID: tenant-2" -d "{\"text\":\"My second note on tenant-2\"}" http://localhost:8080/api/multitenancy/notes

$ curl -i -X POST -H "Content-Type: application/json" -H "X-TenantID: tenant-1" -X GET http://localhost:8080/api/multitenancy/notes

$ curl -i -X POST -H "Content-Type: application/json" -H "X-TenantID: tenant-2" -X GET http://localhost:8080/api/multitenancy/notes

# Jmeter project

$ wget https://downloads.apache.org//jmeter/binaries/apache-jmeter-5.4.1.zip

$ unzip apache-jmeter-5.4.1.zip

create 9 tenants: 
- tenant-1, tenant-2, tenant-3, tenant-4, tenant-5, tenant-6, tenant-7, tenant-8, tenant-9

$ ./apache-jmeter-5.4.1/bin/jmeter

(load 'Jmeter Thread Group.jmx' project)
