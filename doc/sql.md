-- 广告信息
create table `ad_info` (
    `ad_id` varchar(32) not null comment '广告id',
    `ad_name` varchar(32) not null comment '广告名称',
    `ad_type` tinyint(3) not null default '0' comment '广告类型 默认图片广告',
    `store_id` varchar(32) not null comment '门店id',
    `create_time` timestamp not null default current_timestamp comment '创建时间',
    `update_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
    primary key (`ad_id`)
);
alter table ad_info add index idx_storeId (store_id);

-- 广告详情
create table `ad_detail` (
    `detail_id` varchar(32) not null comment '广告详情id',
    `ad_id` varchar(32) not null comment '广告id',
    `detail_name` varchar(32)  comment '广告详情名称',
    `detail_url` varchar(5120) not null comment '广告详情url',
    `create_time` timestamp not null default current_timestamp comment '创建时间',
    `update_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
    primary key (`detail_id`)
);
alter table ad_detail add index idx_adId (ad_id);


-- 品牌信息
create table `brand_info` (
    `brand_id` varchar(32) not null comment '品牌id',
    `org_id` varchar(32) not null comment '品牌在权限系统的ID',
    `org_account` varchar(32) not null comment '品牌在权限系统的账号',
    `org_name` varchar(32) not null comment '品牌在权限系统名称',
    `logo_url` varchar(5120)  comment '品牌logo',
    `create_time` timestamp not null default current_timestamp comment '创建时间',
    `update_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
    primary key (`brand_id`)
);
alter table brand_info add unique key(org_id);
alter table brand_info add unique key(org_account);


-- 门店信息
create table `store_info` (
    `store_id` varchar(32) not null comment '门店id',
    `merchant_account` varchar(32) not null comment '门店在权限系统的账号',
    `merchant_id` varchar(32) not null comment '门店在权限系统的ID',
    `store_name` varchar(32) not null comment '门店名称',
    `store_province` varchar(32) not null comment '门店省',
    `store_city` varchar(32) not null comment '门店市',
    `store_district` varchar(32) not null comment '门店区',
    `store_address` varchar(32) not null comment '门店地址',
    `store_location` varchar(32) comment '门店坐标',
    `store_logo` varchar(5120) comment '门店logo',
    `store_description` varchar(128) not null comment '门店介绍',
    `store_phone` varchar(32) not null comment '门店电话',
    `store_officehours` varchar(32) not null comment '门店营业时间',
    `have_parking` tinyint(3) not null default '0' comment '是否有车位，默认无',
    `have_wifi` tinyint(3) not null default '0' comment '是否有wifi，默认无',
    `have_privateroom` tinyint(3) not null default '0' comment '是否有包房，默认无',
    `store_menu` varchar(5120)  comment '门店菜品图片地址',
    `store_photo` varchar(5120)  comment '门店图片地址',
    `have_booking` tinyint(3) not null default '0' comment '是否能预订，默认不能',
    `create_time` timestamp not null default current_timestamp comment '创建时间',
    `update_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
    primary key (`store_id`)
);
alter table store_info add unique key(store_name);
alter table store_info add unique key(merchant_account);
alter table store_info add unique key(merchant_id);
alter table store_info add index idx_merchantAccount (merchant_account);
alter table store_info add index idx_merchantId (merchant_id);
