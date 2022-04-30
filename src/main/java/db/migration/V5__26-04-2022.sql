INSERT INTO acessos_end_point(
	nome_end_point, qtd_acesso_end_point)
	VALUES ('CONSULTA_PF_NOME', 0);
	
ALTER TABLE acessos_end_point ADD CONSTRAINT nome_end_point_unique UNIQUE (nome_end_point);