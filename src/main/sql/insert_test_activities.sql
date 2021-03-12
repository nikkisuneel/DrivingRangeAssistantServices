-- A script to create test data to populate the activity table
-- Used for generating data trend charts

DELETE FROM driving_range.ball_picking_activity;

INSERT INTO driving_range.ball_picking_activity (activity_date, ball_count, picker_counts, start_time, end_time)
SELECT 
	d activity_date,
	CASE WHEN EXTRACT(MONTH from d) >= 1 AND EXTRACT(MONTH from d) <= 3
			THEN floor(random() * 300 + 200)::int
		 WHEN EXTRACT(MONTH from d) >= 4 AND EXTRACT(MONTH from d) <= 5
			THEN floor(random() * 400 + 400)::int
		 WHEN EXTRACT(MONTH from d) >= 6 AND EXTRACT(MONTH from d) <= 9
			THEN floor(random() * 200 + 800)::int
		 WHEN EXTRACT(MONTH from d) >= 10 AND EXTRACT(MONTH from d) <= 12
			THEN floor(random() * 300 + 200)::int 	
		 ELSE 0
	END ball_count,
	'{''Manual-small'': 2, ''Automatic-small'': 2, ''Automatic-medium'': 1, ''Automatic-large'': 1}' picker_count,
	CASE WHEN EXTRACT(MONTH from d) >= 1 AND EXTRACT(MONTH from d) <= 3
			THEN date_trunc('day', d) + '16:00:00' 
		 WHEN EXTRACT(MONTH from d) >= 4 AND EXTRACT(MONTH from d) <= 5
			THEN date_trunc('day', d) + '18:00:00'
		 WHEN EXTRACT(MONTH from d) >= 6 AND EXTRACT(MONTH from d) <= 9
			THEN date_trunc('day', d) + '19:00:00'
		 WHEN EXTRACT(MONTH from d) >= 10 AND EXTRACT(MONTH from d) <= 12
			THEN date_trunc('day', d) + '17:00:00' 	
		 ELSE date_trunc('day', d)
	END start_time,
	CASE WHEN EXTRACT(MONTH from d) >= 1 AND EXTRACT(MONTH from d) <= 3
			THEN date_trunc('day', d) + '16:00:00' + (floor(random() * 20 + 20) * interval '1 minute')
		 WHEN EXTRACT(MONTH from d) >= 4 AND EXTRACT(MONTH from d) <= 5
			THEN date_trunc('day', d) + '18:00:00' + (floor(random() * 20 + 40) * interval '1 minute')
		 WHEN EXTRACT(MONTH from d) >= 6 AND EXTRACT(MONTH from d) <= 9
			THEN date_trunc('day', d) + '19:00:00' + (floor(random() * 30 + 60) * interval '1 minute')
		 WHEN EXTRACT(MONTH from d) >= 10 AND EXTRACT(MONTH from d) <= 12
			THEN date_trunc('day', d) + '17:00:00' + (floor(random() * 20 + 20) * interval '1 minute')
		 ELSE date_trunc('day', d)
	END end_time
from generate_series(
  current_timestamp - interval '1 year', 
  current_timestamp, 
  '1 day'
) d 
