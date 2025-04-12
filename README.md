# 📰 News Aggregator API

A Spring Boot-based RESTful API that fetches news articles based on user preferences, with support for JWT authentication, role-based access (User/Admin), Redis caching for optimized performance, and Swagger for API documentation.

---

## 🚀 Features

- ✅ User registration & secure login using JWT
- ✅ Role-based access control (`USER`, `ADMIN`)
- ✅ Admin-specific endpoints (e.g., view all users)
- ✅ Users can set preferences from predefined news categories
- ✅ News fetched from [GNews API](https://gnews.io/)
- ✅ Redis caching to minimize duplicate API calls
- ✅ Swagger integrated for easy testing & documentation

---

## 🛠️ Tech Stack

- **Backend**: Spring Boot, Spring Security, JPA (Hibernate)
- **Database**: PostgreSQL
- **Caching**: Redis
- **API Docs**: Swagger (Springdoc OpenAPI)
- **Build Tool**: Maven

---

## 🧠 Project Architecture

- **User**: Can register, log in, set preferences, and fetch personalized news
- **Admin**: Can do everything a user does + access admin-only features
- **NewsService**: Fetches news articles per category, caches them with Redis
- **RedisService**: Handles storing and retrieving cached data

---

## 🔐 Authentication & Roles

- Uses **JWT** for secure stateless authentication
- Roles are stored as `List<String>` (e.g., `["USER"]`, `["USER", "ADMIN"]`)
- Access rules:
    - `/admin/**` → only accessible by `ADMIN`
    - Other endpoints → accessible by `USER` or `ADMIN`

---

## 💾 Redis Caching

- Each news category is cached with a unique key
- Prevents repeated API calls for the same category
- TTL (time-to-live) ensures regular updates
- Boosts performance and reduces external API usage

---

## 📄 API Documentation

> Swagger UI available at:
http://localhost:8080/swagger-ui/index.html


Use it to test login, register, fetch news, and admin actions.

---

## 🧪 Sample API Usage

### 📝 Register

POST /register
Content-Type: application/json

```json
{
  "email": "d@example.com",
  "password": "d@123",
  "categoryIds": [1,2]
}
```
🔑 Login

POST /login
Content-Type: application/json

```json

{
  "email": "user@example.com",
  "password": "password123"
}
```
Response will include a JWT token:

```json
{
"token": "eyJhbGciOiJIUzI1NiIsInR..."
}
```
Use it in requests as:

```http
Authorization: Bearer <your_token_here>

```

## 👑 Admin Endpoints
- GET /admin/users → View all registered users (only id and email shown)

- Admin can access all regular user endpoints as well

## 👨‍💻 Author

### Developed with ❤️ by Akash Kanojiya
