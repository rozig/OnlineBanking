insert into admin (date_of_birth, email, firstname, lastname, password, phone_number, token, token_created)
    values (now(), "tamirsnarf@gmail.com", "Tamir", "Batmunkh", "password", "99887788", "8md9Eb1aGDyfe259nnA4aS3ezB3ySEv8TLsVGFKPPCI=", now());

insert into customer(date_of_birth, email, firstname, lastname, password, phone_number, token, token_created, is_activated, ssn, monthly_income, credit_score)
values (now(), "gerdenebat@mum.edu", "Ganzorig", "Erdenebat", "password", "99999999", "8md9Eb1aGDyfe259nnA4aS3ezB3ySEv8TLsVGFKPPCI=", now(), "Y", "985745", 5000, 200);

insert into customer(date_of_birth, email, firstname, lastname, password, phone_number, token, token_created, is_activated, ssn, monthly_income, credit_score)
values (now(), "tbatmunkh@mum.edu", "Tamir", "Batmunkh", "90s880s4", "99111111", "8md9Eb1aGDyfe259nnA4aS3ezB3ySEv8TLsVGFKPPCI=", now(), "Y", "933748", 8000, 400);

insert into rule (max_tran_limit) VALUES (2000);

update customer set rule_id = 1 where id = 1;

insert into rule (max_tran_limit) VALUES (3000);

update customer set rule_id = 2 where id = 2;

insert into account (account_name, account_open_date, balance, is_activated, is_default, customer_id) values
  ("Ganzoo dans 3", now(), 1000, "Y", "N", 1);


insert into account (account_name, account_open_date, balance, is_activated, is_default, customer_id) values
  ("Ganzo dans 1", now(), 10000, "Y", "N", (select id from customer where id=1));


insert into account (account_name, account_open_date, balance, is_activated, is_default, customer_id) values
  ("Ganzo dans 2", now(), 20000, "Y", "N", (select id from customer where id=1));

insert into account (account_name, account_open_date, balance, is_activated, is_default, customer_id) values
  ("Tamir", now(), 20000, "Y", "Y", (select id from customer where id=2));

insert into account (account_name, account_open_date, balance, is_activated, is_default, customer_id) values
  ("Tamir", now(), 30000, "Y", "N", (select id from customer where id=2));

insert into saving_account (account_id, interest_rate, maturity_date) values
  (1, 10, now());

insert into saving_account (account_id, interest_rate, maturity_date) values
  (2, 12, now());

insert into transaction (amount, channel_id, transaction_date, credit_account_account_id, debit_account_account_id)
values (1000, "ByAccountId", now(), 1, 2);

insert into transaction (amount, channel_id, transaction_date, credit_account_account_id, debit_account_account_id)
values (3000, "ByAccountId", now(), 2, 1);

insert into transaction (amount, channel_id, transaction_date, credit_account_account_id, debit_account_account_id)
values (70000, "ByAccountId", now(), 3, 1);

insert into transaction (amount, channel_id, transaction_date, credit_account_account_id, debit_account_account_id)
values (10000, "ByAccountId", now(), 2, 3);

insert into currency_rate (buy_rate, currency_code, date, sale_rate) values (70, "MNT", now(), 75);
insert into currency_rate (buy_rate, currency_code, date, sale_rate) values (20, "JPY", now(), 25);
insert into currency_rate (buy_rate, currency_code, date, sale_rate) values (10, "CNY", now(), 15);
insert into currency_rate (buy_rate, currency_code, date, sale_rate) values (27, "RUB", now(), 30);

insert into account_book (customer_id) values ((select id from customer where id=1));
insert into account_book (customer_id) values ((select id from customer where id=2));
