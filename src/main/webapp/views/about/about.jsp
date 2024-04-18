<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Konnect - About</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/global.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/accounts.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/input.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/fonts.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/spacings.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/grid.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/buttons.css">
</head>

<body>
  <div class="flex flex-column align-center justify-center w-vw h-vh bg-white">
    <div class="flex flex-column align-center justify-center gap-54 w-half h-400">
      <div class="flex flex-row align-center justify-center">
        <a href="${pageContext.request.contextPath}/home">
          <img src="${pageContext.request.contextPath}/assets/images/konnect-logo.svg" alt="logo" width="96">
        </a>
      </div>

      <div class="flex flex-column align-center gap-24">
        <div class="xl-title heavy black text-center nunito">A rede social verdadeira</div>
        <div class="body dark-gray nunito text-center">Em um mundo onde a conexão é fundamental, apresentamos uma nova plataforma que redefine o significado de amizade e interação online</div>
        <div class="body dark-gray nunito text-center">Imagine uma rede onde não existem exclusões, onde cada usuário é uma peça importante do grande quebra-cabeça social. Sem exclusões, sem grupos seletivos - apenas uma teia interligada de conexões genuínas e completas</div>
        <div class="body dark-gray nunito text-center">Ao mergulhar em nossa plataforma, você descobrirá uma infinidade de perspectivas, ideias e histórias. Desde os momentos compartilhados com amigos mais próximos até as descobertas emocionantes através de conexões mais distantes, cada interação é uma oportunidade para expandir sua rede e sua mente</div>
      </div>

      <div class="flex flex-row align-center w-ull gap-6">
        <a class="btn secondary-btn" href="home">Voltar</a>
        <a href="login" class="button">
          <div id="register-button" class="btn primary-btn">Entrar</div>
        </a>
      </div>
    </div>
  </div>
</body>