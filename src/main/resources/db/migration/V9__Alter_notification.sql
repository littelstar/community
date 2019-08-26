ALTER TABLE `notification`
ADD COLUMN `outer_title`  varchar(200) NULL AFTER `status`,
ADD COLUMN `notifier_title`  varchar(100) NULL AFTER `outer_title`;