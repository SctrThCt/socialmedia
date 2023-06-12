RESTful API для социальной медиа платформы, позволяющая пользователям регистрироваться, входить в систему, создавать посты, переписываться, подписываться на других пользователей и получать свою ленту активности.

Предварительные настройки в application.yaml: 
1. поле jwt.expiry указать желаемое время жизни токена в секундах (сейчас установлен на один сутки для тестов)
2. поле spring.servlet.multipart.location указать место на диске, куда будут сохранятся фото пользователей в постах, если указанной директории не существует, она будет создана автоматически
3. в поле datasource указать адрес где базы данных и учетные данные для подключения, где должны хранится данные (ВНИМАНИЕ в тестовом режиме проект стирает данные в бд и накатывает свои)
4. по желанию можно сгенерировать свой публичный и приватный ключ для шифрования если установлен GitBash то можно выполнить команды:

`openssl genpkey -out jwt.pem -algorithm RSA -pkeyopt rsa_keygen_bits:2048` - приватный ключ в формате PKCS8 для подписи

`openssl rsa -in jwt.pem -pubout -out jwt.pub` - публичный ключ для декодирования

сгенерированные ключи нужно поместить в папку resources/keys


Запуск: 
1. склонировать репозиторий на жесткий диск, 
2. открыть проект в IDEA, 
3. запустить проект через SocialMediaApplication,
4. опционально перед запуском возможно запустить тесты или через файл AbstractControllerTest или через Maven Test Plugin.

ИЛИ

1. Сколнировать репозиторий
2. В папке с репозиторием запустить cmd и выполнить mvn spring-boot:run (в системе должна быть прописана OpenJDK 19.0.2 или аналог)

После запуска по адресу http://localhost:8080/swagger-ui.html будет доступна Swagger-документация.
Авторизация в SwaggerUI: в поле Select a definition выбрать JWT Token, обратится к эндпоинту /token для генерации токена, учетны данные для тестовых пользователей указаны в описании.
После получения токена выставить select a definition на REST API и использовать полученный токе в для авторизации в запросах
