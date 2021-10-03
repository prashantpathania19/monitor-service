drop table if exists monitor_service;

create table monitor_service (
    id bigint(20) unsigned auto_increment primary key,
    service_name varchar(60) NOT NULL,
    service_url varchar(60) NOT NULL,
    created datetime NOT NULL,
    updated datetime NOT NULL,
    status varchar(15) NOT NULL
);

