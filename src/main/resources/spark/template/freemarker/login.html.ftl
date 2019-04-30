<#-- @ftlvariable name="user_name" type="java.lang.String" -->
<#-- @ftlvariable name="password" type="java.lang.String" -->
<#-- @ftlvariable name="errorMsg" type="java.lang.String" -->

<!doctype html public "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Rockets: a rocket information repository</title>

    <meta http-equiv="Content-type" content="text/html;charset=UTF-8">

    <meta name="description" content="Rockets: a rocket information repository - Login.">
</head>

<body>

<div id="title_pane">
    <h3>User Login</h3>
</div>

<form name="f" action="/login" method="POST">
    <div class="fieldset" id="login">
        <div class="legend">Login with your email address and password.</div>
        <ol>
            <li>
                <label for="user_name">Email: </label>
                <input id="user_name" name="user_name" type="text" value="${user_name}">
            </li>
            <li>
                <label for="password">Password:</label>
                <input id="password" name="password" type="password">
            </li>
        <#if errorMessage?? && errorMessage?has_content>
            <li><h4 class="errorMsg">${errorMessage}</h4></li>
        </#if>
        </ol>
    </div>

    <#if errorMsg?? && errorMsg?has_content>
        <div id="error">
            <p>Error: ${errorMsg}</p>
        </div>
    </#if>

    <div id="buttonwrapper">
        <button type="submit">Login</button>
        <a href="/register">Register</a>
        <a href="/">Cancel</a>
    </div>
</form>
