CREATE TABLE `comment` (
`id`  bigint(20) NULL DEFAULT NULL ,
`parent_id`  bigint(20) NULL DEFAULT NULL ,
`type`  int(11) NULL DEFAULT NULL ,
`commentator_id`  int(11) NULL DEFAULT NULL ,
`gmt_create`  bigint(20) NULL DEFAULT NULL ,
`gmt_modified`  bigint(20) NULL DEFAULT NULL ,
`like_count`  bigint(20) NULL DEFAULT 0 ,
`content`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
ROW_FORMAT=COMPACT
;

