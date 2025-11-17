<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>PharmaEase | Register</title>

    <style>
        *{
            margin:0;
            padding:0;
            box-sizing:border-box;
            font-family: "Poppins", sans-serif;
        }
        body {
            background: linear-gradient(135deg,#06D6A0,#1B9AAA);
            height: 100vh;
            display:flex;
            justify-content:center;
            align-items:center;
            padding:15px;
        }
        .container {
            background: #fff;
            width: 480px;
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
            transition:0.3s;
        }
        .inputBox input:focus {
            border-color:#1B9AAA;
            box-shadow:0 0 5px rgba(27,154,170,0.4);
        }
        .btn {
            width:100%;
            padding:14px;
            background:#1B9AAA;
            border:none;
            color:#fff;
            font-size:16px;
            border-radius:8px;
            cursor:pointer;
            transition:0.3s;
            margin-top:10px;
        }
        .btn:hover {
            background:#168a90;
        }

        .bottomText {
            text-align:center;
            margin-top:15px;
            font-size:14px;
        }
        .bottomText a {
            color:#1B9AAA;
            text-decoration:none;
            font-weight:600;
        }
    </style>

    <script>
        function validateRegister(){
            let name = document.getElementById("name").value.trim();
            let email = document.getElementById("email").value.trim();
            let password = document.getElementById("password").value.trim();
            let confirm = document.getElementById("confirm").value.trim();

            if(name === "" || email === "" || password === "" || confirm === ""){
                alert("All fields are required.");
                return false;
            }

            if(password !== confirm){
                alert("Passwords do not match.");
                return false;
            }

            if(password.length < 6){
                alert("Password must be at least 6 characters.");
                return false;
            }

            return true;
        }
    </script>

</head>
<body>

<div class="container">
    <h2>Create Account</h2>

    <form action="RegisterServlet" method="post" onsubmit="return validateRegister()">

        <div class="inputBox">
            <label>Full Name</label>
            <input type="text" name="name" id="name" placeholder="Enter full name">
        </div>

        <div class="inputBox">
            <label>Email Address</label>
            <input type="email" name="email" id="email" placeholder="Enter email">
        </div>

        <div class="inputBox">
            <label>Password</label>
            <input type="password" name="password" id="password" placeholder="Enter password">
        </div>

        <div class="inputBox">
            <label>Confirm Password</label>
            <input type="password" id="confirm" placeholder="Re-enter password">
        </div>

        <button type="submit" class="btn">Register</button>

        <div class="bottomText">
            Already have an account? <a href="login.jsp">Login</a>
        </div>

    </form>
</div>

</body>
</html>