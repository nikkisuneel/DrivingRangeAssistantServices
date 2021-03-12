-- Table: driving_range.ball_picking_activity

-- DROP TABLE driving_range.ball_picking_activity;

CREATE TABLE driving_range.ball_picking_activity (
    id SERIAL,
    activity_date TIMESTAMP NOT NULL,
    ball_count INTEGER NOT NULL,
    picker_counts VARCHAR(500) NOT NULL,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP,
CONSTRAINT ball_picking_activity_pkey PRIMARY KEY (id)
)
