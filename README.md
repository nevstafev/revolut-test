## Revolut test task

This is a simple service for transfer money.

#### How it works:

For creating accounts it uses simple rest api.

After create transfer create it will not transfer money instantly.
 Instead of it task for money transfer will be submitted and return to user transaction
  connected with this task. 

## Running

Use `gradlew build` for build jar that you can find in `lib` folder.

For running use `java -jar revolut-test-1.0-SNAPSHOT.jar`

### Rest Api

Base URL `http://localhost:8080`

Use `Content-Type application/json` header

##### Accounts

###### For create account

` POST /accounts` with body 

```{"initialAmount": 10000}```
 
 returns account in json format

```
{
  "id":"1",
  "balance":10000 
}
```

###### Get account by id

`GET /accounts/{id}`

 returns account in json format

```
{
  "id":"1",
  "balance":10000 
}
```

###### Get all accounts

`GET /accounts`

 returns accounts in json format

```
[
  {
    "id":"1",
    "balance":10000
  },
  {
    "id":"2",
    "balance":20000
  } 
]
```

#### Transfer

###### Create transfer 

`PUT /transfer` with body:
```
{
  "sourceAccountId":"1",
  "destintionAccountId":"2",
  "amount": 10000
}
```

returns transaction object
```
{
  "id": "1",
  "sourceId": "1",
  "destinationId": "2",
  "amount": 1000,
  "status": "finished"
}
```

There are four possible statuses:

`submitted` - after transaction has created

`running` - when transaction processing has started

`finished` - after transaction processing has finished

`failed` - if any problem while process transaction
