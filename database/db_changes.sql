CREATE SEQUENCE transaction_log_id_seq;
CREATE TABLE transaction_logs (
  id bigint primary key DEFAULT nextval('transaction_log_id_seq'),
  payload text,
  date_created timestamp
);
ALTER SEQUENCE transaction_log_id_seq OWNED BY transaction_logs.id;

CREATE SEQUENCE customer_id_seq;
CREATE TABLE customers (
  id bigint primary key DEFAULT nextval('customer_id_seq'),
  name varchar(255) NOT NULL
);
ALTER SEQUENCE customer_id_seq OWNED BY customers.id;


CREATE SEQUENCE account_id_seq;
CREATE TABLE accounts (
   id bigint primary key DEFAULT nextval('account_id_seq'),
   number varchar(40) NOT NULL,
   customer_id bigint
);
ALTER SEQUENCE account_id_seq OWNED BY accounts.id;
ALTER TABLE accounts ADD CONSTRAINT accnt_cust_fk FOREIGN KEY (customer_id) REFERENCES customers(id);


CREATE SEQUENCE transaction_id_seq;
CREATE TABLE transactions (
  id bigint primary key DEFAULT nextval('transaction_id_seq'),
  code CHAR(4),
  date_created timestamp,
  account_id bigint
);
ALTER SEQUENCE transaction_id_seq OWNED BY transactions.id;
ALTER TABLE transactions ADD CONSTRAINT txn_accnt_fk FOREIGN KEY (account_id) REFERENCES accounts(id);
