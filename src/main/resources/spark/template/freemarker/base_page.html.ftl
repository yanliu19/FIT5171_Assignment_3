<#-- @ftlvariable name="message" type="java.lang.String" -->
<#-- @ftlvariable name="user" type="rockets.model.User" -->

<!doctype html public "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Rockets: a rocket information repository</title>

    <meta http-equiv="Content-type" content="text/html;charset=UTF-8">

    <meta name="description" content="Rockets: a rocket information repository">
</head>

<body>

<div>
    <#if user??>
        <span><h3>Welcome back: ${user.firstName} ${user.lastName}!</h3></span>
        <p><a href="/logout">Logout</a></p>
    <#else>
        <span><h3>Welcome to Rockets!</h3></span>
    </#if>

    <p>What do you want to do today?</p>

	<ul>
        <#if user??>
		    <li>Go to your <a href="/user/${user.id}">own page.</a></li>
        <#else>
            <li><a href="/login">Login</a> or <a href="/register">register</a>.</li>
        </#if>
		<li>See the list of <a href="/users">users</a> registered with the system.</li>
		<li>See the list of <a href="/rockets">rockets</a>.</li>
	</ul>
</div>

</body>
</html>