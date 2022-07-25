insert into cozinha (nome) values('Tailandesa');
insert into cozinha (nome) values('Indiana');

insert into estado (nome) values ('Minas Gerais');
insert into estado (nome) values ('São Paulo');
insert into estado (nome) values ('Ceará');

insert into cidade (nome, estado_id) values ('Uberlândia', 1);
insert into cidade (nome, estado_id) values ('Belo Horizonte', 1);
insert into cidade (nome, estado_id) values ('São Paulo', 2);
insert into cidade (nome, estado_id) values ( 'Campinas', 2);
insert into cidade (nome, estado_id) values ('Fortaleza', 3);


insert into restaurante (nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro) values ('Thai Gourmet', 10, 1, LOCALTIMESTAMP,LOCALTIMESTAMP,1, '38400-999', 'Rua João Pinheiro', '1000', 'Centro');
insert into restaurante (nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) values('Thai Delivery',6.2,1,LOCALTIMESTAMP,LOCALTIMESTAMP);
insert into restaurante (nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) values('Tuk Tuk Comida Indiana',15,2,LOCALTIMESTAMP,LOCALTIMESTAMP);

insert into forma_pagamento (id, descricao) values (1, 'Cartão de crédito');
insert into forma_pagamento (id, descricao) values (2, 'Cartão de débito');
insert into forma_pagamento (id, descricao) values (3, 'Dinheiro');

insert into permissao (id, nome, descricao) values (1, 'CONSULTAR_COZINHAS', 'Permite consultar cozinhas');
insert into permissao (id, nome, descricao) values (2, 'EDITAR_COZINHAS', 'Permite editar cozinhas');

insert into restaurante_forma_pagamento (restaurante_id,forma_pagamento_id) values(1,1),(1,2),(1,3),(2,3),(3,2),(3,3);