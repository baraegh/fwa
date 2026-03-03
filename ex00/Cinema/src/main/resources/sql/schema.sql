Create table users (
    id              SERIAL PRIMARY KEY,
    first_name      VARCHAR(50) NOT NULL,
    last_name       VARCHAR(50) NOT NULL,
    phone_number    VARCHAR(15),
    password        VARCHAR(100) NOT NULL,
    email           VARCHAR(100) NOT NULL UNIQUE,
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

Create table login_history (
    id          SERIAL PRIMARY KEY,
    user_id     BIGINT,
    ip_address  VARCHAR(50) NOT NULL,
    date        DATE NOT NULL,
    time        TIME NOT NULL
);

Create table images (
    id          SERIAL PRIMARY KEY,
    user_id     BIGINT,
    file_name   VARCHAR(255),
    mime_type   VARCHAR(100),
    size        BIGINT,
    file_path   VARCHAR(255)
);