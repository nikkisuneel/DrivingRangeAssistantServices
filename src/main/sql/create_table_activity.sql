-- Table: driving_range.activity

-- DROP TABLE driving_range.activity;

CREATE TABLE driving_range.activity (
    id SERIAL,
    activity_date TIMESTAMP NOT NULL,
    ball_count INTEGER NOT NULL,
    picker_counts VARCHAR(500) NOT NULL,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP,
CONSTRAINT activity_pkey PRIMARY KEY (id)
)
