

-- 用户注册的基本信息 
create table if not exists `user` ( 
    id bigint(20) not null auto_increment comment 'user identified number', 
    name char(20) not null default 'NULL', 
    `password` char(20) not null, 
    regist_email char(50) not null comment 'user regist email address, need unique', 
    create_time timestamp not null default current_timestamp comment 'user acturally create timestamp', 
    update_time timestamp not null default current_timestamp on update current_timestamp comment 'user update timestamp', 
    primary key (id) 
) ENGINE=InnoDB AUTO_INCREMENT = 16 DEFAULT CHARSET = utf8mb4 COMMENT = 'user basic infomation';

create index idx_user on user (id); 
create unique index idx_regist_email on user (regist_email); 
-- 修改用户密码长度, md5 32
alter table user modify column password char(50);

-- 船舶静态信息, 需要与船舶的动态数据分开, 这样数据分割可以加速数据存储, 防止后期出现数据瓶颈 
create table if not exists `ship` ( 
    id bigint(20) not null auto_increment comment 'ship identified number', 
    user_id bigint(20) not null comment 'user table ,user id reference', 
    name char(20) not null comment 'ship name', 
    mmsi char(10) comment 'mmsi', 
    imo_number char(10) default 0 comment 'IMO Number', 
    call_number char(10) comment 'call number', 
    type int(20) default 0 comment 'ship type, such as pilot, fishing, supply and so on', 
    electronic_type int(20) default '0' comment 'ship electronic type/equipment', 
    draft float(12) default '10' comment 'ship draft m/kt', 
    create_time timestamp not null default current_timestamp comment 'ship create timestamp', 
    update_time timestamp not null default current_timestamp on update current_timestamp comment 'ship update timestamp', 
    primary key (id) 
) ENGINE=InnoDB AUTO_INCREMENT = 16 DEFAULT CHARSET = utf8mb4 COMMENT = 'ship basic information'; 

create index idx_ship on ship (id); 
create index idx_user_id on ship (user_id); 


-- voyage data recorder 船舶数据记录仪(VDR) 
-- 路径数据是时序递增数据, 更适合使用时序数据库 
create table if not exists `ship_track_point` ( 
    id bigint(50) not null auto_increment comment 'ship vdr record identified number', 
    ship_id bigint(20) not null comment 'ship identified number', 
    rotation_acceleration float(20) default '0' comment 'ship rotate speed', 
    sog_speed float(20) default '0' comment 'speed of groud', 
    cog_cource float(20) default '0' comment 'cource of groud', 
    speed float(20) default '0' comment 'speed of ship', 
    cource float(20) default '0' comment 'cource of ship', 
    lng float(20) default '0' comment 'logitude of position', 
    lat float(20) default '0' comment 'latitude of position', 
    create_time timestamp not null default current_timestamp comment 'ship track point create time', 
    primary key (id) 
) ENGINE=InnoDB AUTO_INCREMENT = 16 DEFAULT CHARSET = utf8mb4 COMMENT = 'ship track point VDR'; 

create index idx_track on ship_track_point (id); 
create index idx_ship_id on ship_track_point (ship_id); 

alter table ship_track_point add column rudder float(10) default '0' comment 'ship rudder angle status' after cource ;

















