<html>
    <head>
        <title>User info </title>
    </head>
    <body>
        <h1> User info </h1>
        <p>
            Email ${user.email}
            ID=${user.id}
            Role=${user.userRole}
        </p>
        <h3>Tickets</h3>
        <#list user.boughtTickets as ticket>
            <a href="/purchase/ticket/${ticket.id}">${ticket.id}</a>
        </#list>
    </body>
</html>