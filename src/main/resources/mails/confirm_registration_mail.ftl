<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Подтверждение регистрации</title>
</head>
<body>
<h1>Подтверждение регистрации</h1>
<h2>Добрый день!</h2>
<p>
    Для подтверждения регистрации используйте код: <strong>${code}</strong>
</p>
<p>
    Или перейдите по <a href="http://localhost:8080/api/auth/confirm?code=${code}">ссылке</a>.
</p>
</body>
</html>