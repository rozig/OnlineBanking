insert into admin (date_of_birth, email, firstname, lastname, password, phone_number, token, token_created)
    values (now(), "tamirsnarf@gmail.com", "Tamir", "Batmunkh", "password", "99887788", "8md9Eb1aGDyfe259nnA4aS3ezB3ySEv8TLsVGFKPPCI=", now());

# insert into customer(date_of_birth, email, firstname, lastname, password, phone_number, token, token_created)
# values (now(), "tbatmunkh@mum.edu", "Tamir", "Batmunkh", "90s880s4", "99111111", "8md9Eb1aGDyfe259nnA4aS3ezB3ySEv8TLsVGFKPPCI=", now());

insert into customer(date_of_birth, email, firstname, lastname, password, phone_number, token, token_created, is_activated, ssn)
values (now(), "gerdenebat@mum.edu", "Ganzorig", "Erdenebat", "password", "99999999", "8md9Eb1aGDyfe259nnA4aS3ezB3ySEv8TLsVGFKPPCI=", now(), "Y", "985745");

insert into account (account_name, account_open_date, balance, is_activated, is_default, customer_id) values
  ("Ganzo dans 1", now(), 10000, "Y", "Y", (select id from customer where id=1));


insert into account (account_name, account_open_date, balance, is_activated, is_default, customer_id) values
  ("Ganzo dans 2", now(), 20000, "Y", "Y", (select id from customer where id=1));

insert into saving_account (account_id, interest_rate, maturity_date) values
  (1, 10, now());

insert into transaction (amount, channel_id, transaction_date, credit_account_account_id, debit_account_account_id)
values (7000, "ByAccountId", now(), 1, 2);

insert into account (account_name, account_open_date, balance, is_activated, is_default, customer_id) values
  ("Ganzoo dans 3", now(), 1000, "Y", "Y", 1);
