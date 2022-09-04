# Documentation for application Real estate market
## API

### Authorization

<table>

<tr>
    <th>METHOD</th>
    <th>URL</th>
    <th>ACTION</th>
    <th>ALLOWED ROLES</th>
</tr>


<tr>
<td>🟩POST</td>
<td>

`/api/auth`
</td>
<td>Return JWT token if authorization is successful</td>
<td>ALL</td>
</tr>


<tr>
<td colspan="4">

Optional keys: **none**
```json
{
    "username": "alexsnitol",
    "password": "alexsnitol"
}
```
</td>
</tr>

</table>


### Users

<table>

<tr>
    <th>METHOD</th>
    <th>URL</th>
    <th>ACTION</th>
    <th>ALLOWED ROLES</th>
</tr>


<tr>
<td>🟦GET</td>
<td>

`/api/users`
</td>
<td>Return all users</td>
<td>ADMIN</td>
</tr>


<tr>
<td>🟩POST</td>
<td>

`/api/users`
</td>
<td>Add user from DTO</td>
<td>ALL</td>
</tr>


<tr>
<td colspan="4">

Optional keys: `lastName`, `firstName`, `patronymic`, `email`, `phoneNumber`
```json
{
    "username": "alexsnitol",
    "password": "alexsnitol",
    "lastName": "Слотин",
    "firstName": "Александр",
    "patronymic": "Сергеевич",
    "email": "sslotin74@gmail.com",
    "phoneNumber": "89000000000"
}
```
</td>
</tr>


<tr>
<td>🟦GET</td>
<td>

`/api/users/{id}`
</td>
<td>Return user by id</td>
<td>ADMIN</td>
</tr>


<tr>
<td>🟥DELETE</td>
<td>

`/api/users/{id}`
</td>
<td>Delete user by id</td>
<td>ADMIN</td>
</tr>


<tr>
<td>🟧PUT</td>
<td>

`/api/users/{id}`
</td>
<td>Update user by id from DTO</td>
<td>ADMIN</td>
</tr>


<tr>
<td colspan="4">

Optional keys: **all**
```json
{
    "username": "alexsnitol",
    "password": "alexsnitol",
    "enabled": true,
    "lastName": "Слотин",
    "firstName": "Александр",
    "patronymic": "Сергеевич",
    "email": "sslotin74@gmail.com",
    "phoneNumber": "89000000000"
}
```
</td>
</tr>


<tr>
<td>🟦GET</td>
<td>

`/api/users/current`
</td>
<td>Return current user</td>
<td>ALL</td>
</tr>


<tr>
<td>🟧PUT</td>
<td>

`/api/users/current`
</td>
<td>Update current user from DTO</td>
<td>ALL</td>
</tr>


<tr>
<td colspan="4">

Optional keys: **all**
```json
{
    "username": "alexsnitol",
    "password": "alexsnitol",
    "enabled": false,
    "lastName": "Слотин",
    "firstName": "Александр",
    "patronymic": "Сергеевич",
    "email": "sslotin74@gmail.com",
    "phoneNumber": "89000000000"
}
```
</td>
</tr>

</table>


### Messages

<table>

<tr>
    <th>METHOD</th>
    <th>URL</th>
    <th>ACTION</th>
    <th>ALLOWED ROLES</th>
</tr>


<tr>
<td>🟦GET</td>
<td>

`/api/messages/users`
</td>
<td>

Return all users with which **current user** have been messages in order date
of sending last message both incoming and outgoing</td>
<td>ALL</td>
</tr>

<tr>
<td>🟦GET</td>
<td>

`/api/messages/users/{id}`
</td>
<td>

Return all messages of **current user** with user by `id` in asc order sending date time</td>
<td>ALL</td>
</tr>

<tr>
<td>🟩POST</td>
<td>

`/api/messages/users/{id}`
</td>
<td>

Send a message to user by `id` from **current user**</td>
<td>ALL</td>
</tr>


<tr>
<td colspan="4">

Optional keys: **none**
```json
{
    "text": "Hello!"
}
```
</td>
</tr>

</table>


### Reviews

<table>

<tr>
    <th>METHOD</th>
    <th>URL</th>
    <th>ACTION</th>
    <th>ALLOWED ROLES</th>
</tr>


<tr>
<td>🟦GET</td>
<td>

`/api/reviews/customers/{customerId}/reviews`
</td>
<td>

Return all customer reviews of sellers by `customerId`
</td>
<td>ADMIN</td>
</tr>

<tr>
<td>🟦GET</td>
<td>

`/api/reviews/sellers/{sellerId}/reviews`
</td>
<td>

Returns all reviews of seller by `sellerId`</td>
<td>ALL</td>
</tr>

<tr>
<td>🟩POST</td>
<td>

`/api/reviews/sellers/{sellerId}/reviews`
</td>
<td>

Send a review to seller by `sellerId` from current user</td>
<td>ALL</td>
</tr>


<tr>
<td colspan="4">

Optional keys: `comment`
```json
{
    "comment": "Very nice!",
    "note": 5
}
```
</td>
</tr>

</table>