<#-- @ftlvariable name="errorMsg" type="java.lang.String" -->
<#-- @ftlvariable name="user" type="rockets.model.User" -->

<!doctype html public "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Rockets: a rocket information repository</title>

    <meta http-equiv="Content-type" content="text/html;charset=UTF-8">

    <meta name="description" content="Rockets: a rocket information repository - User">
</head>

<body>
<div id="title_pane">
<#if user??>
    <h3>${user.firstName} ${user.lastName}'s Page</h3>
<#else>
    <h3>User page</h3>
    <p>Error: ${errorMsg}</p>
</#if>
</div>

<div>

<#if user??>
    <span><h3>Welcome back: ${user.firstName} ${user.lastName}!</h3></span>
    <p><a href="/logout">Logout</a></p>
<#else>
    <span><h3>Welcome to Fly Me To Mars!</h3></span>
    <p>You can <a href="/login">Login</a> or <a href="/register">register</a>.</p>
</#if>
</div>

</body>
</html>