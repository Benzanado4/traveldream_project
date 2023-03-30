DROP TABLE EXCURSION;
DROP TABLE FLIGHT;
DROP TABLE HOTEL;
DROP TABLE CITY;
DROP TABLE PURCHASE;
DROP TABLE PERS_PACKAGE;
DROP TABLE USERS_GROUPS;
DROP TABLE USERS;
DROP TABLE GIFTLIST;
DROP TABLE PARTECIPATE;
DROP TABLE INCLUDES;
DROP TABLE INCLUDES_PERS;
DROP TABLE DEFAULT_PACKAGE;
DROP TABLE PRODUCT;

CREATE TABLE USERS (USERNAME VARCHAR(255) NOT NULL, ADDRESS VARCHAR(255), EMAIL VARCHAR(255), NAME VARCHAR(255), PASSWORD VARCHAR(255), PHONENUMBER VARCHAR(255), SURNAME VARCHAR(255), PRIMARY KEY (USERNAME));
CREATE TABLE CITY (CITYNAME VARCHAR(255) NOT NULL, PRIMARY KEY (CITYNAME));
CREATE TABLE EXCURSION (DATE DATETIME, city VARCHAR(255), productId INTEGER NOT NULL, PRIMARY KEY (productId));
CREATE TABLE FLIGHT (DATE DATETIME, city1 VARCHAR(255), city2 VARCHAR(255), productId INTEGER NOT NULL, PRIMARY KEY (productId));
CREATE TABLE HOTEL (City VARCHAR(255), productId INTEGER NOT NULL, PRIMARY KEY (productId));
CREATE TABLE PRODUCT (PRODUCTID INTEGER AUTO_INCREMENT NOT NULL, DESCRIPTION VARCHAR(255), NAME VARCHAR(255), PRICE FLOAT, PRIMARY KEY (PRODUCTID));
CREATE TABLE DEFAULT_PACKAGE (ID INTEGER AUTO_INCREMENT NOT NULL, DESCRIPTION LONGTEXT, ENDDATE DATE, IMAGEID INTEGER, NAME VARCHAR(255), STARTDATE DATE, PRIMARY KEY (ID));
CREATE TABLE PURCHASE (ID INTEGER AUTO_INCREMENT NOT NULL, PERSPACKAGEID INTEGER, PURCHASEDATE DATE, USERNAME VARCHAR(255), PRIMARY KEY (ID));
CREATE TABLE PERS_PACKAGE (ID INTEGER AUTO_INCREMENT NOT NULL, DEFAULTPACKAGEID INTEGER, DESCRIPTION LONGTEXT, ENDDATE DATE, IMAGEID INTEGER, NAME VARCHAR(255), STARTDATE DATE, USERNAME VARCHAR(255), PRIMARY KEY (ID));
CREATE TABLE GIFTLIST (USERNAME VARCHAR(255), PERSPACKAGEID INTEGER NOT NULL, PRODUCTID INTEGER NOT NULL, PRIMARY KEY (PERSPACKAGEID, PRODUCTID));
CREATE TABLE USERS_GROUPS (username VARCHAR(255), groupname VARCHAR(255));
CREATE TABLE PARTECIPATE (username VARCHAR(255) NOT NULL, pers_package_id INTEGER NOT NULL, PRIMARY KEY (username, pers_package_id));
CREATE TABLE INCLUDES (product_id INTEGER NOT NULL, package_id INTEGER NOT NULL, PRIMARY KEY (product_id, package_id));
CREATE TABLE INCLUDES_PERS (pers_package_id INTEGER NOT NULL, product_id INTEGER NOT NULL, PRIMARY KEY (pers_package_id, product_id));

ALTER TABLE EXCURSION ADD CONSTRAINT FK_EXCURSION_city FOREIGN KEY (city) REFERENCES CITY (CITYNAME);
ALTER TABLE EXCURSION ADD CONSTRAINT FK_EXCURSION_productId FOREIGN KEY (productId) REFERENCES PRODUCT (PRODUCTID);
ALTER TABLE FLIGHT ADD CONSTRAINT FK_FLIGHT_productId FOREIGN KEY (productId) REFERENCES PRODUCT (PRODUCTID);
ALTER TABLE FLIGHT ADD CONSTRAINT FK_FLIGHT_city2 FOREIGN KEY (city2) REFERENCES CITY (CITYNAME);
ALTER TABLE FLIGHT ADD CONSTRAINT FK_FLIGHT_city1 FOREIGN KEY (city1) REFERENCES CITY (CITYNAME);
ALTER TABLE HOTEL ADD CONSTRAINT FK_HOTEL_productId FOREIGN KEY (productId) REFERENCES PRODUCT (PRODUCTID);
ALTER TABLE HOTEL ADD CONSTRAINT FK_HOTEL_City FOREIGN KEY (City) REFERENCES CITY (CITYNAME);
ALTER TABLE USERS_GROUPS ADD CONSTRAINT FK_USERS_GROUPS_username FOREIGN KEY (username) REFERENCES USERS (USERNAME);
ALTER TABLE PARTECIPATE ADD CONSTRAINT FK_PARTECIPATE_pers_package_id FOREIGN KEY (pers_package_id) REFERENCES PERS_PACKAGE (ID);
ALTER TABLE PARTECIPATE ADD CONSTRAINT FK_PARTECIPATE_username FOREIGN KEY (username) REFERENCES USERS (USERNAME);
ALTER TABLE INCLUDES ADD CONSTRAINT FK_INCLUDES_product_id FOREIGN KEY (product_id) REFERENCES PRODUCT (PRODUCTID);
ALTER TABLE INCLUDES ADD CONSTRAINT FK_INCLUDES_package_id FOREIGN KEY (package_id) REFERENCES DEFAULT_PACKAGE (ID);
ALTER TABLE INCLUDES_PERS ADD CONSTRAINT FK_INCLUDES_PERS_product_id FOREIGN KEY (product_id) REFERENCES PRODUCT (PRODUCTID);
ALTER TABLE INCLUDES_PERS ADD CONSTRAINT FK_INCLUDES_PERS_pers_package_id FOREIGN KEY (pers_package_id) REFERENCES PERS_PACKAGE (ID);

ALTER TABLE EXCURSION DROP FOREIGN KEY FK_EXCURSION_city;
ALTER TABLE EXCURSION DROP FOREIGN KEY FK_EXCURSION_productId;
ALTER TABLE FLIGHT DROP FOREIGN KEY FK_FLIGHT_productId;
ALTER TABLE FLIGHT DROP FOREIGN KEY FK_FLIGHT_city2;
ALTER TABLE FLIGHT DROP FOREIGN KEY FK_FLIGHT_city1;
ALTER TABLE HOTEL DROP FOREIGN KEY FK_HOTEL_productId;
ALTER TABLE HOTEL DROP FOREIGN KEY FK_HOTEL_City;
ALTER TABLE USERS_GROUPS DROP FOREIGN KEY FK_USERS_GROUPS_username;
ALTER TABLE PARTECIPATE DROP FOREIGN KEY FK_PARTECIPATE_pers_package_id;
ALTER TABLE PARTECIPATE DROP FOREIGN KEY FK_PARTECIPATE_username;
ALTER TABLE INCLUDES DROP FOREIGN KEY FK_INCLUDES_product_id;
ALTER TABLE INCLUDES DROP FOREIGN KEY FK_INCLUDES_package_id;
ALTER TABLE INCLUDES_PERS DROP FOREIGN KEY FK_INCLUDES_PERS_product_id;
ALTER TABLE INCLUDES_PERS DROP FOREIGN KEY FK_INCLUDES_PERS_pers_package_id;

/*
************************************************************************************************ Insert Users info
*/
INSERT INTO `TravelDreamDB`.`USERS`
(`USERNAME`,
`ADDRESS`,
`EMAIL`,
`NAME`,
`PASSWORD`,
`PHONENUMBER`,
`SURNAME`)
VALUES
('giacomo1',
'Binasco',
'brescia@mail.com',
'Giacomo',
'a03ea09072d789adff29aff6a3758e9294c96ce803915c1456384eaa6e2d2df9',
'3331924892',
'Bresciani'),

('giacomo2',
'Milano',
'giacomo4@mail.com',
'amministratore ',
'a03ea09072d789adff29aff6a3758e9294c96ce803915c1456384eaa6e2d2df9',
'3331924892',
'di tutto');



INSERT INTO `TravelDreamDB`.`USERS_GROUPS`
(`USERNAME`,
`GROUPNAME`)
VALUES
('giacomo1',
'USER'),
('giacomo2',
'ADMIN');

INSERT INTO `TravelDreamDB`.`CITY`
(`CITYNAME`)
VALUES
('Milano'), ('Roma'), ('Fuerteventura'), ('Madrid'), ('Berlino'), ('Gerusalemme'), ('Amsterdam'), ('Cape Town'), ('New York'), ('Mosca'), ('Ibiza'), ('Siracusa'), ('Pechino'); 

	
insert into PRODUCT (NAME,DESCRIPTION,PRICE) 
	values ('Hotel Betulla', 'Ampie camere con TV e aria condizionata,si affaccia sul colosseo ', 50),
('Rome Suite Hotel', 'Hotel 5 stelle, pieno di confort e sensibile alle richieste del cliente. Viene considerato uno degli hotel di maggior lusso della capitale ', 80),
('Domina Coral Bay', 'Resort situato nella zona piu frequentata della citta. Qui si trova ogni tipo di divertimento, vivendo a pieno sia il giorno(con attivita in spiaggia e nelle 7 piscine del resort), sia la notte, con lo Smaila Club e la discoteca Stargate ', 90),
('Hotel Stardust', 'Storico albergo di lusso 5 stelle, a pochi passi dal centro, con piu di 200 camere in stile ottocentesco, dotate di tutti i comfort. Ristorante di prima classe e centro benessere per tutti gli ospiti ', 130),
('Yehudad Hotel', 'Circondato da un grande complesso circondato da montagne verdi, ampi prati e piscina. Offre confortevoli camere grandi per tutti i gusti dal design suggestivo con viste mozzafiato. Personale molto cordiale e disponibile ' , 90),
('Amstel Break', 'Elegante B&B arredato in perfetto stile olandese, con una spaziosa zona relax per tutti gli ospiti. Situato nella zona del Van Gogh Museum e VondelPark ', 25),

('Otto Palace', 'Piccolo hotel accogliente nella nuova zona residenziale della citta ricca di ristoranti e caffe tipici, a due minuti dalla stazione della metropolitana di Dahlem-Dorf. Tv via cavo, wi-fi e colazione compresi nel prezzo ', 35),
('Kennedy', 'Nuovissimo albergo che sorge nel cuore di New York, a pochi passi da Time Warner Center e Columbus Circle. Camere Rilassati in una delle 600 camere con aria condizionata della struttura, complete di frigorifero, wi-fi gratuito e TV a schermo piatto ', 100),
('The Thompson', 'Hotel boutique situato nel quartiere SoHo di Manhattan e fornito di ristorante e bar. Situato a 800 metri dal quartiere di Little Italy. Offre camere moderne, un servizio in camera esclusivo e una sala per gustarsi cocktail e intrattenimento dal vivo ', 70),
('Julian Centre', 'Garantisce ai clienti una posizione centrale strategica a Roma, a due passi dai teatri e dai musei. Questo hotel 5 stelle si trova a pochi passi da piazza San Pietro', 50),
('BorisPlaza', 'Struttura situata vicino a Giardini botanici e Palazzo Ostankino.  Tutte Le 60 camere dispongono di tutti i comfort:TV (disponibile via cavo), frigobar, condizionatore ed un telefono fisso.Posteggio auto ampio e gratuito ', 60),
('Palacio de Raton', 'Ubicato in uno spettacolare palazzo del 18 secolo nel distretto di Las Letras. Tra le attrazioni locali nelle immediate vicinanze vi sono Plaza Mayor Plaza Santa Ana e musei tra cui il Prado il Reina Sofia ', 80),
('Don Pedro', 'Prezzi modici, dotato di tutti i comfort, vicino a Plaza Mayor ', 80),
('ZUZU Villa', 'Meravigliosa villa con giardino. Situato nella zona residenziale. La posizione e la migliore di Cape Town per la sicurezza, circondato da ristoranti tipici. Splendida posizione da cui si puo vedere anche la Table Mountain ', 99),
('WangFung Hotel', 'Le camere godono di vista sulla citta o sul giardino, aria condizionata e sono dotate di vasca idromassaggio privata e minibar. Oltre a un bar/lounge/sala da ballo, offre connessione wireless a Internet gratuita, e servizi di tintoria/lavaggio a secco ', 105),

('EZY5566', 'volo Easyjet da Milano a Roma', 35),
('EZY1020', 'volo Easyjet da Roma a Milano', 50),
('EZY6135', 'volo Easyjet da Roma a Fuerteventura', 35),
('EZY9857', 'volo Easyjet da Fuerteventura a Roma', 50),
('LKP3132', 'volo Ryanair da Milano a New York', 95),
('CCC1413', 'volo Ryanair da New York a Milano', 60),
('OIL1518', 'volo Air Europa da Roma a Fuerteventura', 80),
('GOU4412', 'volo Ryanair da Ibiza a Milano', 58),
('EKF7717', 'volo Ryanair da Milano a Roma', 75),
('EKP2217', 'volo Ryanair da Roma a Milano', 60),
('UUD3333', 'volo Virgin America da Milano a New York', 125),
('EKP4518', 'volo Virgin America da New York a Milano', 100),
('SXX1890', 'volo United Airlines da New York a Roma', 92),
('SSR2839', 'volo Germanwings da Roma a Berlino', 49),
('SLK8543', 'volo Germanwings da Berlino a Roma', 42),
('QCG9432', 'volo EasyJet da Milano a Berlino', 75),
('SST0909', 'volo Germanwings da Berlino a Milano', 39),
('PRO4721', 'volo Aeroflot da Mosca a Pechino', 78),
('FHJ9892', 'volo Air China da Pechino a Mosca', 55),
('ZXC0426', 'volo Iberia da Milano a Madrid', 34),
('BSX9991', 'volo Easyjet da Milano a Madrid', 50),
('ZSD8276', 'volo Easyjet da Madrid a Milano', 22),
('ZSD8131', 'volo Easyjet da Madrid a Milano', 34),
('MLK8060', 'volo Easyjet da Milano ad Amsterdam', 39),
('DWQ6212', 'volo Easyjet da Milano ad Amsterdam', 69),
('MDR5533', 'volo Easyjet da Amsterdam a Milano', 52),
('ART4350', 'volo Easyjet da Amsterdam a Milano', 72),
('GGP0120', 'volo Spanair da Roma a Madrid', 60),
('GFD7654', 'volo Easyjet da Madrid a Roma', 45),
('BBN3004', 'volo South African AirWays da Roma a Cape Town', 100),
('BPO2917', 'volo Luxair da Cape Town a Roma', 120),

('Il colosseo', 'Visita al colosseo e alle sue rovine', 15),
('Ras Mohamed', 'Viaggio in barca fino alla spiaggia di Ras Mohamed. Possibilita di noleggiare attrezzatura da snorkling', 30),
('Cena nel deserto', 'Cena nel deserto, con prodotti locali.', 20),
('Visita a Il Cairo', 'Visita alla capitale Il Cairo, con pranzo incluso nel delizioso ristorante "El Mukunda"', 40),
('Van Gogh Museum', 'Visita al museo piu affascinante e famoso di Amsterdam', 25),
('Prado', 'Visita  i grandi capolavori di Francisco Goya, El Greco, Mantegna, Botticelli, Caravaggio o Rembrandt', 15),
('Reina Sofia', 'Visita le grandi opere di Miro, Picasso, Dali, Magritte, Man Ray, Broodthaers, Yves Klein, Merz, Michaux', 15),
('Traviata alla Scala','Fatti sorprendere dalla Traviata del maestro Muti', 55),
('Inverdoon Zoo Safari', 'Una natura cosi non si e mai vista!', 30),
('Mount of Beatitudes', 'Esplora le meraviglie di New York!', 35),
('Heineken Browery', 'Visita il Museo-Fabbrica della famosa Birra Heineken, al termine del tuor e offerta la degustazione! ', 15),
('Casa di Anna Frank', 'Visita l alloggio segreto e il diario di Anna Frank ', 15),
('Rijksmuseum', 'Visita le grandi opere fiamminghe', 25),
('Stadio Santiago Bernabeu', 'Visita lo stadio piu famoso d Europa del Real Madrid, piu di 400 visitatori al giorno!', 25),
('ZOO PANDA', 'Visita il terzo Zoo piu grande di Europa, famoso soprattutto per i suoi cuccioli di panda ', 30);

/*
************************************************************************************************ Insert "Alberghi"
*/
insert into HOTEL (City, productid) 
(select 'Roma' as City, PRODUCTID as productid from Product 
where (NAME = 'Hotel Betulla'
	and DESCRIPTION='Ampie camere con TV e aria condizionata,si affaccia sul colosseo ' 
	and PRICE=50));

insert into HOTEL (City, productid) 
(select 'Roma' as City, PRODUCTID as productid from Product 
where (NAME = 'Rome Suite Hotel'
	and DESCRIPTION='Hotel 5 stelle, pieno di confort e sensibile alle richieste del cliente. Viene considerato uno degli hotel di maggior lusso della capitale ' 
	and PRICE=80));

insert into HOTEL (City, productid) 
(select 'Fuerteventura' as City, PRODUCTID as productid from Product 
where (NAME = 'Domina Coral Bay'
	and DESCRIPTION='Resort situato nella zona piu frequentata della citta. Qui si trova ogni tipo di divertimento, vivendo a pieno sia il giorno(con attivita in spiaggia e nelle 7 piscine del resort), sia la notte, con lo Smaila Club e la discoteca Stargate ' 
	and PRICE=90));

insert into HOTEL (City, productid) 
(select 'Amsterdam' as City, PRODUCTID as productid from Product 
where (NAME = 'Hotel Stardust'
	and DESCRIPTION='Storico albergo di lusso 5 stelle, a pochi passi dal centro, con piu di 200 camere in stile ottocentesco, dotate di tutti i comfort. Ristorante di prima classe e centro benessere per tutti gli ospiti '
	and PRICE=130));

insert into HOTEL (City, productid) 
(select 'New York' as City, PRODUCTID as productid from Product 
where (NAME = 'Yehudad Hotel'
	and DESCRIPTION='Circondato da un grande complesso circondato da montagne verdi, ampi prati e piscina. Offre confortevoli camere grandi per tutti i gusti dal design suggestivo con viste mozzafiato. Personale molto cordiale e disponibile '
	and PRICE=90));

insert into HOTEL (City, productid) 
(select 'Amsterdam' as City, PRODUCTID as productid from Product 
where (NAME = 'Amstel Break'
	and DESCRIPTION='Elegante B&B arredato in perfetto stile olandese, con una spaziosa zona relax per tutti gli ospiti. Situato nella zona del Van Gogh Museum e VondelPark ' 
	and PRICE=25));

insert into HOTEL (City, productid) 
(select 'Berlino' as City, PRODUCTID as productid from Product 
where (NAME = 'Otto Palace'
	and DESCRIPTION='Piccolo hotel accogliente nella nuova zona residenziale della citta ricca di ristoranti e caffe tipici, a due minuti dalla stazione della metropolitana di Dahlem-Dorf. Tv via cavo, wi-fi e colazione compresi nel prezzo ' 
	and PRICE=35));	

insert into HOTEL (City, productid) 
(select 'New York' as City, PRODUCTID as productid from Product 
where (NAME = 'Kennedy'
	and DESCRIPTION='Nuovissimo albergo che sorge nel cuore di New York, a pochi passi da Time Warner Center e Columbus Circle. Camere Rilassati in una delle 600 camere con aria condizionata della struttura, complete di frigorifero, wi-fi gratuito e TV a schermo piatto '
	and PRICE=100));	

insert into HOTEL (City, productid) 
(select 'New York' as City, PRODUCTID as productid from Product 
where (NAME = 'The Thompson'
	and DESCRIPTION='Hotel boutique situato nel quartiere SoHo di Manhattan e fornito di ristorante e bar. Situato a 800 metri dal quartiere di Little Italy. Offre camere moderne, un servizio in camera esclusivo e una sala per gustarsi cocktail e intrattenimento dal vivo '
	and PRICE=70));	

insert into HOTEL (City, productid) 
(select 'Roma' as City, PRODUCTID as productid from Product 
where (NAME = 'Julian Centre'
	and DESCRIPTION='Garantisce ai clienti una posizione centrale strategica a Roma, a due passi dai teatri e dai musei. Questo hotel 5 stelle si trova a pochi passi da piazza San Pietro' 
	and PRICE=50));

insert into HOTEL (City, productid) 
(select 'Fuerteventura' as City, PRODUCTID as productid from Product 
where (NAME = 'BorisPlaza'
	and DESCRIPTION='Struttura situata vicino a Giardini botanici e Palazzo Ostankino.  Tutte Le 60 camere dispongono di tutti i comfort:TV (disponibile via cavo), frigobar, condizionatore ed un telefono fisso.Posteggio auto ampio e gratuito ' 
	and PRICE=60));	

insert into HOTEL (City, productid) 
(select 'Madrid' as City, PRODUCTID as productid from Product 
where (NAME = 'Palacio de Raton'
	and DESCRIPTION='Ubicato in uno spettacolare palazzo del 18 secolo nel distretto di Las Letras. Tra le attrazioni locali nelle immediate vicinanze vi sono Plaza Mayor Plaza Santa Ana e musei tra cui il Prado il Reina Sofia ' 
	and PRICE=80));

insert into HOTEL (City, productid) 
(select 'Madrid' as City, PRODUCTID as productid from Product 
where (NAME = 'Don Pedro'
	and DESCRIPTION='Prezzi modici, dotato di tutti i comfort, vicino a Plaza Mayor ' 
	and PRICE=80));
	
insert into HOTEL (City, productid) 
(select 'Cape Town' as City, PRODUCTID as productid from Product 
where (NAME = 'ZUZU Villa'
	and DESCRIPTION='Meravigliosa villa con giardino. Situato nella zona residenziale. La posizione e la migliore di Cape Town per la sicurezza, circondato da ristoranti tipici. Splendida posizione da cui si puo vedere anche la Table Mountain ' 
	and PRICE=99));

insert into HOTEL (City, productid) 
(select 'Amsterdam' as City, PRODUCTID as productid from Product 
where (NAME = 'WangFung Hotel'
	and DESCRIPTION='Le camere godono di vista sulla citta o sul giardino, aria condizionata e sono dotate di vasca idromassaggio privata e minibar. Oltre a un bar/lounge/sala da ballo, offre connessione wireless a Internet gratuita, e servizi di tintoria/lavaggio a secco ' 
	and PRICE=105));
/*
************************************************************************************************ Insert "Voli"
*/
insert into Flight (DATE, city1, city2, productId) 
(select '2014-02-25 09:00:00' as DATE, 'Milano' as city1, 'Roma' as city2, PRODUCTID as productid from Product 
where (NAME = 'EZY5566'
	and DESCRIPTION='volo Easyjet da Milano a Roma' 
	and PRICE=35));

insert into Flight (DATE, city1, city2, productId) 
(select '2014-03-05 09:00:00' as DATE, 'Roma' as city1, 'Milano' as city2, PRODUCTID as productid from Product 
where (NAME = 'EZY1020'
	and DESCRIPTION='volo Easyjet da Roma a Milano' 
	and PRICE=50));	
	
insert into Flight (DATE, city1, city2, productId) 
(select '2014-03-15 09:00:00' as DATE, 'Roma' as city1, 'Fuerteventura' as city2, PRODUCTID as productid from Product 
where (NAME = 'EZY6135'
	and DESCRIPTION='volo Easyjet da Roma a Fuerteventura' 
	and PRICE=35));

insert into Flight (DATE, city1, city2, productId) 
(select '2014-03-26 16:00:00' as DATE, 'Fuerteventura' as city1, 'Roma' as city2, PRODUCTID as productid from Product 
where (NAME = 'EZY9857'
	and DESCRIPTION='volo Easyjet da Fuerteventura a Roma' 
	and PRICE=50));

insert into Flight (DATE, city1, city2, productId) 
(select '2014-08-01 23:00:00' as DATE, 'Milano' as city1, 'New York' as city2, PRODUCTID as productid from Product 
where (NAME = 'LKP3132'
	and DESCRIPTION='volo Ryanair da Milano a New York' 
	and PRICE=95));

insert into Flight (DATE, city1, city2, productId) 
(select '2014-07-05 10:45:00' as DATE, 'New York' as city1, 'Milano' as city2, PRODUCTID as productid from Product 
where (NAME = 'CCC1413'
	and DESCRIPTION='volo Ryanair da New York a Milano' 
	and PRICE=60));

insert into Flight (DATE, city1, city2, productId) 
(select '2014-03-20 21:00:00' as DATE, 'Roma' as city1, 'Fuerteventura' as city2, PRODUCTID as productid from Product 
where (NAME = 'OIL1518'
	and DESCRIPTION='volo Air Europa da Roma a Fuerteventura' 
	and PRICE=80));
	
insert into Flight (DATE, city1, city2, productId) 
(select '2014-05-29 10:00:00' as DATE, 'Ibiza' as city1, 'Milano' as city2, PRODUCTID as productid from Product 
where (NAME = 'GOU4412'
	and DESCRIPTION='volo Ryanair da Ibiza a Milano' 
	and PRICE=58));
	
insert into Flight (DATE, city1, city2, productId) 
(select '2014-02-27 07:00:00' as DATE, 'Milano' as city1, 'Roma' as city2, PRODUCTID as productid from Product 
where (NAME = 'EKF7717'
	and DESCRIPTION='volo Ryanair da Milano a Roma' 
	and PRICE=75));
	
insert into Flight (DATE, city1, city2, productId) 
(select '2014-03-15 12:00:00' as DATE, 'Roma' as city1, 'Milano' as city2, PRODUCTID as productid from Product 
where (NAME = 'EKP2217'
	and DESCRIPTION='volo Ryanair da Roma a Milano' 
	and PRICE=60));

insert into Flight (DATE, city1, city2, productId) 
(select '2014-06-30 07:00:00' as DATE, 'Milano' as city1, 'New York' as city2, PRODUCTID as productid from Product 
where (NAME = 'UUD3333'
	and DESCRIPTION='volo Virgin America da Milano a New York' 
	and PRICE=125));

insert into Flight (DATE, city1, city2, productId) 
(select '2014-07-23 09:15:00' as DATE, 'New York' as city1, 'Milano' as city2, PRODUCTID as productid from Product 
where (NAME = 'EKP4518'
	and DESCRIPTION='volo Virgin America da New York a Milano' 
	and PRICE=100));
	
insert into Flight (DATE, city1, city2, productId) 
(select '2014-08-10 08:00:00' as DATE, 'New York' as city1, 'Milano' as city2, PRODUCTID as productid from Product 
where (NAME = 'SXX1890'
	and DESCRIPTION='volo United Airlines da New York a Roma' 
	and PRICE=92));

insert into Flight (DATE, city1, city2, productId) 
(select '2014-03-15 08:30:00' as DATE, 'Roma' as city1, 'Berlino' as city2, PRODUCTID as productid from Product 
where (NAME = 'SSR2839'
	and DESCRIPTION='volo Germanwings da Roma a Berlino' 
	and PRICE=49));

insert into Flight (DATE, city1, city2, productId) 
(select '2014-04-08 16:30:00' as DATE, 'Berlino' as city1, 'Roma' as city2, PRODUCTID as productid from Product 
where (NAME = 'SLK8543'
	and DESCRIPTION='volo Germanwings da Berlino a Roma' 
	and PRICE=42));

insert into Flight (DATE, city1, city2, productId) 
(select '2014-02-27 23:30:00' as DATE, 'Milano' as city1, 'Berlino' as city2, PRODUCTID as productid from Product 
where (NAME = 'QCG9432'
	and DESCRIPTION='volo Easyjet da Milano a Berlino' 
	and PRICE=75));

insert into Flight (DATE, city1, city2, productId) 
(select '2014-04-01 11:00:00' as DATE, 'Berlino' as city1, 'Milano' as city2, PRODUCTID as productid from Product 
where (NAME = 'SST0909'
	and DESCRIPTION='volo Germanwings da Berlino a Milano' 
	and PRICE=39));	
	
insert into Flight (DATE, city1, city2, productId) 
(select '2014-01-27 03:30:00' as DATE, 'Mosca' as city1, 'Pechino' as city2, PRODUCTID as productid from Product 
where (NAME = 'PRO4721'
	and DESCRIPTION='volo Aeroflot da Mosca a Pechino' 
	and PRICE=78));

insert into Flight (DATE, city1, city2, productId) 
(select '2014-07-21 11:00:00' as DATE, 'Pechino' as city1, 'Mosca' as city2, PRODUCTID as productid from Product 
where (NAME = 'FHJ9892'
	and DESCRIPTION='volo Air China da Pechino a Mosca' 
	and PRICE=55));

insert into Flight (DATE, city1, city2, productId) 
(select '2014-07-15 06:30:00' as DATE, 'Milano' as city1, 'Madrid' as city2, PRODUCTID as productid from Product 
where (NAME = 'ZXC0426'
	and DESCRIPTION='volo Iberia da Milano a Madrid'
	and PRICE=34));

insert into Flight (DATE, city1, city2, productId) 
(select '2014-06-15 09:30:00' as DATE, 'Milano' as city1, 'Madrid' as city2, PRODUCTID as productid from Product 
where (NAME = 'BSX9991'
	and DESCRIPTION='volo Easyjet da Milano a Madrid'
	and PRICE=50));
	
insert into Flight (DATE, city1, city2, productId) 
(select '2014-06-25 20:00:00' as DATE, 'Madrid' as city1, 'Milano' as city2, PRODUCTID as productid from Product 
where (NAME = 'ZSD8276'
	and DESCRIPTION='volo Easyjet da Madrid a Milano' 
	and PRICE=22));

insert into Flight (DATE, city1, city2, productId) 
(select '2014-08-01 20:00:00' as DATE, 'Madrid' as city1, 'Milano' as city2, PRODUCTID as productid from Product 
where (NAME = 'ZSD8131'
	and DESCRIPTION='volo Easyjet da Madrid a Milano' 
	and PRICE=34));

insert into Flight (DATE, city1, city2, productId) 
(select '2014-03-18 09:30:00' as DATE, 'Milano' as city1, 'Amsterdam' as city2, PRODUCTID as productid from Product 
where (NAME = 'MLK8060'
	and DESCRIPTION='volo Easyjet da Milano ad Amsterdam'
	and PRICE=39));

insert into Flight (DATE, city1, city2, productId) 
(select '2014-03-25 10:00:00' as DATE, 'Milano' as city1, 'Amsterdam' as city2, PRODUCTID as productid from Product 
where (NAME = 'DWQ6212'
	and DESCRIPTION='volo Easyjet da Milano ad Amsterdam'
	and PRICE=69));

insert into Flight (DATE, city1, city2, productId) 
(select '2014-04-21 10:30:00' as DATE, 'Amsterdam' as city1, 'Milano' as city2, PRODUCTID as productid from Product 
where (NAME = 'MDR5533'
	and DESCRIPTION='volo Easyjet da Amsterdam a Milano' 
	and PRICE=52));

insert into Flight (DATE, city1, city2, productId) 
(select '2014-04-02 19:30:00' as DATE, 'Amsterdam' as city1, 'Milano' as city2, PRODUCTID as productid from Product 
where (NAME = 'ART4350'
	and DESCRIPTION='volo Easyjet da Amsterdam a Milano' 
	and PRICE=72));

insert into Flight (DATE, city1, city2, productId) 
(select '2014-02-01 15:45:00' as DATE, 'Roma' as city1, 'Madrid' as city2, PRODUCTID as productid from Product 
where (NAME = 'GGP0120'
	and DESCRIPTION='volo Spanair da Roma a Madrid' 
	and PRICE=60));

insert into Flight (DATE, city1, city2, productId) 
(select '2014-02-11 17:00:00' as DATE, 'Madrid' as city1, 'Roma' as city2, PRODUCTID as productid from Product 
where (NAME = 'GFD7654'
	and DESCRIPTION='volo Easyjet da Madrid a Roma' 
	and PRICE=45));
	
insert into Flight (DATE, city1, city2, productId) 
(select '2014-08-01 15:45:00' as DATE, 'Roma' as city1, 'Cape Town' as city2, PRODUCTID as productid from Product 
where (NAME = 'BBN3004'
	and DESCRIPTION='volo South African AirWays da Roma a Cape Town' 
	and PRICE=100));

insert into Flight (DATE, city1, city2, productId) 
(select '2014-08-24 19:30:00' as DATE, 'Cape Town' as city1, 'Roma' as city2, PRODUCTID as productid from Product 
where (NAME = 'BPO2917'
	and DESCRIPTION='volo Luxair da Cape Town a Roma' 
	and PRICE=120));
/*
************************************************************************************************ Insert "Escursioni"
*/
insert into excursion (DATE, city, productId) 
(select '2014-03-19 19:00:00' as DATE, 'Fuerteventura' as city, PRODUCTID as productid from Product 
where (NAME = 'Cena nel deserto'
	and DESCRIPTION='Cena nel deserto, con prodotti locali.' 
	and PRICE=20));

insert into excursion (DATE, city, productId) 
(select '2014-03-29 10:00:00' as DATE, 'Fuerteventura' as city, PRODUCTID as productid from Product 
where (NAME = 'Visita a Il Cairo'
	and DESCRIPTION='Visita alla capitale Il Cairo, con pranzo incluso nel delizioso ristorante "El Mukunda"' 
	and PRICE=40));

insert into excursion (DATE, city, productId) 
(select '2014-03-08 19:00:00' as DATE, 'Roma' as city, PRODUCTID as productid from Product 
where (NAME = 'Il colosseo'
	and DESCRIPTION='Visita al colosseo e alle sue rovine'
	and PRICE=15));	

insert into excursion (DATE, city, productId) 
(select '2014-03-22 10:00:00' as DATE, 'Fuerteventura' as city, PRODUCTID as productid from Product 
where (NAME = 'Ras Mohamed'
	and DESCRIPTION='Viaggio in barca fino alla spiaggia di Ras Mohamed. Possibilita di noleggiare attrezzatura da snorkling' 
	and PRICE=30));

insert into excursion (DATE, city, productId) 
(select '2014-03-28 10:45:00' as DATE, 'Amsterdam' as city, PRODUCTID as productid from Product 
where (NAME = 'Van Gogh Museum'
	and DESCRIPTION='Visita al museo piu affascinante e famoso di Amsterdam'
	and PRICE=25));

insert into excursion (DATE, city, productId) 
(select '2014-02-05 10:00:00' as DATE, 'Madrid' as city, PRODUCTID as productid from Product 
where (NAME = 'Prado'
	and DESCRIPTION='Visita  i grandi capolavori di Francisco Goya, El Greco, Mantegna, Botticelli, Caravaggio o Rembrandt'
	and PRICE=15));

	insert into excursion (DATE, city, productId) 
(select '2014-02-07 12:30:00' as DATE, 'Madrid' as city, PRODUCTID as productid from Product 
where (NAME = 'Reina Sofia'
	and DESCRIPTION='Visita le grandi opere di Miro, Picasso, Dali, Magritte, Man Ray, Broodthaers, Yves Klein, Merz, Michaux'
	and PRICE=15));	

insert into excursion (DATE, city, productId) 
(select '2014-03-01 22:00:00' as DATE, 'Roma' as city, PRODUCTID as productid from Product 
where (NAME = 'Traviata alla Scala'
	and DESCRIPTION='Fatti sorprendere dalla Traviata del maestro Muti'
	and PRICE=55));


insert into excursion (DATE, city, productId) 
(select '2014-08-05 10:30:00' as DATE, 'New York' as city, PRODUCTID as productid from Product 
where (NAME = 'Central Park'
	and DESCRIPTION='Visita con guida al meraviglioso parco NewYorkese'
	and PRICE=15));

insert into excursion (DATE, city, productId) 
(select '2014-07-06 10:30:00' as DATE, 'New York' as city, PRODUCTID as productid from Product 
where (NAME = 'Mount of Beatitudes'
	and DESCRIPTION='Esplora le meraviglie di New York!'
	and PRICE=35));


insert into excursion (DATE, city, productId) 
(select '2014-03-27 14:00:00' as DATE, 'Amsterdam' as city, PRODUCTID as productid from Product 
where (NAME = 'Heineken Browery'
	and DESCRIPTION='Visita il Museo-Fabbrica della famosa Birra Heineken, al termine del tuor e offerta la degustazione! '
	and PRICE=15));
	

insert into excursion (DATE, city, productId) 
(select '2014-03-30 11:00:00' as DATE, 'Amsterdam' as city, PRODUCTID as productid from Product 
where (NAME = 'Casa di Anna Frank'
	and DESCRIPTION='Visita l alloggio segreto e il diario di Anna Frank '
	and PRICE=15));	
	
insert into excursion (DATE, city, productId) 
(select '2014-04-01 09:30:00' as DATE, 'Amsterdam' as city, PRODUCTID as productid from Product 
where (NAME = 'Rijksmuseum'
	and DESCRIPTION='Visita le grandi opere fiamminghe '
	and PRICE=25));	
	
insert into excursion (DATE, city, productId) 
(select '2014-04-01 09:30:00' as DATE, 'Madrid' as city, PRODUCTID as productid from Product 
where (NAME = 'Stadio Santiago Bernabeu'
	and DESCRIPTION='Visita lo stadio piu famoso d Europa del Real Madrid, piu di 400 visitatori al giorno!'
	and PRICE=25));	

insert into excursion (DATE, city, productId) 
(select '2014-04-01 09:30:00' as DATE, 'Madrid' as city, PRODUCTID as productid from Product 
where (NAME = 'ZOO PANDA'
	and DESCRIPTION='Visita il terzo Zoo piu grande di Europa, famoso soprattutto per i suoi cuccioli di panda '
	and PRICE=30));	
/*
************************************************************************************************ Predefinite packages
*/
INSERT INTO DEFAULT_PACKAGE(DESCRIPTION,ENDDATE,IMAGEID,NAME,STARTDATE)
values ('Vola alla scoperta di Madrid!','2014-06-25',4,'Madrid','2014-06-15'), 
('La piu africana delle isole Canarie, dove la sabbia finissima congiunge la terra e il mare cristallino con i suoi fondali spettacolari. Un piccolo Sahara circondato da acque trasparenti e continuamente accarezzato dal vento. Una condizione ideale per tutti gli amanti del windsurf. Fuerteventura e un isola accogliente, ricca di profumi e fragranze, distesa su variopinti spazi sconfinati che aspettano  solo di essere conquistati, dagli occhi e dal cuore.','2014-03-26',1,'FuerteVentura','2014-03-15'), 
('Tra le capitali europee Madrid non e ne la più antica ne la più elegante. Forse pero e la più vitale e divertente. La citta, a tratti monumentale, puo vantare musei straordinari come il Prado e meravigliosi parchi. Ma la vita di strada, l euforia che si protrae fino alle ore piccole, le tascas e i bar de tapas gremiti di gente ricordano a tutti che Madrid è una città da vivere.','2014-04-21',7,'Amsterdam','2014-03-25'), 
('E impossibile restare indifferenti al fascino esercitato da New York. Il mix di etnie e culture dei suoi abitanti, il ritmo frenetico delle attivita e del traffico delirante assieme alla tranquillita di Central Park, la vertigine dei grattacieli, la movimentata vita notturna si combinano in maniera unica rendendo una vacanza in questa metropoli un esperienza assolutamente unica e indimenticabile.','2014-07-23',9,'New York','2014-06-30');
	
INSERT INTO includes(product_id,package_id)
 values (13,1), (36,1), (37,1), 
 (3,2), (18,2), (19,2), (48,2), 
 (4,3), (40,3), (41,3), (58,3), 
 (59,3), (8,4), (26,4), (27,4);
   

 


