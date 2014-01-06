CREATE TABLE IF NOT EXIST ChicagoCTA (
stop_id INT  NOT NULL AUTO_INCREMENT PRIMARY KEY
on_street VARCHAR(50) 
cross_street VARCHAR(50) 
routes INT 
boardings INT 
alightings INT 
month_beginning DATE 
daytype VARCHAR(50) 
location VARCHAR(50) 
) Engine=InnoDB;
