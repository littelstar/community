ALTER TABLE `comment`
MODIFY COLUMN `id`  bigint(20) NOT NULL AUTO_INCREMENT FIRST ,
ADD PRIMARY KEY (`id`);
ALTER TABLE `comment`
MODIFY COLUMN `commentator_id`  bigint(11) NULL DEFAULT NULL AFTER `type`;

