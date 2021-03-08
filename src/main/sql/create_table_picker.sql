-- Table: driving_range.picker

-- DROP TABLE driving_range.picker;

CREATE TABLE driving_range.picker (
    id SERIAL,
    name VARCHAR(50) NOT NULL,
    type VARCHAR(50) NOT NULL,
    throughput INTEGER,
CONSTRAINT picker_pkey PRIMARY KEY (id)
)
