PRAGMA foreign_keys = ON;

CREATE TABLE IF NOT EXISTS transactions(
    id TEXT PRIMARY KEY NOT NULL,
    amount INTEGER NOT NULL
);
