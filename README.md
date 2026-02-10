# Task Manager
REST API для управления задачами с JWT-аутентификацией, email-уведомлениями и хранением данных в PostgreSQL.  
Разворачивается через Docker + Docker Compose.

---

## Стек технологий
- Java 25
- Spring Boot
- Spring Security + JWT
- Spring Data JPA
- PostgreSQL
- Docker / Docker Compose
- Maven

---

## Конфигурация и профили
- Используются **Spring Profiles** (`application.yml` + `application-dima.yml`)
- Секреты, пароли и настройки почты через ```.env```

### Переменные окружения (.env)
```env
POSTGRES_PASSWORD=ваш_пароль_для_postgres
JWT_SECRET=ваш_jwt_секрет
MAIL_HOST=smtp.gmail.com
MAIL_PORT=587
MAIL_USERNAME=ваш_email
MAIL_PASSWORD=ваш_пароль
```

### Запуск проекта
```
docker-compose up --build
```
Приложение доступно по адресу: http://localhost:8085

После запуска приложения через Docker, Swagger UI доступен по адресу:

[http://localhost:8085/swagger-ui.html](http://localhost:8085/swagger-ui.html)

В Swagger вы можете увидеть все доступные эндпоинты, их описание, параметры запросов и пример ответа.

---

### База данных
- DB: PostgreSQL 15
- Internal port: 5432
- External port: 5436
- Таблицы создаются автоматически через JPA (```ddl-auto: update```)

### JWT
- Токен жизни: 24 часа
- Секрет хранится в ```.env```
- Используется для аутентификации и авторизации эндпоинтов

### Email
- SMTP через переменные окружения
- Настройка через ```MailConfig```
 
---

## Структура проекта

├── src
│   ├── main
│   │   ├── java/com/example/TaskManager
│   │   └── resources
├── docker-compose.yml
├── Dockerfile
├── .env
├── .gitignore
├── README.md
└── pom.xml

---

## Эндпоинты API

### 1. Task endpoints
<img width="1622" height="330" alt="image" src="https://github.com/user-attachments/assets/db352f48-72af-4a87-bd19-141fb7b68875" />

### 2. Auth endpoints
<img width="1624" height="310" alt="image" src="https://github.com/user-attachments/assets/a0d829a8-1998-49db-a700-9fa509d9ba30" />



---

## Автор

**Dima Polegenkii**  
Email: dmpoleg2006@gmail.com  
GitHub: [https://github.com/magma5shot123](https://github.com/magma5shot123)

---
