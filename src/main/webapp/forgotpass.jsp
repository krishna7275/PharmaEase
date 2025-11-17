<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Reset Password | PharmaEase</title>

    <style>
        *{margin:0; padding:0; box-sizing:border-box; font-family:"Poppins", sans-serif;}
        body{
            background: linear-gradient(135deg,#5A5AF6,#6EC6FF);
            height:100vh;
            display:flex;
            justify-content:center;
            align-items:center;
        }
        .container{
            width:420px;
            background:#fff;
            padding:35px 40px;
            border-radius:15px;
            box-shadow:0 8px 25px rgba(0,0,0,0.20);
            animation:fade .5s ease;
        }
        @keyframes fade{from{opacity:0; transform:translateY(20px);} to{opacity:1;}}
        h2{text-align:center; color:#222; margin-bottom:20px;}

        .inputBox{margin-bottom:18px;}
        .inputBox label{font-weight:600; font-size:14px;}
        .inputBox input{
            width:100%; padding:12px;
            margin-top:6px; border-radius:8px;
            border:1.4px solid #bbb; font-size:15px;
            transition:.3s;
        }
        .inputBox input:focus{
            border-color:#5A5AF6;
            box-shadow:0 0 6px rgba(90,90,246,0.4);
        }
        .btn{
            width:100%; padding:14px;
            background:#5A5AF6; color:#fff;
            border:none; border-radius:8px;
            cursor:pointer; font-size:16px;
            transition:.3s;
        }
        .btn:hover{background:#4949d4;}
        .back{text-align:center; margin-top:15px;}
        .back a{text-decoration:none; color:#5A5AF6; font-weight:600;}
    </style>

    <script>
        function validateForm(){
            let email = document.getElementById("email").value.trim();
            let pass = document.getElementById("password").value.trim();
            let confirm = document.getElementById("confirm").value.trim();

            if(email === "" || pass === "" || confirm === ""){
                alert("All fields are required!");
                return false;
            }
            if(pass.length < 6){
                alert("Password must be at least 6 characters!");
                return false;
            }
            if(pass !== confirm){
                alert("Passwords do not match!");
                return false;
            }
            return true;
        }
    </script>

</head>
<body>

<div class="container">
    <h2>Reset Password</h2>

    <form action="ForgotPasswordServlet" method="post" onsubmit="return validateForm()">

        <div class="inputBox">
            <label>Email Address</label>
            <input type="email" id="email" name="email" placeholder="Enter your registered email" required>
        </div>

        <div class="inputBox">
            <label>New Password</label>
            <input type="password" id="password" name="password" placeholder="New password" required>
        </div>

        <div class="inputBox">
            <label>Confirm Password</label>
            <input type="password" id="confirm" placeholder="Confirm password" required>
        </div>

        <button class="btn" type="submit">Update Password</button>

        <div class="back">
            <a href="login.jsp">Back to Login</a>
        </div>
    </form>

</div>

</body>
</html>
