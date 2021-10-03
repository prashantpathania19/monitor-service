package com.monitor.db;

import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.statement.UseRowMapper;

import java.util.List;
import java.util.Optional;

/**
 * interface contains sqls
 * @author prashant
 */
public interface ServiceSQLs {
    @SqlUpdate("INSERT INTO monitor_service (service_name, service_url, created, updated, status) " +
    "VALUES (:serviceName,:serviceUrl,curtime(),curtime(),:status)")
    @GetGeneratedKeys
    Integer saveNewService(@Bind("serviceName") String serviceName, @Bind("serviceUrl") String serviceUrl, @Bind("status") String status);

    @SqlQuery("SELECT id, service_name, service_url, created, updated, status FROM monitor_service WHERE service_name = :serviceName")
    @UseRowMapper(ServiceBeanMapper.class)
    Optional<ServiceBean> getServiceByName(@Bind("serviceName") String serviceName);

    @SqlQuery("SELECT id, service_name, service_url, created, updated, status FROM monitor_service")
    @UseRowMapper(ServiceBeanMapper.class)
    List<ServiceBean> getServices();

    @SqlUpdate("UPDATE monitor_service SET service_url = :serviceUrl, status = 'PENDING' WHERE service_name = :serviceName")
    Integer updateServiceUrl(@Bind("serviceName") String serviceName, @Bind("serviceUrl") String serviceUrl);

    @SqlUpdate("UPDATE monitor_service SET status = :status, updated = curtime() WHERE service_name = :serviceName")
    Integer updateServiceStatus(@Bind("serviceName") String serviceName, @Bind("status") String status);

    @SqlUpdate("DELETE FROM monitor_service WHERE service_name = :serviceName")
    Integer delete(@Bind("serviceName") String serviceName);
}
