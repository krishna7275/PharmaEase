<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>PharmaEase | Login</title>

    <style>
        *{
            margin:0;
            padding:0;
            box-sizing:border-box;
            font-family: "Poppins", sans-serif;
        }
        body {
            background: linear-gradient(135deg,#4CC9FE,#4895EF);
            height: 100vh;
            display:flex;
            justify-content:center;
            align-items:center;
        }
        .container {
            background: #fff;
            width: 420px;
            padding: 35px 40px;
            border-radius: 15px;
            box-shadow: 0 8px 25px rgba(0,0,0,0.15);
            animation: fadeIn .6s ease;
        }

        @keyframes fadeIn {
            from {opacity:0; transform: translateY(20px);}
            to   {opacity:1; transform: translateY(0);}
        }

        h2 {
            text-align:center;
            margin-bottom:20px;
            color:#222;
        }

        .inputBox {
            margin-bottom:18px;
        }
        .inputBox label {
            font-size:14px;
            font-weight:600;
        }
        .inputBox input {
            width:100%;
            padding:12px;
            border:1.4px solid #bbb;
            border-radius:8px;
            margin-top:6px;
            font-size:15px;
            transition:0.3s ease;
        }
        .inputBox input:focus {
            border-color:#4895EF;
            box-shadow: 0 0 5px rgba(72,149,239,0.4);
        }

        .btn {
            width:100%;
            padding:14px;
            background:#4895EF;
            border:none;
            color:#fff;
            font-size:16px;
            border-radius:8px;
            cursor:pointer;
            transition:0.3s;
            margin-top:10px;
        }
        .btn:hover {
            background:#3b82e0;
        }

        .bottomText {
            text-align:center;
            margin-top:15px;
            font-size:14px;
        }
        .bottomText a {
            color:#4895EF;
            text-decoration:none;
            font-weight:600;
        }
    </style>

    <script>
        function validateLogin(){
            const email = document.getElementById("email").value.trim();
            const password = document.getElementById("password").value.trim();

            if(email === "" || password === ""){
                alert("All fields are required.");
                return false;
            }
            return true;
        }
    </script>

</head>
<body>

<div class="container">
    <h2>PharmaEase Login</h2>

    <form action="LoginServlet" method="post" onsubmit="return validateLogin()">

        <div class="inputBox">
            <label>Email Address</label>
            <input type="email" name="email" id="email" placeholder="Enter email">
        </div>

        <div class="inputBox">
            <label>Password</label>
            <input type="password" name="password" id="password" placeholder="Enter password">
        </div>

        <button type="submit" class="btn">Login</button>

        <div class="bottomText">
            Don’t have an account? <a href="register.jsp">Register</a>
        </div>
<div class="bottomText">
    <a href="forgotPassword.jsp">Forgot Password?</a>
</div>

    </form>
</div>

</body>
</html>