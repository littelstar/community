CREATE TABLE `user` (
  `id`  int(11) NOT NULL AUTO_INCREMENT ,
  `login`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
  `account_id`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
  `token`  char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
  `gmt_create`  bigint(20) NULL DEFAULT NULL ,
  `gmt_modefied`  bigint(20) NULL DEFAULT NULL ,
  PRIMARY KEY (`id`)
);