--liquibase formatted sql
--changeset andrew.siquieri:1.0.1 endDelimiter:\

/* TODO: Create tables
do $$
DECLARE
  counted integer;
BEGIN

  SELECT COUNT(table_name) INTO counted FROM information_schema.tables WHERE table_name = 'USER_INFO';
  IF counted = 0 THEN
    EXECUTE 'CREATE TABLE USER_INFO (
      ID        SERIAL       NOT NULL,
      EMAIL     VARCHAR(255) NOT NULL,
      PASSWORD  VARCHAR(255) NOT NULL,
      USER_NAME VARCHAR(255) NOT NULL,
      PRIMARY KEY (ID))';
  END IF;
END $$
\*/
