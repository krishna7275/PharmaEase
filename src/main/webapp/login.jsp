<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>Login - PharmaEase</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body class="d-flex align-items-center justify-content-center" style="min-height:100vh;">
<div class="card p-4" style="max-width:420px; width:100%;">
    <h4 class="mb-3 text-center">PharmaEase — Login</h4>

    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>

    <form action="auth" method="post">
        <input type="hidden" name="action" value="login"/>
        <div class="mb-2"><label>Email</label>
            <input class="form-control" type="email" name="email" required/>
        </div>
        <div class="mb-3"><label>Password</label>
            <input class="form-control" type="password" name="password" required/>
        </div>
        <div class="d-flex justify-content-between mb-3">
            <a href="forgot-pass.jsp">Forgot Password?</a>
            <a href="register.jsp">Register</a>
        </div>
        <button class="btn btn-primary w-100" type="submit">Login</button>
    </form>
</div>
</body>
</html>
