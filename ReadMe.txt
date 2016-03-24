Create a restful web service for User Management application using * Spark Java http://sparkjava.com/

ASSUMPTIONS

* User has the following properties

o id

o firstName (alphabets)

o middleName (alphabets but it is optional)

o lastName (alphabets)

o age (valid non zero positive number)

o gender (M or F)

o phone (10-digit positive number)

o zip (optional field)

* Use a simple collection (preferably HashMap) of objects as your data store.

* The User's ID uniquely identifies a user.

* Feel free to use any framework or library in your code. But Spark Java is a must for exposing your service.

QUESTION

* Develop the below 4 services using Spark Java

o createUser

* Takes input as JSON.

* Creates the user if he is not already available in the data store.

o getAllUsers

* Gives the list of all users that are in the data store

o updateUser

* Takes input as JSON

* Finds the user from the data store

* if found, updates the required fields

* if not found, sends back a 404 saying user not found

* Unit tests using any unit testing library