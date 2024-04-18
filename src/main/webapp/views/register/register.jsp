<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Konnect - register</title>
  <link rel="stylesheet" href="register.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/accounts.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/global.css">
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
        <p class="big-title bolder">Crie sua conta</p>
        <p class="body mt-12 primary">Acompanhe os posts somente de quem interessa. Sem mais.</p>

        <div class="create-account absolute flex flex-row">
          <p>Ficou interessado?</p>
          <a class="text-btn primary" href="${pageContext.request.contextPath}/register">Saiba mais sobre nós</a>
        </div>
      </div>
  
      <div class="credentials-ctn relative flex flex-column align-end justify-center">
        <div class="credentials flex flex-column">
          <div class="forms flex-column align-start justify-center">
            <label for="username-input">Username</label>
            <input id="username-input" type="text" placeholder="Escolha um nome">
          </div>

          <div class="forms flex-column align-start justify-center">
            <label for="password-input">Senha</label>
            <input id="password-input" type="password" placeholder="Insira pelo menos 6 caracteres">
          </div>
        </div>

        <div class="btn-ctn flex flex-row align-end">
          <a class="btn secondary-btn" href="${pageContext.request.contextPath}/login">Voltar</a>
          <a href="login" class="button">
            <div id="register-button" class="btn primary-btn register">Cadastrar</div>
          </a>
        </div>
      </div>
    </div>
  </div>
</body>

<script type="module" src="${pageContext.request.contextPath}/views/register/register.js"></script>
</html>