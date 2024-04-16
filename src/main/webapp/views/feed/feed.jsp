<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Konnect - Feed</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/feed/feed.css">
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
  <div class="flex flex-column align-center w-vw h-vh bg-white pl-24 pr-24 gap-24">
    <!-- Header -->
    <div class="flex w-full max-w-660 pt-12 pb-12 " id="header">
      <div class="flex flex-row align-center justify-between w-full">
        <a href="${pageContext.request.contextPath}/home">
          <img class="dark-gray" src="${pageContext.request.contextPath}/assets/images/konnect-logo.svg" alt="logo" width="36">
        </a>
        <div class="flex flex-row align-center justify-center gap-12">
          Diogo Laurindo
          <img class="dark-gray pointer" src="${pageContext.request.contextPath}/assets/images/konnect-options.svg" alt="options" width="24">
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
        <input id="username-search" type="text" placeholder="BragaLindo">
        <img class="absolute pointer gray right-12" src="${pageContext.request.contextPath}/assets/images/konnect-search-gray.svg" alt="search" width="24">
      </div>
    </div>

    <!-- Post new message -->
    <div class="flex w-full max-w-660 flex-column align-end gap-18">
      <div class="forms">
        <input class="flex w-full min-h-100 h-fit-content box-shadow" id="content-input" type="text" placeholder="Escreva sua mensagem aqui">
      </div>
      <a href="home.jsp" class="btn primary-btn">Postar</a>
    </div>

    <!-- Feed -->
    <div class="flex w-full max-w-660" id="feed-container">
      <div class="">
        
      </div>
    </div>

  </div>

</body>
<script type="module" src="${pageContext.request.contextPath}/views/feed/feed.js"></script>
</html>