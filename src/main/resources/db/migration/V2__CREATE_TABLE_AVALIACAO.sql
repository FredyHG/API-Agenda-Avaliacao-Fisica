CREATE TABLE avaliacao(
    id int primary key,
    datahora date,
    status boolean,
    alergias varchar(130),
    limitacoesfisica varchar(130),
    clientId int REFERENCES cliente(id)
);
