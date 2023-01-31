-- liquibase formatted sql

-- changeset ashabelskii:1
CREATE TABLE IF NOT EXISTS applications
(
    id            UUID                        NOT NULL,
    client_id     UUID,
    credit_id     UUID,
    status        VARCHAR(255),
    creation_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    applied_offer JSONB,
    sign_date     TIMESTAMP WITHOUT TIME ZONE,
    ses_code      VARCHAR(255),
    CONSTRAINT pk_applications PRIMARY KEY (id)
);

CREATE TABLE applications_status_history
(
    application_id    UUID NOT NULL,
    status_history_id UUID NOT NULL
);

-- changeset ashabelskii:2
CREATE TABLE IF NOT EXISTS clients
(
    id               UUID NOT NULL,
    first_name       VARCHAR(255),
    last_name        VARCHAR(255),
    middle_name      VARCHAR(255),
    birthdate        date,
    email            VARCHAR(255),
    gender           VARCHAR(255),
    marital_status   VARCHAR(255),
    dependent_amount INTEGER,
    passport_id      UUID,
    employment_id    UUID,
    account          VARCHAR(255),
    CONSTRAINT pk_clients PRIMARY KEY (id)
);

-- changeset ashabelskii:3
CREATE TABLE IF NOT EXISTS credits
(
    id               UUID NOT NULL,
    amount           DECIMAL,
    term             INTEGER,
    monthly_payment  DECIMAL,
    rate             DECIMAL,
    psk              DECIMAL,
    payment_schedule JSONB,
    insurance_enable BOOLEAN,
    salary_client    BOOLEAN,
    status           VARCHAR(255),
    CONSTRAINT pk_credits PRIMARY KEY (id)
);

-- changeset ashabelskii:4
CREATE TABLE IF NOT EXISTS employments
(
    id                      UUID NOT NULL,
    status                  VARCHAR(255),
    employer_inn            VARCHAR(255),
    salary                  DECIMAL,
    position                VARCHAR(255),
    work_experience_total   INTEGER,
    work_experience_current INTEGER,
    CONSTRAINT pk_employments PRIMARY KEY (id)
);

-- changeset ashabelskii:5
CREATE TABLE IF NOT EXISTS passports
(
    id           UUID NOT NULL,
    series       VARCHAR(255),
    number       VARCHAR(255),
    issue_branch VARCHAR(255),
    issue_date   date,
    CONSTRAINT pk_passports PRIMARY KEY (id)
);

-- changeset ashabelskii:6
CREATE TABLE IF NOT EXISTS status_history
(
    id          UUID NOT NULL,
    status      VARCHAR(255),
    time        TIMESTAMP WITHOUT TIME ZONE,
    change_type VARCHAR(255),
    CONSTRAINT pk_status_history PRIMARY KEY (id)
);

-- changeset ashabelskii:7
ALTER TABLE IF EXISTS applications
    ADD CONSTRAINT uc_applications_id UNIQUE (id);

ALTER TABLE IF EXISTS applications_status_history
    ADD CONSTRAINT uc_applications_status_history_apidstid UNIQUE (application_id, status_history_id);

ALTER TABLE IF EXISTS applications
    ADD CONSTRAINT FK_APPLICATIONS_ON_CLIENT FOREIGN KEY (client_id) REFERENCES clients (id);

ALTER TABLE IF EXISTS applications
    ADD CONSTRAINT FK_APPLICATIONS_ON_CREDIT FOREIGN KEY (credit_id) REFERENCES credits (id);

ALTER TABLE IF EXISTS applications_status_history
    ADD CONSTRAINT fk_appstahis_on_application FOREIGN KEY (application_id) REFERENCES applications (id);

ALTER TABLE IF EXISTS applications_status_history
    ADD CONSTRAINT fk_appstahis_on_status_history FOREIGN KEY (status_history_id) REFERENCES status_history (id);

-- changeset ashabelskii:8
ALTER TABLE IF EXISTS clients
    ADD CONSTRAINT uc_clients_id UNIQUE (id);

ALTER TABLE IF EXISTS clients
    ADD CONSTRAINT FK_CLIENTS_ON_EMPLOYMENT FOREIGN KEY (employment_id) REFERENCES employments (id);

ALTER TABLE IF EXISTS clients
    ADD CONSTRAINT FK_CLIENTS_ON_PASSPORT FOREIGN KEY (passport_id) REFERENCES passports (id);

-- changeset ashabelskii:9
ALTER TABLE IF EXISTS credits
    ADD CONSTRAINT uc_credits_id UNIQUE (id);

-- changeset ashabelskii:10
ALTER TABLE IF EXISTS employments
    ADD CONSTRAINT uc_employments_id UNIQUE (id);

-- changeset ashabelskii:11
ALTER TABLE IF EXISTS passports
    ADD CONSTRAINT uc_passports_id UNIQUE (id);

-- changeset ashabelskii:12
ALTER TABLE IF EXISTS status_history
    ADD CONSTRAINT uc_status_history_id UNIQUE (id);






