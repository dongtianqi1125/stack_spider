CREATE TABLE `t_article` (
  `id` bigint(64) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `column_id` int(32) DEFAULT NULL COMMENT '栏目id',
  `title` varchar(100) DEFAULT NULL COMMENT '标题',
  `tag` varchar(100) DEFAULT NULL COMMENT '标签',
  `summary` varchar(500) DEFAULT NULL COMMENT '摘要',
  `author` varchar(30) DEFAULT NULL COMMENT '作者',
  `real_source` varchar(50) DEFAULT NULL COMMENT '稿件真实来源',
  `content` text COMMENT '稿件纯文本内容',
  `html_content` text COMMENT '稿件内容',
  `keywords` varchar(100) DEFAULT NULL COMMENT '关键词',
  `publish_time` datetime DEFAULT NULL COMMENT '发布时间',
  `create_time` datetime DEFAULT NULL COMMENT '抓取时间',
  `url` varchar(200) DEFAULT NULL COMMENT '稿件url',
  `status` int(32) DEFAULT NULL COMMENT '-2废弃 -1失败  0 未处理 1正在处理 2 成功',
  `times` int(32) DEFAULT NULL COMMENT '处理次数',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_index_url` (`url`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2272 DEFAULT CHARSET=utf8 COMMENT='稿件表';
CREATE TABLE `t_web_site_column` (
  `id` int(32) NOT NULL AUTO_INCREMENT COMMENT '主键-栏目id',
  `web_site_id` int(32) DEFAULT NULL COMMENT '网站id',
  `column_url` varchar(200) DEFAULT NULL COMMENT '栏目url',
  `column_name` varchar(20) DEFAULT NULL COMMENT '栏目名称',
  `column_alias` varchar(20) DEFAULT NULL COMMENT '栏目英文别名',
  `status` int(32) DEFAULT NULL COMMENT '-2废弃 -1失败  0 未处理 1正在处理 2 成功',
  `domain_url` varchar(50) DEFAULT NULL COMMENT '栏目里面连接的域名',
  `total_pages` int(32) DEFAULT NULL COMMENT '接口访问的页面数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COMMENT='网站栏目表';

CREATE TABLE `t_web_site_main` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `web_name` varchar(30) DEFAULT NULL COMMENT '网站名字',
  `web_char` varchar(10) DEFAULT NULL COMMENT '网站编码',
  `web_alias` varchar(12) DEFAULT NULL COMMENT '别名（缩写名字）',
  `web_main_url` varchar(50) DEFAULT NULL COMMENT '网站主页',
  `class_name` varchar(100) DEFAULT NULL COMMENT '类全路径',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='网站主表';

