<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Konnect - Login</title>
  <link rel="stylesheet" href="login.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/accounts.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/input.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/fonts.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/spacings.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/grid.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/buttons.css">
</head>

<body>
  <div class="background-ctn">
    <div class="login-box box-shadow">
      <div class="greetings-ctn relative flex flex-column align-start justify-center">
        <a href="home.jsp">
          <img class="absolute top-12 title" src="${pageContext.request.contextPath}/assets/images/Konnect-logo.svg" alt="logo">
        </a>
        <p class="big-title">Seja bem-vindo ao Konnect</p>

        <div class="create-account absolute flex flex-row">
          <p>Não tem uma conta?</p>
          <a class="text-btn" href="${pageContext.request.contextPath}/registrate">Criar conta</a>
        </div>
      </div>
  
      <div class="credentials-ctn relative flex flex-column align-end justify-center">
        <div class="credentials flex flex-column">
          <div class="forms">
            <label for="email">Username</label>
            <input id="email-input" type="text" placeholder="BigoLau">
          </div>

          <div class="forms">
            <label for="password">Senha</label>
            <input id="password-input" type="password" placeholder="*******">
          </div>

          <div class="reset-password">
            <p class="text-btn small-title dark-gray">Esqueceu a senha?</p>
          </div>
        </div>

        <div id="login-button" class="btn primary-btn login">Entrar</div>
      </div>
    </div>
  </div>
</body>
<script src="login.js"></script>
</html>