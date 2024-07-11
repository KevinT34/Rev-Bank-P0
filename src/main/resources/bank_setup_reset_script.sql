DROP TABLE IF EXISTS "user";



CREATE TABLE "user" (
	user_id INTEGER PRIMARY KEY AUTOINCREMENT,
	username TEXT,
	password TEXT
);

CREATE TABLE Accounts (
	account_number INTEGER PRIMARY KEY,
	balance INTEGER,
	user_id INTEGER REFERENCES "user"(user_id)
);

INSERT INTO "user" (username, password) VALUES ('admin', 1234);
INSERT INTO "user" (username, password) VALUES ('admin2', 5678);

