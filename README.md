# Requirements:
- Java Development Kit 8+
- Maven 3+

# How to build:
```sh
$ git clone https://github.com/artemmensk/money-transfers-api
$ cd money-transfers-api/
$ mvn install 
```

# How to run:
```sh
$ java -jar target/money-transfers-api-1.0-SNAPSHOT.jar
```

# Specification:

### Create account:
```sh
POST http//localhost:4567/account
```
Expected result:
 - **201** Created
 - Header _Location_ was set

### Deposit money:
```sh
PUT http//localhost:4567/account/1
body: {"amount": 500}
```
Expected result:
 - **204** No Content
 - Header _Location_ was set

### View account:
```sh
GET http//localhost:4567/account/1
```
Expected result:
 - **200** OK
 - Body contains JSON

### Perform wire transfer:
```sh
POST http//localhost:4567/transfer
body: {"amount": 200, "sourceId":1, "destinationId":2}
```
Expected result:
 - **201** Created
 - Header _Location_ was set

### View transfer:
```sh
GET http//localhost:4567/transfer/9eef47a5-f590-428a-9b55-c258f521e60a
```
Expected result:
 - **200** OK
 - Body contains JSON

### View transfers for specific account id:
```sh
GET http//localhost:4567/account/1/transfer
```
Expected result:
 - **200** OK
 - Body contains JSON

### View all transfers:
```sh
GET http//localhost:4567/transfer
```
Expected result:
 - **200** OK
 - Body contains JSON

### Possible unexpected results:
 - **404** Account / Transfer not found
 - **409** Not enough balance
 - **400** Negative deposit / The same account