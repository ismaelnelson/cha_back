INSERT INTO `railway`.`roles` (`id`, `name`)
SELECT '1', 'ADMIN'
    WHERE NOT EXISTS (SELECT 1 FROM `railway`.`roles` WHERE `id` = '1');

INSERT INTO `railway`.`roles` (`id`, `name`)
SELECT '2', 'USER'
    WHERE NOT EXISTS (SELECT 1 FROM `railway`.`roles` WHERE `id` = '2');

INSERT INTO `railway`.`users` (`id`, `email`, `name`, `nick`,`photo`,`password`,`roles`)
SELECT '1','admin@chakruk.com','ADMIN','ADMIN','rey-animal.png','$2a$10$iY0.yv579OFity1Hqy2ybe6YBEsV7QrSOzOl8F1GhKjwjlBTG3nzO','1'
    WHERE NOT EXISTS (SELECT 1 FROM `railway`.`users` WHERE `id` = '1');
