<%@ page isErrorPage="true" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>Error</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body class="d-flex align-items-center justify-content-center" style="min-height:100vh;">
<div class="text-center">
    <h2>Something went wrong</h2>
    <p>${requestScope['javax.servlet.error.message']}</p>
    <a href="dashboard" class="btn btn-primary">Go Home</a>
</div>
</body>
</html>
