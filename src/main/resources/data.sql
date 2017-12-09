insert into customer(date_of_birth, email, firstname, lastname, password, phone_number, token, token_created, username)
values (now(), "tamir@mail.com", "Tamir", "Batmunkh", "90s880s4", "99111111", "tokenss", now(), "tamiraa");

insert into customer(date_of_birth, email, firstname, lastname, password, phone_number, token, token_created, username)
values (now(), "ganzo@mail.com", "Ganzorig", "Erdenebat", "password", "99999999", "tokens", now(), "ganzoo");

insert into account (account_name, account_open_date, balance, is_activated, is_default, customer_id) values
  ("Tamir dans 1", now(), 10000, "Y", "Y", (select id from customer where id=1));


insert into account (account_name, account_open_date, balance, is_activated, is_default, customer_id) values
  ("Ganzo dans 1", now(), 20000, "Y", "Y", (select id from customer where id=2));

insert into saving_account (account_id, interest_rate, maturity_date) values
  (1, 10, now());

insert into transaction (amount, channel_id, transaction_date, credit_account_account_id, debit_account_account_id)
values (7000, "ByAccountId", now(), 1, 2);
