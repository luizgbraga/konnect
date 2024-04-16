<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Konnect - register</title>
  <link rel="stylesheet" href="register.css">
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
          <img class="absolute top-12 title" src="${pageContext.request.contextPath}/assets/images/konnect-logo.svg" alt="logo" width="48">
        </a>
        <p class="big-title">Crie sua conta</p>
      </div>
  
      <div class="credentials-ctn relative flex flex-column align-end justify-center">
        <div class="credentials flex flex-column">
          <div class="forms">
            <label for="username-input">Username</label>
            <input id="username-input" type="text" placeholder="BigoLau">
          </div>

          <div class="forms">
            <label for="password-input">Senha</label>
            <input id="password-input" type="password" placeholder="*******">
          </div>
        </div>

        <div class="btn-ctn flex flex-row align-end">
          <a class="btn secondary-btn" href="${pageContext.request.contextPath}/login">Voltar</a>
  
          <div id="register-button" class="btn primary-btn register">Cadastrar</div>
        </div>
      </div>
    </div>
  </div>
</body>

<script type="module" src="${pageContext.request.contextPath}/views/register/register.js"></script>
</html>