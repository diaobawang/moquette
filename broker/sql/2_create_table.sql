DROP TABLE IF EXISTS `t_messages`;
CREATE TABLE `t_messages` (
  `_mid` bigint(20) NOT NULL,
  `_from` varchar(64) NOT NULL,
  `_type` tinyint NOT NULL DEFAULT 0,
  `_target` varchar(64) NOT NULL,
  `_line` tinyint NOT NULL DEFAULT 0,
  `_data` BLOB NOT NULL,
  `_searchable_key` TEXT DEFAULT NULL,
  `_dt` bigint(20) NOT NULL,
  PRIMARY KEY (`_mid`),
  INDEX `message_from_index` (`_from` ASC),
  INDEX `message_target_index` (`_target` ASC),
  INDEX `message_type_index` (`_type` ASC),
  INDEX `message_line_index` (`_line` ASC)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `t_user_messages`;
CREATE TABLE `t_user_messages` (
  `_mid` bigint(20) NOT NULL,
  `_target` varchar(64) NOT NULL,
  `_type` tinyint NOT NULL DEFAULT 0,
  `_line` tinyint NOT NULL DEFAULT 0,
  `_uid` varchar(64) NOT NULL,
  INDEX `message_user_target_index` (`_target` ASC),
  INDEX `message_user_type_index` (`_type` ASC),
  INDEX `message_user_line_index` (`_line` ASC),
  UNIQUE INDEX `message_mid_uid_index` (`_uid` ASC, `_mid` ASC)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


DROP TABLE IF EXISTS `t_group`;
CREATE TABLE `t_group` (
  `_gid` varchar(64) NOT NULL,
  `_name` varchar(64) DEFAULT '',
  `_portrait` varchar(1024) DEFAULT '',
  `_owner` varchar(64) DEFAULT '',
  `_type` tinyint NOT NULL DEFAULT 0,
  `_extra` TEXT DEFAULT NULL,
  `_dt` bigint(20) NOT NULL,
  PRIMARY KEY (`_gid`),
  INDEX `group_name_index` (`_name` ASC)
)

ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `t_group_member`;
CREATE TABLE `t_group_member` (
  `_gid` varchar(64) NOT NULL,
  `_mid` varchar(64) DEFAULT '',
  `_alias` varchar(64) DEFAULT '',
  `_type` tinyint DEFAULT 0 COMMENT "0普通成员；1，管理员；2，群主，与Owner相同",
  `_dt` bigint(20) NOT NULL,
  PRIMARY KEY (`_gid`, `_mid`)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `_uid` varchar(64) NOT NULL,
  `_name` varchar(64) DEFAULT '',
  `_display_name` varchar(64) DEFAULT '',
  `_portrait` varchar(1024) DEFAULT '',
  `_mobile` varchar(64) DEFAULT '',
  `_email` varchar(64) DEFAULT '',
  `_address` varchar(64) DEFAULT '',
  `_company` varchar(64) DEFAULT '',
  `_social` varchar(64) DEFAULT '',
  `_passwd_md5` varchar(64) DEFAULT '',
  `_salt` varchar(64) DEFAULT '',
  `_extra` TEXT DEFAULT NULL,
  `_dt` bigint(20) NOT NULL,
  PRIMARY KEY (`_uid`),
  UNIQUE INDEX `user_name_index` (`_name` ASC),
  INDEX `user_display_name_index` (`_display_name` ASC),
  INDEX `user_mobile_index` (`_mobile` ASC),
  INDEX `user_email_index` (`_email` ASC)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `t_friend_request`;
CREATE TABLE `t_friend_request` (
  `_uid` varchar(64) NOT NULL,
  `_friend_uid` varchar(64) NOT NULL,
  `_reason` TEXT DEFAULT NULL,
  `_status` tinyint NOT NULL DEFAULT 0,
  `_dt` bigint(20) NOT NULL,
  `_from_read_status` tinyint DEFAULT 0,
  `_to_read_status` tinyint DEFAULT 0,
  UNIQUE INDEX `fr_user_target_index` (`_uid` ASC, `_friend_uid` ASC)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


DROP TABLE IF EXISTS `t_friend`;
CREATE TABLE `t_friend` (
  `_uid` varchar(64) NOT NULL,
  `_friend_uid` varchar(64) NOT NULL,
  `_state` tinyint DEFAULT 0 COMMENT "0, normal; 1, deleted",
  `_dt` bigint(20) NOT NULL,
  INDEX `fr_user_target_index` (`_uid` ASC, `_friend_uid` ASC)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;
