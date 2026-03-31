DO $$ BEGIN
CREATE TYPE token_type AS ENUM ('ACCESS','REFRESH');
EXCEPTION WHEN duplicate_object THEN null;
END $$;