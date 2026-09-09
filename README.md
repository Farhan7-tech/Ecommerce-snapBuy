<div align="center">

# 🛍️ SnapBuy

**A full-stack e-commerce platform with JWT auth, product catalog, cart, and Stripe checkout.**

[![Java](https://img.shields.io/badge/Java-17-orange)](#)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4-brightgreen)](#)
[![React](https://img.shields.io/badge/React-18-61DAFB)](#)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-336791)](#)
[![Stripe](https://img.shields.io/badge/Payments-Stripe-635BFF)](#)

</div>

---

## 📖 About

SnapBuy is a two-part e-commerce application: a **Spring Boot REST API** (`sb-ecommerce`) backing a **React + Redux Toolkit** storefront (`ecom-frontend`). It covers the core shopping flow end to end — browse products, manage a cart, save addresses, and pay via Stripe.

<details>
<summary><strong>🖼️ Click to expand: what's implemented</strong></summary>

- User signup/login with JWT (cookie-based) authentication and role-based access
- Category and product catalog with search, filtering, and pagination
- Shopping cart: add, update quantity, remove
- Saved shipping addresses per user
- Order placement with Stripe payment intent flow
- Admin-only endpoints for managing categories and products

</details>

## 🧰 Tech Stack

| Layer | Technology |
|---|---|
| Backend | Java 17, Spring Boot 3.4, Spring Security, Spring Data JPA |
| Auth | JWT (jjwt), HTTP-only cookies |
| Database | PostgreSQL |
| Payments | Stripe (`stripe-java`) |
| Frontend | React 18, Vite, Redux Toolkit, React Router 7 |
| UI | Tailwind CSS, MUI, Headless UI, Swiper |
| Forms/UX | React Hook Form, React Hot Toast |

## 🗂️ Project Structure

```
Ecommerce-snapBuy/
├── sb-ecommerce/       # Spring Boot backend
│   └── src/main/java/com/ecommerce/sbEcommerce/
│       ├── controller/ # AuthController, ProductController, CartController, OrderController, ...
│       └── model/      # User, Product, Category, Cart, Order, Payment, Address, ...
└── ecom-frontend/      # React frontend
    └── src/
        ├── components/ # auth, products, cart, checkout, home, shared
        ├── store/       # Redux actions & reducers
        └── api/         # Axios API client
```

## 🔌 API Overview

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/auth/signup` | Register a new user |
| `POST` | `/api/auth/signin` | Log in, receive JWT cookie |
| `GET`  | `/api/auth/user` | Get current authenticated user |
| `GET`  | `/api/public/categories` | List product categories |
| `GET`  | `/api/public/products` | List/search/filter products |
| `POST` | `/api/carts/products/{productId}/quantity/{quantity}` | Add item to cart |
| `GET`  | `/api/carts/users/cart` | Get current user's cart |
| `POST` | `/api/addresses` | Save a shipping address |
| `POST` | `/api/order/stripe-client-secret` | Create a Stripe payment intent |
| `POST` | `/api/order/users/payments/{paymentMethod}` | Place an order |

## 🚀 Getting Started

### Prerequisites
- Java 17+ and Maven
- Node.js and npm
- PostgreSQL running locally
- A Stripe account (for a test secret key)

### 1. Backend

```bash
cd sb-ecommerce
```

Copy the example config and fill in your own values — `application.properties` is git-ignored, so your secrets stay local:

```bash
cp src/main/resources/application.properties.example src/main/resources/application.properties
```

Then edit `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/ecommerce
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password
spring.app.jwtSecret=your_jwt_secret
stripe.secret.key=your_stripe_secret_key
```

Then run:

```bash
./mvnw spring-boot:run
```

The API starts on `http://localhost:8080`.

### 2. Frontend

```bash
cd ecom-frontend
npm install
npm run dev
```

The app starts on `http://localhost:5173` (default Vite port).

> ⚠️ **Security note:** `application.properties` was previously committed with a live-looking database password and Stripe secret key. It has since been removed from tracking and added to `.gitignore` — use `application.properties.example` as your template. If you ever pushed real secrets, rotate them, since they remain visible in the repo's git history.

## 🤝 Contributing

Issues and pull requests are welcome — fork the repo, create a feature branch, and open a PR.

## 📄 License

No license specified yet.
