# deutsche bank trade service
#Problem Statement
#There is a scenario where thousands of trades are flowing into one store, assume any way of transmission of trades. 
#We need to create a one trade store, which stores the trade in the following order

#Contitions
There are couples of validation, we need to provide in the above assignment
1.	During transmission if the lower version is being received by the store it will reject the trade and throw an exception. If the version is same it will override the existing record.
2.	Store should not allow the trade which has less maturity date then today date.
3.	Store should automatically update the expire flag if in a store the trade crosses the maturity date.

#To full fill 3rd condition we have added cron job and it will execute every 1 hour, if you want to change the time change the below property value.
application.properties
  trade.scheduler.cron.refresh=0 0 0/1 1/1 * ?

#Swagger
Please use below URL for Swagger
http://localhost:8080/swagger-ui.html#/trade-controller

