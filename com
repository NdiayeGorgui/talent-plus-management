

netstat -ano | findstr :5432
tasklist | findstr 22724
tasklist | findstr 8764
Cela va te montrer les noms des processus (ex: postgres.exe).
taskkill /PID 8764 /F




docker exec -it postgres-db psql -U user -d talent_db
CREATE DATABASE candidat_prod_db;
CREATE DATABASE offre_prod_db;
CREATE DATABASE recrutement_prod_db;
CREATE DATABASE recruteur_prod_db;
CREATE DATABASE utilisateur_prod_db;
CREATE DATABASE notification_talent_prod_db;
\l