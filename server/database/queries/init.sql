CREATE DATABASE lingo;
USE lingo;

CREATE TABLE IF NOT EXISTS user (
	id INT AUTO_INCREMENT,
	username VARCHAR(100) NOT NULL UNIQUE,
	password VARCHAR(100) NOT NULL,
	score INT DEFAULT 0,
	rating INT DEFAULT 0, 
	role ENUM("LEANER", "TEACHER", "ADMIN") NOT NULL,
	phone_number VARCHAR(15),
	email VARCHAR NOT NULL UNIQUE,
	PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS community {
	id INT AUTO_INCREMENT,
	name VARCHAR(100) NOT NULL UNIQUE,
	manager INT NOT NULL, 
	description NVARCHAR(100),
	number_of_users INT DEFAULT 0,
	date_create DATETIME,
	PRIMARY_KEY (id),
	FOREIGN KEY (manager) REFERENCES user(id) ON DELETE CASCADE ON UPDATE CASCADE
};

CREATE TABLE IF NOT EXISTS post {
	community_id INT, 
	id INT AUTO_INCREMENT,
	author INT,
	content NVARCHAR(1024),
	date_posted DATETIME,
	number_of_likes INT DEFAULT 0,
	number_of_dislikes INT DEFAULT 0,
	number_of_views DEFAULT 0,
	PRIMARY KEY (community_id, id),
	FOREIGN KEY (community_id) REFERENCES community(id) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (author) REFERENCES user(id) ON DELETE CASCADE ON UPDATE CASCADE,
};

CREATE TABLE IF NOT EXISTS comment {
	community_id INT,
	post_id INT,
	id INT AUTO_INCREMENT,
	author VARCHAR(32),
	parent INT,
	time DATETIME,
	content NVARCHAR(1024),
	likes INT DEFAULT 0,
	dislikes INT DEFAULT 0,
	PRIMARY KEY (community_id, post_id, id),
	FOREIGN KEY (post_id) REFERENCES post(id) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (author) REFERENCES user(id) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (parent) REFERENCES comment(id) ON DELETE CASCADE ON UPDATE CASCADE
};

CREATE TABLE IF NOT EXISTS vocabulary {
	id INT AUTO_INCREMENT,
	user_id INT NOT NULL,
	text VARCHAR(128) NOT NULL,
	meaning NVARCHAR(1024) NOT NULL,
	pronun_es_us BLOB,
	pronun_en_uk BLOB,
	PRIMARY KEY (user_id, id),
	FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE ON UPDATE CASCADE
};

CREATE TABLE IF NOT EXISTS question {
	id INT AUTO_INCREMENT,
	score INT,
	content NVARCHAR(1024),
	answer VARCHAR(32),
	explanation NVARCHAR(1024), 
	PRIMARY KEY (id)
};

CREATE TABLE IF NOT EXIST answer {
	user_id INT NOT NULL,
	question_id INT NOT NULL,
	id INT AUTO_INCREMENT,
	content NVARCHAR(1024),
	PRIMARY KEY (user_id, question_id, id),
	FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (question_id) REFERENCES question(id) ON DELETE CASCADE ON UPDATE CASCADE
};

CREATE TABLE IF NOT EXISTS mistake {
	id INT AUTO_INCREMENT,
	user_id INT NOT NULL,
	question_id INT NOT NULL,
	tutorial VARCHAR(1024),
	time_stamp DATETIME,
	PRIMARY KEY (user_id, question_id, id),
	FOREIGN KEY (user_id, question_id) REFERENCES QUESTION(
};

CREATE TABLE IF NOT EXISTS lesson {
	id INT AUTO_INCREMENT,
	author VARCHAR(32) NOT NULL,
	time_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	type VARCHAR(1024) NOT NULL,
	score INT DEFAULT 0,
	popularity_score INT DEFAULT 0, 
	difficulty INT DEFAULT 0,
	PRIMARY KEY (id),
	FOREIGN KEY (author) REFERENCES user(author) ON DELETE CASCADE ON UPDATE CASCADE
};

CREATE TABLE IF NOT EXISTS review {
	id INT AUTO_INCREMENT,
	lesson_id INT NOT NULL,
	author INT NOT NULL ,
	date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	star FLOAT DEFAULT 1.0,
	feedback VARCHAR(1024),
	PRIMARY KEY (lesson_id, id),
	FOREIGN KEY (lesson_id) REFERENCES lesson(id),
	FOREIGN KEY (author) REFERENCES user(id)
};

CREATE TABLE IF NOT EXISTS contest {
	id INT AUTO_INCREMENT,
	creator INT NOT NULL,
	contest_name VARCHAR(1024) NOT NULL UNIQUE,
	difficulty INT DEFAULT 0,
	time_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	time_begin DATETIME, 
	duration TIME,
	number_of_registers INT DEFAULT 0,
	PRIMARY KEY (id),
	FOREIGN KEY (creator) REFERENCES user(id) 
};

CREATE TABLE IF NOT EXISTS lesson_completion {
	lesson_id INT NOT NULL,
	user_id INT NOT NULL,
	time_completed TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	has_review BOOLEAN,
	score_received INT NOT NULL,
	PRIMARY KEY (lesson_id, user_id),
	FOREIGN KEY (lesson_id, user_id) 
};

CREATE TABLE IF NOT EXISTS community_membership {
	community_id INT AUTO_INCREMENT,
	user_id INT NOT NULL,
	date_joined TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	role ENUM("Admin", "Member")
	PRIMARY KEY (community_id, user_id),
	FOREIGN KEY (community_id) REFERENCES community(id),
	FOREIGN KEY (user_id) REFERENCES user(id)
};

CREATE TABLE IF NOT EXISTS lesson_question {
	lesson_id,
	question_id,
};

CREATE TABLE IF NOT EXISTS contest_question {
	contest_id,
	id,

};

CREATE TABLE IF NOT EXISTS contest_participation {
	contest_id,
	id,
	total_score,
	time_participated,
	rank,
};

CREATE TABLE IF NOT EXISTS achievement {
	id,
	name,
	content,
	criteria,	
};

CREATE TABLE IF NOT EXISTS user_achievement {
	id,
	achievement_id
	date_achieved,
};

CREATE TABLE IF NOT EXISTS notification {
	id,
	user_id,
	time,
	content,
	viewed,
};

CREATE TABLE IF NOT EXISTS community_membership {
	community_id,
	user_id,
	date_joined,
	role,
};
