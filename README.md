# Home budgeting assistant
This is an application for handling the home budget. Each budget (named "register") has its own name and balance, which can be changed over time, either by charging it or by transfer to other budget. It persists the data across restarts and starts with pre-defined values.

# How to launch
- Build the application (if it was not done previously) with `./gradlew clean build`
- Go to `build/libs` directory
- Run `java -jar BudgetingAssistant-0.0.1-SNAPSHOT.jar` and wait until the message `Started BudgetingAssistantApplication in XXX seconds` appears in the terminal

# How to use
There are three endpoints available to use:

## Get all registers 

This method is used to retrieve all available registers with their names and current state.

`GET http://localhost:8080/registers`
 
Example respone

```
[
  {
    "uuid": "b6f76c59-7c16-46a7-8393-9cec0ddee370",
    "name": "Wallet",
    "balance": 65.0
  }
]
```

## Recharge existing register with given amount
This method is used to recharge existing register (given in the URL) with given amount of money (given in the request body). It returns the changed value of source register.

`POST http://localhost:8080/registers/{uuid}/recharge`

Example request:

```
{
	"amount": "115.0"
}
```

Example response:

```
{
  "uuid": "b6f76c59-7c16-46a7-8393-9cec0ddee370",
  "name": "Wallet",
  "balance": 180.0
}
```

## Transfer money beteween registers
This method is used to transfer money between registers. You have to point out, what is the source register (given in the URL) and target register (given in the request body). It returns the changed value of source register.

`POST http://localhost:8080/registers/{uuid}/transfer`

Example request:

```
{
	"targetRegisterUUID": "69290cc2-0806-464a-ab46-918b6753ed5f",
	"amount": "115.0"
}
```

Example response: 

```
{
  "uuid": "b6f76c59-7c16-46a7-8393-9cec0ddee370",
  "name": "Wallet",
  "balance": 65.0
}
```

## Reset the application to base state
Deleting "data" directory will force the application to regenerate the default database (both schema and date) upon next startup.