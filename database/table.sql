CREATE TABLE `province` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `province_id` varchar(32) NOT NULL COMMENT '省份id',
  `province_name` varchar(32) NOT NULL COMMENT '省份',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_province_od` (`province_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='省份信息';

CREATE TABLE `school` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `school_id` varchar(32) NOT NULL COMMENT '大学id',
  `school_name` varchar(32) DEFAULT NULL COMMENT '大学',
  `province_id` varchar(32) DEFAULT NULL COMMENT '省份id',
  `province_name` varchar(32) DEFAULT NULL COMMENT '省份',
  `city_id` varchar(32) DEFAULT NULL COMMENT '城市id',
  `city_name` varchar(32) DEFAULT NULL COMMENT '城市',
  `level` varchar(16) DEFAULT NULL COMMENT '学校等级',
  `type` varchar(16) DEFAULT NULL COMMENT '类别 理工/综合/医学等',
  `nature` varchar(16) DEFAULT NULL COMMENT '办学性质 公办/民办/独立',
  `email` varchar(64) DEFAULT NULL COMMENT '邮箱地址',
  `phone` varchar(128) DEFAULT NULL COMMENT '联系电话',
  `site` varchar(256) DEFAULT NULL COMMENT '网址',
  `address` varchar(128) DEFAULT NULL COMMENT '联系地址',
  `status` tinyint(4) DEFAULT '0' COMMENT '状态 1抓取完成 0未抓取',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_school_id` (`school_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='学校信息';

CREATE TABLE `school_province_score` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `school_id` varchar(32) NOT NULL COMMENT '学校id',
  `student_province_id` varchar(32) NOT NULL COMMENT '考生省份id',
  `student_type` tinyint(4) DEFAULT NULL COMMENT '考生类别 1理工 2文科 3综合',
  `year` char(4) NOT NULL COMMENT '年份',
  `max_score` double DEFAULT NULL COMMENT '最高分',
  `min_score` double DEFAULT NULL COMMENT '最低分',
  `avg_score` double DEFAULT NULL COMMENT '平均分',
  `pro_score` double DEFAULT NULL COMMENT '省控线',
  `min_position` int(11) DEFAULT NULL COMMENT '最低排名',
  `batch_name` varchar(32) DEFAULT NULL COMMENT '录取批次',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_1` (`school_id`,`student_province_id`,`student_type`,`year`,`batch_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='学校的各省分数线';

CREATE TABLE `school_province_score_crawl_task` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `school_id` varchar(32) NOT NULL COMMENT '学校id',
  `student_province_id` varchar(32) NOT NULL COMMENT '考生省份id',
  `student_type` tinyint(4) NOT NULL COMMENT '1理科 2文科 3综合',
  `year` char(4) NOT NULL COMMENT '年份',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '抓取状态 1已抓取 0未抓取',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='分数抓取作业状态';