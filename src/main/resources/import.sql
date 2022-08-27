INSERT INTO cuenta (cod_cliente, numero_cuenta, moneda, saldo, estado_cuenta, creation_date, status) VALUES(1111, 11110001, 1, 4000, 1, NOW(), 1);
INSERT INTO cuenta (cod_cliente, numero_cuenta, moneda, saldo, estado_cuenta, creation_date, status) VALUES(1112, 11120001, 1, 12500, 1, NOW(), 1);
INSERT INTO cuenta (cod_cliente, numero_cuenta, moneda, saldo, estado_cuenta, creation_date, status) VALUES(1113, 11130001, 0, 1220, 1, NOW(), 1);
INSERT INTO cuenta (cod_cliente, numero_cuenta, moneda, saldo, estado_cuenta, creation_date, status) VALUES(1114, 11140001, 1, 250, 0, NOW(), 1);


INSERT INTO transaccion (tipo, numero_cuenta, moneda, monto, creation_date, status, cuenta_id) VALUES(1, 11110001, 1, 4000, NOW(), 1, 1);
INSERT INTO transaccion (tipo, numero_cuenta, moneda, monto, creation_date, status, cuenta_id) VALUES(1, 11120001, 0, 12500, NOW(), 1, 2);
INSERT INTO transaccion (tipo, numero_cuenta, moneda, monto, creation_date, status, cuenta_id) VALUES(1, 11130001, 0, 1220, NOW(), 1, 3);
INSERT INTO transaccion (tipo, numero_cuenta, moneda, monto, creation_date, status, cuenta_id) VALUES(1, 11140001, 1, 100, NOW(), 1, 4);
INSERT INTO transaccion (tipo, numero_cuenta, moneda, monto, creation_date, status, cuenta_id) VALUES(1, 11140001, 1, 150, NOW(), 1, 4);
