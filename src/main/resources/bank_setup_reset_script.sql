DROP TABLE IF EXISTS "user";
DROP TABLE IF EXISTS Accounts;



CREATE TABLE "user" (
	user_id INTEGER PRIMARY KEY AUTOINCREMENT,
	username TEXT,
	password TEXT
);

CREATE TABLE Accounts (
	account_number INTEGER PRIMARY KEY AUTOINCREMENT,
	balance INTEGER,
	user_id INTEGER REFERENCES "user"(user_id)
);

INSERT INTO "user" (username, password) VALUES ('admin', 1234);
INSERT INTO "user" (username, password) VALUES ('admin2', 5678);


