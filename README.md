# Documentation for application Real estate market
## API
### Users

<table>

<tr>
    <th>METHOD</th>
    <th>URL</th>
    <th>ACTION</th>
    <th>ROLES</th>
</tr>


<tr>
<td>🟦 GET</td>
<td>

`/api/users`
</td>
<td>Return all users</td>
<td>ADMIN</td>
</tr>


<tr>
<td>🟩 POST</td>
<td>

`/api/users`
</td>
<td>Add user from DTO</td>
<td>ALL</td>
</tr>


<tr>
<td colspan="4">

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
<td>🟦 GET</td>
<td>

`/api/users/{id}`
</td>
<td>Return user by id</td>
<td>ADMIN</td>
</tr>


<tr>
<td>🟥 DELETE</td>
<td>

`/api/users/{id}`
</td>
<td>Delete user by id</td>
<td>ADMIN</td>
</tr>


<tr>
<td>🟧 PUT</td>
<td>

`/api/users/{id}`
</td>
<td>Update user by id from DTO</td>
<td>ADMIN</td>
</tr>


<tr>
<td colspan="4">

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
<td>🟦 GET</td>
<td>

`/api/users/current`
</td>
<td>Return current user</td>
<td>ALL</td>
</tr>


<tr>
<td>🟧 PUT</td>
<td>

`/api/users/current`
</td>
<td>Update current user from DTO</td>
<td>ALL</td>
</tr>


<tr>
<td colspan="4">

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
