1. `mvn clean install`
2. `run UsersManagementApplication`
3.  Test application with for example postman:

Create user -> `POST: api/v1/users`
Example: 
* {
* "id": 1,
* "email": "test@test.com",
* "firstName": "Name",
* "lastName": "Surname",
* "birthDate": "1995-02-12",
* "address": "Dnipro",
* "phoneNumber": "111"
* }

Update user -> `PUT: api/v1/users/{id}`
Example:
*   {
*   "email": "new@gmail.com",
*   "firstName": "New Firstname",
*   "lastName": "New LastName",
*   "birthDate": "2000-02-12",
*   "address": "London",
*   "phoneNumber": "222"
*   }}

Update user info -> `PATCH: api/v1/users/{id}`
Example:
* {
* "email": "new@gmail.com",
* "firstName": "newFN",
* "lastName": "newLN",
* "birthDate": "2022-02-12"
* }

Delete user -> `DELETE: api/v1/users/{id}`

Get users by date range -> `GET: /api/v1/users/range?from={from}&to={to}`
Example:
* /api/v1/users/range?from=2025-01-01&to=2024-01-05