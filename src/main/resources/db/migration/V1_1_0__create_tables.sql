CREATE TABLE RECIPES
(
    id               serial      NOT NULL PRIMARY KEY,
    name             text        NOT NULL,
    description      text,
    instructions     text,
    created_at       timestamptz NOT NULL,
    last_modified_at timestamptz NOT NULL
);

CREATE TABLE INGREDIENTS
(
    id               serial      NOT NULL PRIMARY KEY,
    name             text        NOT NULL,
    quantity         text,
    recipe_id        bigint,
    created_at       timestamptz NOT NULL,
    last_modified_at timestamptz NOT NULL,
    CONSTRAINT fk_recipe
        FOREIGN KEY (recipe_id)
            REFERENCES recipes (id)
);