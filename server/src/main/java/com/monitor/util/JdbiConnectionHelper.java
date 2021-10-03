package com.monitor.util;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class JdbiConnectionHelper {
    @Qualifier("dataSource")
    @Autowired
    private DataSource dataSource;

    public <T> T attach(Class<T> tClass) {
        Jdbi jdbi = Jdbi.create(DataSourceUtils.getConnection(dataSource));
        jdbi.installPlugin(new SqlObjectPlugin());
        Handle handle = jdbi.open();
        return handle.attach(tClass);
    }
}

