CREATE TABLE IF NOT EXISTS ACCOUNTS (
    accountId         uuid PRIMARY KEY,
    nickname          VARCHAR(100),
    email             VARCHAR(100),
    password          VARCHAR(100),
    description       VARCHAR(200),
    suburb            VARCHAR(100),
    profileImageUrl   VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS CHILDREN (
    id         uuid PRIMARY KEY,
    accountId  uuid,
    name       VARCHAR(100),
    age        INTEGER,

    FOREIGN KEY (accountId) REFERENCES ACCOUNTS(accountId)
);