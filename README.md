# Navita
Navita admission test for Java Backend Developer - Build Parking management System

## :computer: Technologies used
<ul>
  <li>Java 11</li>
  <li>Spring Boot, Spring MVC, Spring DATA, Spring Security
  <li>Spring Cloud Gateway, Spring Cloud Eureka</li>
  <li>Docker</li>
  <li>PostgresSQL</li>
  <li>Microservices</li>
</ul>

## How to use
It's need a windows computer and docker installed to build the database containers, i don't test on other OS.

Clone the project to your computer and on containers execute de file db-deploy.bat to build the containers. Microservices need to be compiled or execute from a IDE to run, to compile just run Maven Build on your IDE.

Download compile project here: <a href="https://drive.google.com/drive/folders/1tdpALzb6wPv-Z16MysNUDrN7fcbxM8nt?usp=sharing"> Navita-Project</a>

####Execute microservices order:
<ol>
  <li>Discovery-Service - will run on localhost:8761</li>
  <li>Gateway-Service - will run on localhost:9090</li>
  <li>Auth-Service - will run on localhost:9010</li>
  <li>Parking-Service - will run on localhost:9040</li>
</ol>

#####All requests must go through Gateway the root url is localhost:9090

When all services is up you can open on your browser localhost:8761 to see a page of service discovery and all the services registry.

There is a master user to authenticate and test endpoints:
userName: admin@navita.com.br, password: admin

The only unprotect endpoint is localhost:9090/auth/authenticate to get the token, all other endpoints need to send header Authorization with Bearer token, to receive the response in XML user the header Accept: Application/xml.

All payload must be on json format.

### :link: endpoints
authentication: 
method: post
endpoint: localhost:9090/auth/authenticate
payload: {
	"userName": "admin@navita.com.br",
	"password": "admin"
}

#### user operations - must send Authorization header with token

create user: 
method: post
endpoint: localhost:9090/users
payload: {
	"name": "Paulo Monteiro",
  "email": "paulo.fff.monteiro@gmail.com",
  "cpf": "3226655481",
  "password": "senha@123"
}

get all users: 
method: get
endpoint: localhost:9090/users

get user info by id: 
method: get
endpoint: localhost:9090/users/{id}

get user info by email: 
method: get
endpoint: localhost:9090/users/user/{email}

delete user info by id: 
method: delete
endpoint: localhost:9090/users/{id}

delete user by email: 
method: delete
endpoint: localhost:9090/users/user/{email}

#### Parking Lot operations - must send Authorization header with token
create parking lot: 
method: post
endpoint: localhost:9090/parking/parking-lot
payload: {
	"name": "Navita Parking3",
	"cnpj": "314564523123",
	"address": "Rua teste 123",
	"phone": "111111111",
	"spacesMoto": 2,
	"spaceCars": 4
}

update parking lot:
method: put
endpoint: localhost:9090/parking/parking-lot/{id}
payload: {
	"name": "Navita Parking",
	"cnpj": "14564523123",
	"address": "Rua teste 123",
	"phone": "111111111",
	"spacesMoto": 4,
	"spaceCars": 4
}

get all parking lot: 
method: get
endpoint: localhost:9090/parking/parking-lot

get parking lot info by id: 
method: get
endpoint: localhost:9090/parking/parking-lot/{id}

get parking lot info by cnpj: 
method: get
endpoint: localhost:9090/parking/parking-lot/cnpj/{cnpj}

delete parking lot info by id: 
method: delete
endpoint: localhost:9090/parking/parking-lot/{id}

#### Vehicle operations - must send Authorization header with token
create vehicle: 
type 1 --> moto | 2 --> car
method: post
endpoint: localhost:9090/parking/vehicles
payload: {
	"brand": "Chevrolet",
  "model": "Onix",
  "color": "Preta",
  "plate": "122220103",
  "type":  2
}

update vehicle:
method: put
endpoint: localhost:9090/parking/vehicles/{id}
payload: {
	"brand": "Chevrolet",
  "model": "Onix",
  "color": "Preta",
  "plate": "122220103",
  "type":  2
}

get all vehicle: 
method: get
endpoint: localhost:9090/parking/vehicles

get vehicle info by id: 
method: get
endpoint: localhost:9090/parking/vehicles/{id}

get vehicle info by palte: 
method: get
endpoint: localhost:9090/parking/vehicle/plate/{plate}

delete vehicle info by id: 
method: delete
endpoint: localhost:9090/parking/vehicle/{id}

#### Parking operations - must send Authorization header with token
Parking / Unparking car
type 0 --> unpark | 1 --> park
method: post
endpoint: localhost:9090/manager/park
payload: {
	"idParkingLot": 2,
	"idVehicle": 16,
	"idOperation": 1
}

To do:
####Swagger documentation
####Report Service
