ALTER TABLE `user`
MODIFY COLUMN `id`  bigint(11) NOT NULL AUTO_INCREMENT FIRST ;
ALTER TABLE `question`
MODIFY COLUMN `id`  bigint(11) NOT NULL AUTO_INCREMENT FIRST ,
MODIFY COLUMN `creator`  bigint(11) NULL DEFAULT NULL AFTER `gmt_modified`;


