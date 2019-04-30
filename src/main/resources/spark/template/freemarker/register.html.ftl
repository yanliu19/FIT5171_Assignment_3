<#-- @ftlvariable name="email" type="java.lang.String" -->
<#-- @ftlvariable name="firstName" type="java.lang.String" -->
<#-- @ftlvariable name="lastName" type="java.lang.String" -->
<#-- @ftlvariable name="errorMsg" type="java.lang.String" -->

<!doctype html public "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Fly me to Mars - a mission registration system.</title>

    <meta http-equiv="Content-type" content="text/html;charset=UTF-8">

    <meta name="description" content="Rockets: a rocket information repository - register">
</head>

<body>

<div id="title_pane">
    <h3>User Registration</h3>
</div>

<div>
    <p>* Fields are required.</p>
</div>
<form name="create_user" action="/register" method="POST">
    <div id="admin_left_pane" class="fieldset_without_border">
        <div class="legend_no_indent">Account Details</div>
        <ol>
            <li>
                <label for="email" class="bold">Email Address:*</label>
                <input id="email" name="email" type="text" value="${email}">
            </li>
            <li>
                <label for="password" class="bold">Password:*</label>
                <input id="password" name="password" type="password" placeholder="Enter password here">
            </li>
            <li>
                <label for="firstName" class="bold">First Name:*</label>
                <input id="firstName" name="firstName" type="text" value="${firstName}">
            </li>
            <li>
                <label for="lastName" class="bold">Last Name:*</label>
                <input id="lastName" name="lastName" type="text" value="${lastName}">
            </li>
        </ol>
    </div>

    <#if errorMsg?? && errorMsg?has_content>
        <div id="error">
            <p>Error: ${errorMsg}</p>
        </div>
    </#if>

    <div id="buttonwrapper">
        <button type="submit">Create New User</button>
        <a href="/">Cancel</a>
    </div>
</form>
</body>
</html>