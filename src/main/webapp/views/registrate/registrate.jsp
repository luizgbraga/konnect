<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Konnect - Registrate</title>
  <link rel="stylesheet" href="registrate.css">
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
        <p class="absolute top-12 title">KONNECT</p>
        <p class="big-title">Criar uma conta do Konnect</p>
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
        </div>

        <div class="btn-ctn flex flex-row align-end">
          <div id="login-button" class="btn secondary-btn back">Voltar</div>
  
          <div id="login-button" class="btn primary-btn registrate">Cadastrar</div>
        </div>
      </div>
    </div>
  </div>
</body>
<script src="login.js"></script>
</html>