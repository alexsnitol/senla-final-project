<style>
    .block {
        display: inline-block;
        padding: 4px;
        margin: 2px;
        border-radius: 3px;
        font-weight: 500;
        color: #c7c7c7;
        background-color: #3b3b3b;
    }
    .role {
        color: #8080e7;
        background-color: #343448;
    }
    .post-method {
        color: #66c766;
        background-color: #245424;
    }
    .get-method {
        color: #c812f1;
        background-color: #412454;
    }
    .put-method {
        color: #e07856;
        background-color: #493421;
    }
    .delete-method {
        color: #f11212;
        background-color: #542424;
    }
    .json-text::before {
        content: 'JSON example:';
    }
</style>

# Documentation for application Real estate market
## API
### Users

<table>
<tr>
    <td><div class="block get-method">GET</div></td>
    <td><div class="block">/api/users</div></td>
    <td>Return all users</td>
    <td>
        <div class="block role">ADMIN</div>
    </td>
</tr>
<tr>
    <td><div class="block post-method">POST</div></td>
    <td><div class="block">/api/users</div></td>
    <td>Add user from DTO</td>
    <td>
        <div class="block role">ALL</div>
    </td>
</tr>
<tr>
<td colspan="4">
<div class="json-text"></div>

    {
        "username": "alexsnitol",
        "password": "alexsnitol",
	    "lastName": "Слотин",
	    "firstName": "Александр",
	    "patronymic": "Сергеевич",
	    "email": "sslotin74@gmail.com",
	    "phoneNumber": "89000000000"
    }
</td>
</tr>
<tr>
    <td><div class="block get-method">GET</div></td>
    <td><div class="block">/api/users/{id}</div></td>
    <td>Return user by id</td>
    <td>
        <div class="block role">ADMIN</div>
    </td>
</tr>
<tr>
    <td><div class="block delete-method">DELETE</div></td>
    <td><div class="block">/api/users/{id}</div></td>
    <td>Delete user by id</td>
    <td>
        <div class="block role">ADMIN</div>
    </td>
</tr>
<tr>
    <td><div class="block put-method">PUT</div></td>
    <td><div class="block">/api/users/{id}</div></td>
    <td>Update user by id from DTO</td>
    <td>
        <div class="block role">ADMIN</div>
    </td>
</tr>
<tr>
<td colspan="4">
<div class="json-text"></div>

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
</td>
</tr>
<tr>
    <td><div class="block get-method">GET</div></td>
    <td><div class="block">/api/users/current</div></td>
    <td>Return current user</td>
    <td>
        <div class="block role">ALL</div>
    </td>
</tr>
<tr>
    <td><div class="block put-method">PUT</div></td>
    <td><div class="block">/api/users/current</div></td>
    <td>Update current user from DTO</td>
    <td>
        <div class="block role">ALL</div>
    </td>
</tr>
<tr>
<td colspan="4">
<div class="json-text"></div>

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
</td>
</tr>
</table>
