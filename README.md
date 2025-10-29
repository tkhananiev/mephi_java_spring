# 🏨 SmartLodge — экзаменационный проект (MEPHI Java Spring)

**SmartLodge** — это демонстрационный микросервисный проект на базе **Spring Boot 3.3.4**, 
включающий систему бронирования отелей с аутентификацией и маршрутизацией через Gateway.  
Проект разворачивается полностью в Docker, включает **Eureka Discovery**, **Gateway**, 
и два бизнес-сервиса — **Booking** и **Hotel**, каждый со своей Swagger-документацией.

---

## ⚙️ Архитектура

| Сервис | Порт | Назначение |
|---------|------|-------------|
| **discovery-service** | `8761` | Реестр сервисов (Eureka Server) |
| **gateway-service** | `8080` | Точка входа и маршрутизация |
| **hotel-service** | `8082` | Управление отелями и номерами |
| **booking-service** | `8083` | Аутентификация и бронирование |

---

## 🚀 Как запустить проект

### 🧱 1. Сборка проекта
```bash
mvn clean package -DskipTests
```

### 🐳 2. Запуск всех сервисов через Docker Compose
```bash
docker compose build
docker compose up
```

### 🧩 3. Проверка статуса сервисов
Открой в браузере:

- **Eureka Dashboard:**  
  [http://localhost:8761](http://localhost:8761)  
  → Все сервисы должны иметь статус `UP`

- **Здоровье Gateway:**  
  [http://localhost:8080/actuator/health](http://localhost:8080/actuator/health)

---

## 📚 Swagger (OpenAPI)

Swagger подключён через библиотеку **springdoc-openapi-starter-webmvc-ui**  
(версия `2.6.0`) и автоматически активируется при запуске.

### 🔗 Доступ к документации
| Сервис | Swagger UI URL | OpenAPI JSON |
|---------|----------------|---------------|
| **Hotel Service** | [http://localhost:8082/swagger-ui/index.html](http://localhost:8082/swagger-ui/index.html) | [http://localhost:8082/v3/api-docs](http://localhost:8082/v3/api-docs) |
| **Booking Service** | [http://localhost:8083/swagger-ui/index.html](http://localhost:8083/swagger-ui/index.html) | [http://localhost:8083/v3/api-docs](http://localhost:8083/v3/api-docs) |

Swagger UI позволяет выполнять тестовые запросы напрямую к сервисам.

---

## 🔐 Пример API-запросов

### Регистрация пользователя
```bash
curl -X POST http://localhost:8080/booking-service/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"timur","password":"secret"}'
```

**Ожидаемый ответ:**
```json
{
  "id": 1,
  "username": "timur",
  "role": "USER",
  "token": "eyJhbGciOi..."
}
```

### Получение списка отелей
```bash
curl -X GET http://localhost:8080/hotel-service/api/hotels
```

---

## 🧠 Основные технологии

- **Spring Boot 3.3.4**  
- **Spring Cloud 2023.0.3 (Eureka, Gateway)**  
- **Spring Data JPA + H2 Database**  
- **Spring Security + JWT (jjwt 0.11.5)**  
- **Spring Validation (jakarta.validation)**  
- **Springdoc OpenAPI (Swagger UI)**  
- **Docker Compose**  
- **Java 21**

---

## 🧾 Внесённые изменения

| Категория | Изменение | Причина |
|------------|------------|----------|
| **Spring Cloud BOM** | Добавлен в родительский `pom.xml` | Управление версиями зависимостей |
| **JPA + H2** | Добавлены в `hotel-service` и `booking-service` | Работа с сущностями и локальная БД |
| **Validation** | Добавлен `spring-boot-starter-validation` | Поддержка аннотаций `@Valid`, `@NotBlank`, `@FutureOrPresent` |
| **JWT** | Добавлен пакет `io.jsonwebtoken` | Токен-аутентификация |
| **Swagger (Springdoc)** | Добавлен во все бизнес-сервисы | Автогенерация документации API |
| **Fat-JAR** | Настроен через `spring-boot-maven-plugin` | Исправлена ошибка `no main manifest attribute` |

---

## 🧩 Проверка кластера

1. Убедиться, что все контейнеры запущены:  
   ```bash
   docker ps
   ```
2. Проверить Eureka: [http://localhost:8761](http://localhost:8761)  
   → все сервисы должны быть в статусе **UP**  
3. Проверить доступ к Swagger UI для Booking и Hotel  
4. Выполнить тестовый запрос (регистрация / авторизация / список отелей)

---

## 👨‍💻 Автор

**Тимур Хананиев**  
Проект подготовлен в рамках экзаменационного задания по направлению **Java Spring (MEPHI)**  
с фокусом на микросервисную архитектуру и DevOps-подход к сборке и развертыванию.
