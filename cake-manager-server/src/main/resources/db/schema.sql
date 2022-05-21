DROP TABLE IF EXISTS cake;

CREATE TABLE cake
(
    id          VARCHAR(40) PRIMARY KEY,
    title       VARCHAR(100) NOT NULL,
    description VARCHAR(500) NOT NULL,
    image_url   VARCHAR(300) NOT NULL,
    CONSTRAINT titleAK UNIQUE KEY (title)
);