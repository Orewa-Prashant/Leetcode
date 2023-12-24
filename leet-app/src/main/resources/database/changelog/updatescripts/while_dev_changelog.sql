-- liquibase formatted sql

-- changeset prashant:1
-- preconditions onFail:MARK_RAN onError:HALT
-- precondition-sql-check expectedResult:0 SELECT count(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'public' AND TABLE_NAME = 'app_user';
CREATE TABLE app_user(
                         app_user_id serial PRIMARY KEY,
                         email varchar(100)
);

-- changeset prashant:2
-- preconditions onFail:MARK_RAN onError:HALT
-- precondition-sql-check expectedResult:0 SELECT count(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'public' AND TABLE_NAME = 'subscribed_user';
CREATE TABLE subscribed_user(
                                subs_user_id serial PRIMARY KEY,
                                leetcode_username varchar(100)
);


-- changeset prashant:3
-- preconditions onFail:MARK_RAN onError:HALT
-- precondition-sql-check expectedResult:0 SELECT count(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'public' AND TABLE_NAME = 'subscriptions';
CREATE TABLE subscriptions(
                              app_user_id smallint references app_user(app_user_id),
                              leet_user_id smallint references subscribed_user(subs_user_id),

                              Primary key (app_user_id, leet_user_id)
);