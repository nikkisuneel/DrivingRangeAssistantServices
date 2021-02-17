CREATE TABLE driving_range.activity (
id SERIAL,
activity_date timestamp NOT NULL,
ball_count integer NOT NULL,
picker_counts varchar(500) NOT NULL,
start_time timestamp,
end_time timestamp,
CONSTRAINT activity_pkey PRIMARY KEY (id)
)
