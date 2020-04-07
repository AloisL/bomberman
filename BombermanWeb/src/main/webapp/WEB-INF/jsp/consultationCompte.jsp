<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<link
        href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
        rel="stylesheet" id="bootstrap-css">
<script
        src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<script
        src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<!------ Include the above in your HEAD tag ---------->
<link rel="stylesheet" href="./css/connect.css">
<body>
<div id="register">
    <div class="container">
        <div id="login-row" class="row justify-content-center align-items-center">
            <div id="login-column" class="col-md-6">
                <div id="login-box" class="col-md-12">
                    <form id="login-form" class="form" action="/login" method="post">
                        </br>
                        </br>
                        </br>
                        <h3 class="text-center text-info">Consultation du compte :</h3>
                        </br>
                        </br>
                        </br>
                        <div class="form-group">
                            <label for="username" class="text-info">Username:</label>
                            <input type="text" name="username" id="username" class="form-control">
                        </div>
                        <div class="form-group">
                            <label for="password" class="text-info">Password:</label>
                            <input type="password" name="password" id="password" class="form-control">
                        </div>
                        <div class="form-group">
                            <label for="password" class="text-info">Password:</label>
                            <input type="password" name="password" id="passwordverif" class="form-control">
                        </div>
                        <div class="form-group">
                            <label for="mail" class="text-info">Mail:</label>
                            <input type="email" name="mail" id="mail" class="form-control">
                        </div>
                        <div class="form-group">
                            <br>
                            <input type="submit" name="submit" class="btn btn-info btn-md" value="Modifier">
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>