CREATE TABLE contest_detail(
        detail_id serial PRIMARY KEY,
        user_name varchar(100),
        rank smallint, score smallint,
        finish_time timestamp
);