<html>
    <head>
        <title>Guide</title>
    </head>
    <body>
        <h1> Welcome to Spring Advanced Training </h1>
        <p>Author: Maksym Baziuk</p>
        <p>API map:</p>
        <ol>
            <li>
                User handling (see com.baziuk.spring.user.web.UserController):
                <ul>
                    <li>
                        User info by email: GET /user/info?email={email_address}
                    </li>
                    <li>
                        User info by id: GET /user/info/{id}
                    </li>
                    <li>
                        Create user: PUT /user/create?email={email_address}&birthday={dd/MM/yyyy}
                    </li>
                    <li>
                        Get all registered users: GET /user/all
                    </li>
                    <li>
                        Update user: POST /user/update?email={email}&birthday={dd/MM/yyyy}&id={id}&userRole={role} <br>
                        after update redirecting to: GET /user/info/{id}
                    </li>
                </ul>
            </li>
            <li>
                Booking endpoints (see com.baziuk.spring.booking.web.BookingController):
                <ul>
                    <li>
                        Check availability of sits: GET /purchase/check?event={eventId}&show={showId}&places={comma-separated place numbers}<br>
                        Returns TRUE/FALSE as JSON
                    </li>
                    <li>
                        Book tickets: POST /purchase/buy?event={eventId}&show={showId}&places={comma-separated place numbers}&user={userId}
                        Returns Ticket DTOs as JSON
                    </li>
                    <li>
                        Get ticket price: GET purchase/ticket/price?event={eventId}&show={showId}&places={comma-separated place numbers}&user={userId}
                        Returns calculated price double as JSON
                    </li>
                    <li>
                        Get ticket by id: GET /purchase/ticket/{ticketId}
                        Returns PDF with a ticket
                    </li>
                </ul>
            </li>
            <li>
                Event endpoints (see com.baziuk.spring.events.web.EventController):
                <ul>
                    <li>
                        Get all events: GET /event/all
                        Returns Event DTOs as JSON
                    </li>
                    <li>
                        Get event by id: GET /event/get/{eventId}
                        Returns Event DTO as JSON
                    </li>
                    <li>
                        Get event for date range: GET /event/daterange?from={from date in format "dd/MM/yyyy.HH:mm"}&to={to date in format "dd/MM/yyyy.HH:mm"}
                        Returns Event DTOs as JSON
                    </li>
                    <li>
                        Get next events: GET /event/daterange?from={from date in format "dd/MM/yyyy.HH:mm"}
                        Returns Event DTOs as JSON
                    </li>
                </ul>
            </li>
            <li>
                Auditoriums endpoints (see com.baziuk.spring.auditorium.web.AuditoriumController)
                <ul>
                    <li>
                        Get all auditoriums: GET /aud/all
                        Returns a list of Auditorium DTOs as JSON
                    </li>
                </ul>
            </li>
        </ol>
        <p>
            User feed files (see com.baziuk.spring.user.web.UserFeedController):
            <form action="/userfeed/load" enctype="multipart/form-data" method="post">
                <input type="file" name="file"><br>
                <input type="file" name="file"><br>
                <input type="submit" name="SUBMIT">
            </form>
        </p>
        <p>
            Events upload (see com.baziuk.spring.event.web.EventFeedController):
            <form action="/eventfeed/load" enctype="multipart/form-data" method="post">
                <input type="file" name="file"><br>
                <input type="file" name="file"><br>
                <input type="submit" name="SUBMIT">
            </form>
        </p>
        <p>
            Exception resolving: see com.baziuk.spring.web.WebAppConfig.simpleMappingExceptionResolver bean config
        </p>
        <p>
            <form action="/logout" method="post">
            <input type="submit"
                   value="Log out" />
            <input type="hidden"
                   name="${_csrf.parameterName}"
                   value="${_csrf.token}"/>
            </form>
        </p>
    </body>
</html>