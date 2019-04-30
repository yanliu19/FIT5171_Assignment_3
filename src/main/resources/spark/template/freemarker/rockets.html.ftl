<#-- @ftlvariable name="errorMsg" type="java.lang.String" -->
<#-- @ftlvariable name="rockets" type="java.util.Collection<rockets.model.Rocket>" -->

<!doctype html public "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Rockets: a rocket information repository</title>

    <meta http-equiv="Content-type" content="text/html;charset=UTF-8">

    <meta name="description" content="Rockets: a rocket information repository - Rockets">
</head>

<body>
<div id="title_pane">
    <h3>Rocket Listing Page</h3>
</div>

<div>
<#if errorMsg?? && errorMsg?has_content>
    <li><h4 class="errorMsg">${errorMsg}</h4></li>
<#elseif rockets?? && rockets?has_content>
    <ul>
        <#list rockets as rocket>
            <li><a href="/rocket/${rocket.id}">${rocket.name}</a></li>
        </#list>

    </ul>
<#else>
    <p>No rocket yet in the system. <a href="/rocket/create">Create one</a> now!</p>
</#if>

</div>

</body>
</html>