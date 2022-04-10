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

CREATE TABLE IF NOT EXISTS EVENTS (
   eventId               uuid PRIMARY KEY,
   eventImageURL         VARCHAR(100),
   eventDate             timestamp,
   location              VARCHAR(100),
   numOfParticipants     INTEGER,
   minAge                INTEGER,
   maxAge                INTEGER,
   eventDescription      VARCHAR(400)
);

CREATE TABLE IF NOT EXISTS CHILD_EVENT_MAPPING (
    childId         uuid,
    eventId         uuid,
    attending       boolean,

    PRIMARY KEY (childId, eventId),

    FOREIGN KEY (childId) REFERENCES CHILDREN(id),
    FOREIGN KEY (eventId) REFERENCES EVENTS(eventId)
)