DROP TABLE IF EXISTS `fish` CASCADE;

CREATE TABLE `fish` (
        `id`          BIGINT(255) PRIMARY KEY AUTO_INCREMENT,
        `name`        VARCHAR(255),
        `date_caught` DATE,
        `price`       DOUBLE PRECISION,
        `quantity`    INTEGER
);