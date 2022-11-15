DROP TABLE IF EXISTS pdf_documents;


CREATE TABLE pdf_documents (
    id                BIGSERIAL PRIMARY KEY,
    user_id           int8 NOT NULL,
    file_name         varchar(255),
    data              oid,
    size              varchar(255),

   FOREIGN KEY (user_id) REFERENCES users (id)
);