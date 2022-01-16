# FinalProject

Logistics System

Prerequisites

Creati un microserviciu REST care sa simuleze functionarea unui sistem de gestionare a livrarilor de colete catre diverse destinatii.

La pornirea aplicatiei, se vor crea automat tabelele DESTINATIONS, ORDERS si vor fi populate cu date din fisierele destinations.csv, orders.csv. (H2 database)

Se va folosi un executor cu maximum 4 threaduri active si un queue size de 100 de taskuri pentru a gestiona livrarile.

Endpoint-uri suportate

POST /shipping/new-day
BODY: empty body
Avanseaza data curenta a aplicatiei cu o zi. La pornirea aplicatiei, data curenta a aplicatiei va fi 15-12-2021. (log + console: “New day starting : 15-12-2021”)
La inceputul fiecarei zile, toate livrarile din ziua respectiva vor fi grupate pe baza destinatiei si vor fi marcate ca fiind “In curs de livrare”. (log + console: “Today we will be delivering to Ploiesti, Pitesti, Craiova”)
Pentru fiecare destinatie distincta se va submite cate un task catre executor care va face livrarile la destinatia respectiva (log + console: “Starting deliveries for Ploiesti on Thread 0 for 25 km”). (hint: folositi @Async)
Dupa un numar de secunde egal cu numarul de km pana la destinatie, threadul de livrare va marca livrarile ca fiind finalizate si va actualiza profitul firmei. (1 leu/km pentru fiecare comanda care s-a livrat) (log + console: “4 deliveries completed for Ploiesti”)

POST /orders/add
BODY: lista de ordere ce trebuie adaugate
Adauga o lista de noi livrari in baza de date. Data de livrare pentru noile livrari trebuie sa fie strict mai mare decat data curenta a aplicatiei (ex. Minim 16-12-2021)

POST /orders/cancel
BODY: lista de id-uri a livrarilor ce trebuie anulate
Marcheaza o lista de livrari ca fiind anulata. O livrare poate fi anulata in orice moment, chiar si cand este in curs de livrare. O livrare anulata nu va aduce profit. O livrare finalizata nu mai poate fi anulata.

GET /orders/status?date=15-12-2021&destination=Ploiesti
Returneaza lista de livrari din ziua si pentru destinatia date ca si parametru. 
Daca parametrul date nu este furnizat, se vor intoarce date despre livrarile din ziua curenta a aplicatiei.
Daca parametrul destination nu este furnizat, se vor intoarce date despre livrarile catre toate destinatiile.

GET /actuator/info
Folosind spring boot actuator, se vor da informatii despre data curenta si profitul companiei.
{
	current-date : 15-12-2021,
	overall-profit : 258
}

POST /destinations/add

PUT /destinations/update

GET /destinations

GET /destinations/{destinationId}

DELETE /destinations/{destinationId}

Tabele:
DESTINATIONS
ID (PK)
NAME (UNIQUE)
DISTANCE

ORDERS
ID (PK)
DESTINATION_ID (FK)
DELIVERY_DATE
STATUS
LAST_UPDATED
Additional info

Toate logurile vor contine timestamp la secunda.

Order status enum: NEW, DELIVERING, DELIVERED, CANCELED

destinations.csv
Ploiesti,10
Pitesti,20
Cluj,30
Oradea,35
Satu Mare,40
Giurgiu,12
Craiova,18
Iasi,27
Bacau,16
Constanta,23

orders.csv
Ploiesti,15-12-2021
Ploiesti,15-12-2021
Pitesti,15-12-2021
Pitesti,15-12-2021
Pitesti,15-12-2021
Cluj,15-12-2021
Oradea,15-12-2021
Oradea,15-12-2021
Satu Mare,15-12-2021
Satu Mare,15-12-2021
Satu Mare,15-12-2021
Giurgiu,15-12-2021
Craiova,15-12-2021
Iasi,15-12-2021
Iasi,15-12-2021
Bacau,15-12-2021
Bacau,15-12-2021
Bacau,15-12-2021
Bacau,15-12-2021
Constanta,15-12-2021
Constanta,15-12-2021
Ploiesti,16-12-2021
Ploiesti,16-12-2021
Pitesti,16-12-2021
Pitesti,16-12-2021
Cluj,16-12-2021
Oradea,16-12-2021
Oradea,16-12-2021
Satu Mare,16-12-2021
Satu Mare,16-12-2021
Giurgiu,16-12-2021
Iasi,16-12-2021
Bacau,16-12-2021
Bacau,16-12-2021
Constanta,16-12-2021
Constanta,16-12-2021
Constanta,16-12-2021
