/*
 * Copyright (c) 2021. ComeOn! All rights reserved. Company Confidential.
 */
package com.monitor.test;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class InMemoryDBTestUtil {

    public static Handle createDBandReturnHandle() throws IOException, ClassNotFoundException {
        Class.forName("org.h2.Driver");
        Jdbi jdbi = Jdbi.create("jdbc:h2:mem:test");
        Handle handle = jdbi.open();
        handle.execute("set mode MySQL;");

        List<Path> list =  Files.list(Paths.get("./db-scripts"))
                .filter(path -> path.toString().contains(".sql"))
                .sorted((f1, f2) -> {
                    String name1 = f1.getName(f1.getNameCount()-1).toString().replace(".sql", "");
                    String name2 = f2.getName(f2.getNameCount()-1).toString().replace(".sql", "");
                    return new Integer(name1).compareTo(new Integer(name2));
                }).collect(Collectors.toList());

        for(Path path : list){
            String sql = new String(Files.readAllBytes(path));
            //h2 v.1.4.196 does not support the following statement, so we remove it
            sql = sql.replaceAll("ON UPDATE CURRENT_TIMESTAMP", " ");
            handle.createScript(sql).executeAsSeparateStatements();

        }
        return handle;
    }

    public static Jdbi getDBI(){
        Jdbi jdbi = Jdbi.create("jdbc:h2:mem:test");
        jdbi.installPlugin(new SqlObjectPlugin());
        return jdbi;
    }
}
