-- Inserir categorias
INSERT INTO tb_categoria (nome) VALUES ('Camisa');
INSERT INTO tb_categoria (nome) VALUES ('Bone');
INSERT INTO tb_categoria (nome) VALUES ('Calça');

-- Inserir produtos
INSERT INTO tb_produto (nome, preco, descricao, img_url) VALUES ('NIKE', 90.5, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'https://trilhaesportes.fbitsstatic.net/img/p/camiseta-nike-dri-fit-academy-23-masculina-preto-72016/274916-1.jpg?w=800&h=800&v=no-value');
INSERT INTO tb_produto (nome, preco, descricao, img_url) VALUES ('NIKE', 2190.0, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'https://trilhaesportes.fbitsstatic.net/img/p/camiseta-nike-dri-fit-academy-23-masculina-preto-72016/274916-1.jpg?w=800&h=800&v=no-value');
INSERT INTO tb_produto (nome, preco, descricao, img_url) VALUES ('NIKE', 1250.0, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'https://trilhaesportes.fbitsstatic.net/img/p/camiseta-nike-dri-fit-academy-23-masculina-preto-72016/274916-1.jpg?w=800&h=800&v=no-value');
INSERT INTO tb_produto (nome, preco, descricao, img_url) VALUES ('NIKE', 1200.0, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'https://trilhaesportes.fbitsstatic.net/img/p/camiseta-nike-dri-fit-academy-23-masculina-preto-72016/274916-1.jpg?w=800&h=800&v=no-value');
INSERT INTO tb_produto (nome, preco, descricao, img_url) VALUES ('NIKE', 100.99, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'https://trilhaesportes.fbitsstatic.net/img/p/camiseta-nike-dri-fit-academy-23-masculina-preto-72016/274916-1.jpg?w=800&h=800&v=no-value');
INSERT INTO tb_produto (nome, preco, descricao, img_url) VALUES ('NIKE', 1350.0, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'https://trilhaesportes.fbitsstatic.net/img/p/camiseta-nike-dri-fit-academy-23-masculina-preto-72016/274916-1.jpg?w=800&h=800&v=no-value');
-- Continue com os outros produtos no mesmo formato

-- Inserir associações entre produtos e categorias
INSERT INTO tb_produto_categoria (produto_id, categoria_id) VALUES (1, 1);
INSERT INTO tb_produto_categoria (produto_id, categoria_id) VALUES (2, 2);
INSERT INTO tb_produto_categoria (produto_id, categoria_id) VALUES (2, 3);
INSERT INTO tb_produto_categoria (produto_id, categoria_id) VALUES (3, 3);
-- Continue com as outras associações no mesmo formato

-- Inserir usuários
INSERT INTO tb_user (nome, email, telefone, senha, data_nascimento) VALUES ('Elias Santos', 'elias@gmail.com', '988888888', '123456', '2001-07-25');
INSERT INTO tb_user (nome, email, telefone, senha, data_nascimento) VALUES ('Alex Costa', 'alex@gmail.com', '977777777', '123456', '1987-12-13');

-- Inserir pedidos
INSERT INTO tb_pedido (momento, status, cliente_id) VALUES ('2022-07-25 13:00:00', 1, 1);
INSERT INTO tb_pedido (momento, status, cliente_id) VALUES ('2022-07-29 15:50:00', 3, 2);
INSERT INTO tb_pedido (momento, status, cliente_id) VALUES ('2022-08-03 14:20:00', 0, 1);

-- Inserir itens de pedidos
INSERT INTO tb_pedido_item (pedido_id, produto_id, quantidade, preco) VALUES (1, 1, 2, 90.5);
INSERT INTO tb_pedido_item (pedido_id, produto_id, quantidade, preco) VALUES (1, 3, 1, 1250.0);
INSERT INTO tb_pedido_item (pedido_id, produto_id, quantidade, preco) VALUES (2, 3, 1, 1250.0);
INSERT INTO tb_pedido_item (pedido_id, produto_id, quantidade, preco) VALUES (3, 1, 1, 90.5);

-- Inserir pagamentos
INSERT INTO tb_pagamento (pedido_id, momento) VALUES (1, '2022-07-25 15:00:00');
INSERT INTO tb_pagamento (pedido_id, momento) VALUES (2, '2022-07-30 11:00:00');tb_categoria