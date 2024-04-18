<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Konnect - Feed</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/feed/feed.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/global.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/accounts.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/background.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/buttons.css">  
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/fonts.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/grid.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/input.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/spacings.css">
</head>

<body>
<!-- <label for="content-input">Content</label>
<input id="content-input" type="text" placeholder="content">
<div id="post-button" class="btn primary-btn login">Post</div> -->
<div class="absolute right-18 top-36 w-250">
    <div class="flex gap-6">
        <p class="mb-20">Notificações</p>
        <img class="dark-gray" src="${pageContext.request.contextPath}/assets/images/bell.png" alt="logo" width="20" height="20">
    </div>
    <div id="notifications-container" class="flex flex-column gap-6 w-250">

    </div>
</div>
<div class="absolute left-18 top-36 w-250">
    <div class="flex gap-6">
        <p class="mb-20">Grupos</p>
        <img class="dark-gray" src="${pageContext.request.contextPath}/assets/images/group.png" alt="logo" width="26" height="26">
    </div>
    <a id="back-button" class="btn secondary-btn">Voltar para o feed</a>
    <div id="groups-container" class="flex flex-column gap-6 w-250 mt-20">

    </div>
</div>
<div class="absolute bottom-30 left-18">
    <div class="flex gap-6">
        <img class="dark-gray" src="${pageContext.request.contextPath}/assets/images/logout.png" alt="logo" width="20" height="20">
        <p id="logout" class="pointer">Logout</p>
    </div>
</div>
  <div class="flex flex-column align-center w-vw h-vh bg-white pl-24 pr-24 gap-24">
    <!-- Header -->
    <div class="flex w-full max-w-660 pt-12 pb-18 " id="header">
      <div class="flex flex-row align-center justify-between w-full">
          <a href="${pageContext.request.contextPath}/home">
          <img class="dark-gray" src="${pageContext.request.contextPath}/assets/images/konnect-logo.svg" alt="logo" width="26">
        </a>
        <div class="flex flex-row align-center justify-center gap-12">
            <img class="dark-gray" src="${pageContext.request.contextPath}/assets/images/user.png" alt="logo" width="20">
            <p id="username"></p>
        </div>
      </div>
    </div>

      <!-- Degree selector -->
      <div class="flex w-full max-w-660 flex-row align-center justify-between gap-12">
          <div class="flex flex-row align-center gap-24">
              <div class="body dark-gray">Grau mínimo</div>
              <div class="flex flex-row align-center gap-6">
                  <img id="min-minus-btn" class="pointer" src="${pageContext.request.contextPath}/assets/images/konnect-minus.svg" alt="min-minus" width="24">
                  <div id="min-degree" class="dark-gray"><p>1</p></div>
                  <img id="min-plus-btn" class="pointer" src="${pageContext.request.contextPath}/assets/images/konnect-plus.svg" alt="min-plus" width="24">
              </div>
          </div>

          <div class="flex flex-row align-center gap-24">
              <div class="flex flex-row align-center gap-6">
                  <img id="max-minus-btn" class="pointer" src="${pageContext.request.contextPath}/assets/images/konnect-minus.svg" alt="max-minus" width="24">
                  <div id="max-degree" class="dark-gray"><p>1</p></div>
                  <img id="max-plus-btn" class="pointer" src="${pageContext.request.contextPath}/assets/images/konnect-plus.svg" alt="max-plus" width="24">
              </div>
              <div class="body dark-gray">Grau máximo</div>
          </div>    </div>
      
    <!-- Search bar -->
    <div class="flex w-full max-w-660 flex-column align-center" id="left-sidebar">
      <div class="forms flex-row align-center w-half relative">
        <input id="search-input" type="text" placeholder="Pesquise um usuário">
        <img class="absolute pointer gray right-12" src="${pageContext.request.contextPath}/assets/images/konnect-search-gray.svg" alt="search" width="20">
      </div>
    </div>

    <!-- Post new message -->
    <div class="flex w-full max-w-660 flex-column align-end gap-18" id="new-message-container">
      <div class="forms">
        <input class="flex w-full min-h-60 h-fit-content box-shadow" id="content-input" type="text" placeholder="O que você está pensando?">
      </div>
      <a id="post-button" class="btn primary-btn">Postar</a>
    </div>

    <!-- Feed -->
    <div class="flex flex-column gap-18 w-full max-w-660" id="feed-container">
    </div>
      <div class="flex flex-column gap-18 w-full max-w-660" id="users-container">
      </div>

  </div>

</body>
<script type="module" src="${pageContext.request.contextPath}/views/feed/feed.js"></script>
<script type="module" src="${pageContext.request.contextPath}/views/feed/connection.js"></script>
<script type="module" src="${pageContext.request.contextPath}/views/feed/notification.js"></script>
<script type="module" src="${pageContext.request.contextPath}/views/feed/kn.js"></script>
</html>