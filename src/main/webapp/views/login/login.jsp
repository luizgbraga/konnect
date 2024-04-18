<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Konnect - Login</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/views/login/login.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/global.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/accounts.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/input.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/fonts.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/spacings.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/grid.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/buttons.css">
</head>

<body>
<div id="spinner">
  <div class="lds-ring"><div></div><div></div><div></div><div></div></div>
</div>
  <div class="background-ctn">
    <div class="login-box box-shadow">
      <div class="greetings-ctn relative flex flex-column align-start justify-center">
        <a href="${pageContext.request.contextPath}/home">
          <img class="absolute top-12 title" src="${pageContext.request.contextPath}/assets/images/konnect-logo.svg" alt="logo" width="48">
        </a>
        <p class="big-title bolder">Seja bem-vindo ao Konnect</p>
        <p class="body mt-12 primary">Crie conexões reais</p>

        <div class="create-account absolute flex flex-row">
          <p>Não tem uma conta?</p>
          <a class="text-btn primary" href="${pageContext.request.contextPath}/register">Criar conta</a>
        </div>
      </div>
  
      <div class="credentials-ctn relative flex flex-column align-end justify-start">
        <div class="credentials flex flex-column w-full align-star pt-36">
          <div class="forms flex-column align-start justify-center">
            <label for="username-input">Username</label>
            <input id="username-input" type="text" placeholder="kool_username">
          </div>
          
          <div class="forms flex-column align-start justify-center">
            <label for="password-input">Senha</label>
            <input id="password-input" type="password" placeholder="******">
          </div>
          
          <div class="reset-password">
            <div class="red sm-body p-0 m-0 w-full" id="error-message"></div>
            <p class="text-btn small-title dark-gray mt-6">Esqueceu a senha?</p>
          </div>
        </div>

        <div id="login-button" class="btn primary-btn login ">Entrar</div>
      </div>
    </div>
  </div>
</body>
<script type="module" src="${pageContext.request.contextPath}/views/login/login.js"></script>
</html>