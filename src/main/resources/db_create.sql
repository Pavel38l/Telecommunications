CREATE DATABASE telecom
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Russian_Russia.1251'
    LC_CTYPE = 'Russian_Russia.1251'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

CREATE TABLE "customer"
(
    "id"                     SERIAL PRIMARY KEY,
    "full_name"              varchar(50),
    "date_of_birth"          date,
    "sex"                    varchar(10),
    "passport_series_number" int
);

CREATE TABLE "channel_package"
(
    "id"          SERIAL PRIMARY KEY,
    "name"        varchar(50),
    "description" varchar(500)
);

CREATE TABLE "contract"
(
    "id"                     SERIAL PRIMARY KEY,
    "start_date"             date,
    "end_date"               date,
    "contract_number"        bigint,
    "customer_id"            bigint,
    "contract_type"          varchar(100),
    "number_of_minutes"      int,
    "number_of_sms"          int,
    "connection_speed"       double precision,
    "channel_package_id"     bigint
);

ALTER TABLE "contract" ADD FOREIGN KEY ("customer_id") REFERENCES "customer" ("id");
ALTER TABLE "contract" ADD FOREIGN KEY ("channel_package_id") REFERENCES "channel_package" ("id");