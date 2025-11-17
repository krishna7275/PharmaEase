<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>Register - PharmaEase</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body class="d-flex align-items-center justify-content-center" style="min-height:100vh;">
<div class="card p-4" style="max-width:520px; width:100%;">
    <h4 class="mb-3 text-center">Create Pharmacist Account</h4>

    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>

    <form action="pharmacist" method="post">
        <input type="hidden" name="action" value="insert"/>
        <div class="mb-2">
            <label>Full name</label>
            <input class="form-control" name="name" required/>
        </div>
        <div class="mb-2">
            <label>Email</label>
            <input class="form-control" type="email" name="email" required/>
        </div>
        <div class="mb-2">
            <label>Password</label>
            <input class="form-control" type="password" name="password" minlength="6" required/>
        </div>
        <div class="mb-3">
            <label>Role</label>
            <select name="role" class="form-select">
                <option value="Pharmacist" selected>Pharmacist</option>
                <option value="Admin">Admin</option>
            </select>
        </div>
        <button class="btn btn-success w-100">Register</button>
    </form>
</div>
</body>
</html>
