package com.monitor.db;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * class to represent
 * player service mapper
 * @author prashant
 */
public class ServiceBeanMapper implements RowMapper<ServiceBean> {
    private static final String DATE_FORMAT_PATTERN = "dd/MM/yyyy HH:mm:ss";
    @Override
    public ServiceBean map(ResultSet rs, StatementContext ctx) throws SQLException {
        ServiceBean.Builder builder = new ServiceBean.Builder();
        builder.id(rs.getLong("id"));
        builder.serviceName(rs.getString("service_name"));
        builder.serviceUrl(rs.getString("service_url"));
        builder.created(getDate(rs.getTimestamp("created")));
        builder.updated(getDate(rs.getTimestamp("updated")));
        builder.status(rs.getString("status"));
        return builder.build();
    }

    private String getDate(Timestamp timestamp){
        Date date = new Date(timestamp.getTime());
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_PATTERN);
        return dateFormat.format(date);
    }
}
