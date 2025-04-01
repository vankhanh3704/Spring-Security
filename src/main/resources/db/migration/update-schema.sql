CREATE TABLE user_entity
(
    id            VARCHAR(255) NOT NULL,
    username      VARCHAR(255) NULL,
    password      VARCHAR(255) NULL,
    first_name    VARCHAR(255) NULL,
    last_name     VARCHAR(255) NULL,
    date_of_birth date NULL,
    CONSTRAINT pk_userentity PRIMARY KEY (id)
);

CREATE TABLE user_entity_roles
(
    user_entity_id VARCHAR(255) NOT NULL,
    roles_name     VARCHAR(255) NOT NULL,
    CONSTRAINT pk_userentity_roles PRIMARY KEY (user_entity_id, roles_name)
);

ALTER TABLE user_entity_roles
    ADD CONSTRAINT fk_useentrol_on_role FOREIGN KEY (roles_name) REFERENCES `role` (name);

ALTER TABLE user_entity_roles
    ADD CONSTRAINT fk_useentrol_on_user_entity FOREIGN KEY (user_entity_id) REFERENCES user_entity (id);