create table Cases
(
    id         int auto_increment
        primary key,
    userId     int          null,
    doctorId   int          null,
    status     varchar(255) null,
    severity   varchar(255) null,
    updateTime varchar(255) null,
    constraint Cases_userId_uindex
        unique (userId)
);

create table Doctor
(
    id         int auto_increment
        primary key,
    userId     int          not null,
    name       varchar(255) null,
    department varchar(255) null,
    occupation varchar(255) null,
    phone      varchar(13)  null,
    constraint Doctor_userId_uindex
        unique (userId)
);

create table HealthChart
(
    id                 int auto_increment
        primary key,
    userId             int          null,
    selfStatus         varchar(255) null,
    familyStatus       varchar(255) null,
    location           varchar(255) null,
    isConCaseIn14      varchar(10)  null,
    isPassRiskAreaIn14 varchar(10)  null,
    isAbroadIn14       varchar(10)  null,
    isConRiskAbrIn14   varchar(10)  null,
    details            text         null,
    reportDate         varchar(255) null,
    constraint HealthChart_userId_uindex
        unique (userId)
);

create table Statistic
(
    id            int auto_increment
        primary key,
    totalDiagnose int          null comment '累计确诊',
    totalDeath    int          null comment '累计死亡',
    totalCure     int          null comment '累计治愈',
    currDiagnose  int          null comment '现存确诊',
    currSuspect   int          null comment '现存疑似',
    currAsy       int          null comment '现存无症状',
    currSevere    int          null comment '现存重症',
    newSuspect    int          null comment '新增疑似',
    newDiagnose   int          null comment '新增确诊',
    newDeath      int          null comment '新增死亡',
    newCure       int          null comment '新增治愈',
    updateDate    varchar(255) null,
    constraint Statistic_updateDate_uindex
        unique (updateDate)
);

create table User
(
    id       int auto_increment
        primary key,
    email    varchar(255) null,
    password varchar(255) null,
    role     varchar(255) null,
    constraint User_email_uindex
        unique (email)
);

create table UserInfo
(
    id              int auto_increment
        primary key,
    userId          int          null,
    schoolId        varchar(255) null,
    name            varchar(255) null,
    gender          varchar(10)  null,
    age             int          null,
    department      varchar(255) null,
    phone           varchar(13)  null,
    urgentPhone     varchar(13)  null,
    isHKMT          varchar(10)  null,
    isInternational varchar(10)  null,
    constraint UserInfo_schoolId_uindex
        unique (schoolId),
    constraint UserInfo_userId_uindex
        unique (userId)
);


