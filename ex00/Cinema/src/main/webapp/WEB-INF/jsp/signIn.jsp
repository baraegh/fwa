<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sign In</title>
    <%-- <link rel="stylesheet" href="/Cinema/css/signIn.css">/ --%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/signIn.css">
</head>
<body>

<div class="signin-card">

    <h2>Welcome back</h2>
    <p class="subtitle">Sign in to your account to continue</p>

    <div class="error-msg" id="errorMsg"></div>

    <form action="/Cinema/signIn" method="POST">

        <div class="form-group">
            <label for="email">Email</label>
            <input type="email" id="email" name="email" placeholder="you@example.com" required>
        </div>

        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" id="password" name="password" placeholder="••••••••" required>
        </div>

        <button type="submit" class="btn-submit">Sign In</button>

    </form>

    <p class="signup-link">Don't have an account? <a href="/Cinema/signUp">Sign up</a></p>

</div>

<script>
    const params = new URLSearchParams(window.location.search);
    if (params.get("error")) {
        const msg = document.getElementById("errorMsg");
        msg.textContent = "Invalid email or password.";
        msg.classList.add("visible");
    }
</script>

</body>
</html>