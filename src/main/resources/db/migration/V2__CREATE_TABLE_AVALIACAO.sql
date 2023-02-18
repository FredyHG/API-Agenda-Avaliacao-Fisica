CREATE TABLE avaliacao(
    id SERIAL primary key,
    datahora date,
    status boolean,
    alergias varchar(130),
    limitacoesfisica varchar(130),
    clienteid int REFERENCES cliente(id)
);
