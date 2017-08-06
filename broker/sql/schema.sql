DROP TABLE IF EXISTS `messages`;
CREATE TABLE `messages` (
  `_mid` bigint(20) NOT NULL,
  `_from` varchar(64) NOT NULL,
  `_to` varchar(64) NOT NULL,
  `_type` tinyint NOT NULL,
  `_data` BLOB NOT NULL,
  `_searchable_key` TEXT DEFAULT NULL,
  `_dt` bigint(20) NOT NULL,
  PRIMARY KEY (`_mid`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `user_messages`;
CREATE TABLE `user_messages` (
  `_mid` bigint(20) NOT NULL,
  `_uid` varchar(64) NOT NULL,
  KEY (`_mid`),
  KEY (`_uid`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `group`;
CREATE TABLE `group` (
  `_gid` varchar(64) NOT NULL,
  `_name` varchar(64) DEFAULT '',
  `_portrait` varchar(1024) DEFAULT '',
  `_owner` varchar(64) DEFAULT '',
  `_type` tinyint NOT NULL,
  `_extra` TEXT DEFAULT NULL,
  `_dt` bigint(20) NOT NULL,
  PRIMARY KEY (`_gid`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `group_member`;
CREATE TABLE `group_member` (
  `_gid` varchar(64) NOT NULL,
  `_mid` varchar(64) DEFAULT '',
  `_dt` bigint(20) NOT NULL,
  KEY (`_gid`),
  KEY (`_mid`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
