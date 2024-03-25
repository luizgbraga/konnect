<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Konnect</title>
  <link rel="stylesheet" href="home.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/background.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/accounts.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/input.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/fonts.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/spacings.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/grid.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/buttons.css">
</head>

<body>
  <div class="bg-white flex relative">
    <div class="w-header flex flex-row justify-between align-center absolute top-0 pt-6 pb-6 pl-144 pr-144">
      <img class="logo" src="${pageContext.request.contextPath}/assets/images/Konnect-logo.svg" alt="logo">
      <div class="flex flex-row justify-end">
        <a href="registrate" class="button">
          <div id="registrate-button" class="btn secondary-btn">Cadastrar</div>
        </a>
        <a href="login" class="button">
          <div id="login-button" class="btn primary-btn">Entrar</div>
        </a>
      </div>
    </div>

    <div class="flex flex-column pt-92">
      Home ctn
    </div>
  </div>
</body>
<script src="home.js"></script>
