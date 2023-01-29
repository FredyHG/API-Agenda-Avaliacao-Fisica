CREATE TABLE avaliacao(
    id bigint not null primary key,
    datahora date,
    status boolean,
    alergias varchar(130),
    limitacoesfisica varchar(130),
    clientId int REFERENCES cliente(id)
);
