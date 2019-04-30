<#-- @ftlvariable name="errorMsg" type="java.lang.String" -->
<#-- @ftlvariable name="users" type="java.util.List<rockets.model.User>" -->

<!doctype html public "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Rockets: a rocket information repository</title>

    <meta http-equiv="Content-type" content="text/html;charset=UTF-8">

    <meta name="description" content="Rockets: a rocket information repository - Users">
</head>

<body>
<div id="title_pane">
    <h3>User Listing Page</h3>
</div>

<div>
<#if errorMsg?? && errorMsg?has_content>
    <li><h4 class="errorMsg">${errorMsg}</h4></li>
<#elseif users?? && users?has_content>
    <ul>
        <#list users as user>
            <li><a href="/user/${user.id}">${user.firstName} ${user.lastName}</a></li>
        </#list>

    </ul>
<#else>
    <p>Nobody in the system. <a href="/register">Register</a> now!</p>
</#if>

</div>

</body>
</html>