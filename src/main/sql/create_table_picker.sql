CREATE TABLE driving_range.picker (
id SERIAL,
name varchar(50) NOT NULL,
type varchar(50) NOT NULL,
throughput integer,
CONSTRAINT picker_pkey PRIMARY KEY (id)
)
