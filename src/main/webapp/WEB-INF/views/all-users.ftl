<html>
    <head>
        <title>All registered users </title>
    </head>
    <body>
        <h1> Users </h1>
        <ol>
            <#list users as user>
                <li>
                    ID=${user.id}; Email=${user.email}; Role=${user.userRole}
                </li>
            </#list>
        </ol>
    </body>
</html>