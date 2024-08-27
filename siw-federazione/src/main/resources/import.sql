insert into utente(id, nome, cognome, CF, data_nascita, luogo_nascita) values(nextval('utente_seq'), 'mario', 'rossi','RSSMRA61E08F205V','8/5/1961','milano');
insert into credentials(id, username, password, role, user_id, email) values(nextval('credentials_seq'), 'mario', 'mario', 'ROLE_ADMIN', 1, 'mario.rossi@gmail.com');

insert into utente(id, nome, cognome,CF, data_nascita, luogo_nascita) values(nextval('utente_seq'), 'federico', 'verdi', 'VRDFRC55L23L219O','23/7/1955','torino');
insert into credentials(id, username, password, role, user_id, email) values(nextval('credentials_seq'), 'luca', 'luca', 'ROLE_PRESIDENT', 51, 'luca.bianchi@gmail.com');

insert into utente(id, nome, cognome, CF, data_nascita, luogo_nascita) values(nextval('utente_seq'), 'marco', 'gialli', ' GLLMRC65S11H501B','11/11/1965','roma');
insert into credentials(id, username, password, role, user_id, email) values(nextval('credentials_seq'), 'marco', 'marco', 'ROLE_USER', 101, 'marco.gialli@gmail.com');

insert into utente(id, nome, cognome, CF, data_nascita, luogo_nascita) values(nextval('utente_seq'), 'francesco', 'bruni', ' GLLMRC65S11H501B','13/12/1975','roma');
insert into credentials(id, username, password, role, user_id, email) values(nextval('credentials_seq'), 'francesco', 'francesco', 'ROLE_PRESIDENT', 151, 'francesco.bruni@gmail.com');

insert into squadra(id, nome, fondazione, indirizzo_sede,presidente_id) values(nextval('squadra_seq'), 'pagnotta', 1976, 'via delle polpette 67', 51);
insert into squadra(id, nome, fondazione, indirizzo_sede,presidente_id) values(nextval('squadra_seq'), 'balene', 1988, 'via del mare 23', 51);

insert into giocatore(id, nome, cognome, data_nascita, luogo_nascita, ruolo) values(nextval('giocatore_seq'), 'filippo', 'neri', '1/5/1989', 'bari', 'attaccante');
insert into giocatore(id, nome, cognome, data_nascita, luogo_nascita, ruolo) values(nextval('giocatore_seq'), 'ernesto', 'marroni', '9/9/1999', 'firenze', 'centrocampista');

