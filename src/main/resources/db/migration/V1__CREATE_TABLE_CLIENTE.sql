CREATE TABLE cliente(
    id int primary key,
    nome varchar(100) NOT NULL ,
    sobrenome varchar (100) NOT NULL ,
    nascimentoDate date NOT NULL ,
    idade varchar(4),
    telefone varchar (30),
    cpf varchar(30) NOT NULL
);