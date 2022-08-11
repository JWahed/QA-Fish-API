DROP TABLE IF EXISTS `fish` CASCADE;

CREATE TABLE `fish` (
        `id`          BIGINT PRIMARY KEY AUTO_INCREMENT,
        `name`        VARCHAR(255),
        `date_caught` DATE,
        `quantity`    INTEGER,
        `price`       DOUBLE PRECISION
);
