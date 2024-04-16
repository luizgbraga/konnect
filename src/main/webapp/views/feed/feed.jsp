<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Konnect - Feed</title>

</head>

<body>
<label for="content-input">Content</label>
<input id="content-input" type="text" placeholder="content">
<div id="post-button" class="btn primary-btn login">Post</div>
</body>
<script type="module" src="${pageContext.request.contextPath}/views/feed/feed.js"></script>
</html>