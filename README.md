# 🏨 SmartLodge — экзаменационный проект (Java Spring)

**SmartLodge** — это демонстрационный микросервисный проект на базе **Spring Boot 3.3.4**, 
реализующий систему бронирования отелей с аутентификацией, маршрутизацией через Gateway 
и автоматической документацией API (Swagger).  
Проект разворачивается полностью в Docker и демонстрирует базовые принципы микросервисной архитектуры.

---

## ⚙️ Архитектура проекта

| Сервис | Порт | Назначение |
|---------|------|-------------|
| **discovery-service** | `8761` | Реестр сервисов (Eureka Server) |
| **gateway-service** | `8080` | Точка входа и маршрутизация |
| **hotel-service** | `8082` | Управление отелями и номерами |
| **booking-service** | `8083` | Аутентификация и бронирование |

---

## 🚀 Запуск проекта

### 🧱 1. Сборка проекта
```bash
mvn clean package -DskipTests
```

### 🐳 2. Запуск через Docker Compose
```bash
docker compose build
docker compose up
```

После успешного запуска можно открыть:

- **Eureka Dashboard:** [http://localhost:8761](http://localhost:8761)  
  → все сервисы должны иметь статус `UP`  
- **Gateway Health Check:** [http://localhost:8080/actuator/health](http://localhost:8080/actuator/health)

---

## 📚 Swagger (OpenAPI)

Swagger подключён через **springdoc-openapi-starter-webmvc-ui (v2.6.0)**  
и автоматически активируется при запуске сервисов.

| Сервис | Swagger UI URL | OpenAPI JSON |
|---------|----------------|---------------|
| **Hotel Service** | [http://localhost:8082/swagger-ui/index.html](http://localhost:8082/swagger-ui/index.html) | [http://localhost:8082/v3/api-docs](http://localhost:8082/v3/api-docs) |
| **Booking Service** | [http://localhost:8083/swagger-ui/index.html](http://localhost:8083/swagger-ui/index.html) | [http://localhost:8083/v3/api-docs](http://localhost:8083/v3/api-docs) |

Swagger UI позволяет выполнять тестовые запросы напрямую к API.

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

## 🧠 Используемые технологии

- **Spring Boot 3.3.4**
- **Spring Cloud 2023.0.3 (Eureka, Gateway)**
- **Spring Data JPA + H2 Database**
- **Spring Security + JWT (jjwt 0.11.5)**
- **Spring Validation (jakarta.validation)**
- **Springdoc OpenAPI (Swagger UI)**
- **Docker Compose**
- **Java 21**

---

## 🧾 Особенности первой версии проекта

| Категория | Особенность | Описание |
|------------|--------------|-----------|
| **Spring Cloud BOM** | Централизованное управление зависимостями | Обеспечивает совместимость Eureka и Gateway |
| **JPA + H2** | Встроенная база данных | Упрощает локальный запуск и тестирование |
| **Validation** | Поддержка аннотаций `@Valid`, `@NotBlank`, `@FutureOrPresent` | Повышает надёжность входных данных |
| **JWT** | Реализована аутентификация с использованием токенов | Безопасный доступ к API |
| **Swagger (Springdoc)** | Автоматическая документация API | Упрощает тестирование и отладку |
| **Fat-JAR** | Настроен `spring-boot-maven-plugin` | Корректное создание исполняемых JAR-файлов |

---

## 🧩 Проверка кластера

1. Убедитесь, что контейнеры запущены:  
   ```bash
   docker ps
   ```
2. Проверьте **Eureka**: [http://localhost:8761](http://localhost:8761)  
   — все сервисы должны иметь статус **UP**  
3. Откройте Swagger UI для Booking и Hotel  
4. Выполните тестовый запрос для регистрации или получения списка отелей

---

## 👨‍💻 Автор

**Тимур Хананиев**  
Проект подготовлен в рамках экзаменационного задания по направлению **Java Spring (MEPHI)**  
с фокусом на микросервисную архитектуру и DevOps-подход к сборке и развертыванию.
