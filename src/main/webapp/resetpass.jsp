<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>Reset Your Password</title>
<style>
body{font-family:Poppins;background:#edf2ff;padding:40px;}
.container{
    width:420px;margin:auto;background:#fff;
    padding:30px;border-radius:10px;
    box-shadow:0 4px 20px rgba(0,0,0,0.1);
}
h2{text-align:center;margin-bottom:20px;}
input{
    width:100%;padding:12px;margin-top:10px;
    border-radius:8px;border:1px solid #bbb;
}
button{
    width:100%;padding:12px;margin-top:15px;
    background:#4a72ff;color:#fff;border:none;
    border-radius:8px;font-size:16px;cursor:pointer;
}
</style>
</head>
<body>
<div class="container">
    <h2>Reset Password</h2>

    <form action="ResetPasswordServlet" method="post">
        <input type="hidden" name="token" value="<%= request.getParameter("token") %>">

        <label>New Password</label>
        <input type="password" name="password" required>

        <label>Confirm Password</label>
        <input type="password" name="confirm" required>

        <button type="submit">Update Password</button>
    </form>
</div>
</body>
</html>
