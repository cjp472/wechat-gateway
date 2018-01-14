-- 门店信息
create table `store_info` (
    `store_id` varchar(32) not null comment '门店id',
    `merchant_account` varchar(32) not null comment '门店在权限系统的账号',
    `store_name` varchar(32) not null comment '门店名称',
    `store_province` varchar(32) not null comment '门店省',
    `store_city` varchar(32) not null comment '门店市',
    `store_district` varchar(32) not null comment '门店区',
    `store_address` varchar(32) not null comment '门店地址',
    `store_description` varchar(128) not null comment '门店介绍',
    `store_phone` varchar(32) not null comment '门店电话',
    `store_officehours` varchar(32) not null comment '门店营业时间',
    `have_parking` tinyint(3) not null default '0' comment '是否有车位，默认无',
    `have_wifi` tinyint(3) not null default '0' comment '是否有wifi，默认无',
    `have_privateroom` tinyint(3) not null default '0' comment '是否有包房，默认无',
    `have_booking` tinyint(3) not null default '0' comment '是否能预订，默认不能',
    `create_time` timestamp not null default current_timestamp comment '创建时间',
    `update_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
    primary key (`store_id`)
);
alter table store_info add unique key(store_name);
alter table store_info add unique key(merchant_account);

-- 菜品图片
create table `store_menu` (
    `menu_id`  varchar(32) not null comment '菜品id',
    `menu_name`  varchar(32) not null comment '菜品名称',
    `menu_url`  varchar(32) not null comment '菜品链接',
    `store_id` varchar(32) not null,
    `create_time` timestamp not null default current_timestamp comment '创建时间',
    `update_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
    primary key (`menu_id`)
);

-- 门店图片
create table `store_photo` (
    `photo_id`  varchar(32) not null comment '菜品id',
    `photo_name`  varchar(32)  comment '菜品名称',
    `photo_url`  varchar(32) not null comment '菜品链接',
    `store_id` varchar(32) not null,
    `create_time` timestamp not null default current_timestamp comment '创建时间',
    `update_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
    primary key (`photo_id`)
);
