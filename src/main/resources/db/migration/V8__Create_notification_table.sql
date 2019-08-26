CREATE TABLE `notification` (
`id`  bigint NOT NULL AUTO_INCREMENT ,
`notifier`  bigint NULL ,
`receiver`  bigint NULL ,
`outer_id`  bigint NULL COMMENT '用于记录评论的id' ,
`type`   INT NULL ,
`gmt_create`  bigint NULL ,
`status`  int NULL DEFAULT 0 ,
PRIMARY KEY (`id`)
)
;

