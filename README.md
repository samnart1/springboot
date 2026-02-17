# Spring Boot Projects

A curated collection of Spring Boot projects I've built while exploring different aspects of the framework - from microservices to security, REST APIs, and full-stack integrations.

## Projects

| Projects            | Description                                              |
|:--------------------|:---------------------------------------------------------|
|`auto`               | A micro job continuity service                           |
|`ecommerce`          | A complete ecommerce backend                             |
|`patient-management` | A micro-service of a healthcare record management system |
|`quizapp`            | A backend for a quiz application                         |
|`securitydemo`       | A demo for implementing Spring Security                  |
|`social`             | A mini social media API                                  |

## Purpose
This repository serves as my hands-on Spring Boot lab, where I experiment with:
- REST API design
- Microservices communication
- Security implementation
- Full-stack integration
- Protobuf + gRPC configs
- CI/CD with Docker

## Tech Staack
- Java 17+
- Spring Boot 3+
- Docker
- Maven


<!----
⚙️ Core Spring Boot (Projects 1–20)
Focus: Basics, REST, JPA, Validation, Security


5. Pagination and Filtering API

6. Weather Tracker – API client + scheduled updates

7. Blog Platform Backend – Markdown parsing + CRUD

8. Email Notification Service – Spring Mail + Templates

9. SMS Verification Service – Twilio + OTP logic

10. Event Logger – Log4j + log rotation

11. REST API Versioning – Header vs URI-based

12. JWT Authentication & Refresh Tokens

13. Rate Limiting Middleware – Spring Filter + Bucket4j

14. Multi-language API Responses – i18n + Messages

15. Audit Trail System – Spring AOP + DB log

16. Role-Based Access Control API

17. Global Exception Handler + Custom Errors

18. API Documentation with Swagger/OpenAPI

19. Application Metrics with Micrometer

20. Spring Profiles for Multi-Env Configs

🧩 Microservices (Projects 21–40)
Focus: Services, Communication, Config, Discovery

21. Microservice Starter Project (Boilerplate)

24. Inventory Microservice

25. Order Microservice with Feign Client

26. Notification Microservice (email + Kafka)

27. API Gateway using Spring Cloud Gateway

28. Circuit Breaker with Resilience4J

29. Dynamic Routing Gateway + Path Rewrite

30. Rate Limiting Gateway with Redis

31. Configurable Feature Flags

32. Load-balanced Communication (Ribbon)

33. Microservices Tracing with Sleuth + Zipkin

34. Service Health Dashboard with Actuator

35. Distributed Logging with ELK Stack

36. Service Mesh Demo (Istio)

37. gRPC Communication between Services

38. GraphQL API Gateway (Spring Boot + GraphQL)

39. Token Relay across Services (OAuth2)

40. Multi-tenant Microservices with Schema Isolation

🛠️ Data & Messaging (Projects 41–60)
Focus: Databases, Messaging Systems

Spring Boot + PostgreSQL Integration

Spring Boot + MongoDB Example

Relational to Document Model Migrator

Event Sourcing System

Kafka Producer/Consumer with Spring

RabbitMQ Event Bus

Dead Letter Queue Processor

Outbox Pattern with Debezium CDC

Redis Caching for Product Listings

Mongo Aggregation Pipelines + Spring Data

Redis Streams for Real-Time Feeds

Database Sharding Demo

Connection Pooling and Monitoring (HikariCP)

Batch Processing App with Spring Batch

Job Scheduler with Quartz

Geo-Search with MongoDB + Spring Data

Write-Heavy Queue-Backed Service

Transactional Messaging with Kafka

Custom Kafka Serializers + Avro

ETL Pipeline: Read-Transform-Store Data

📈 Observability & Monitoring (Projects 61–75)
Focus: Metrics, Logs, Tracing

Custom Micrometer Metrics

Prometheus Metrics Exporter

Grafana Dashboards for Spring Boot App

Structured JSON Logging

Distributed Tracing with Jaeger

OpenTelemetry Integration with gRPC & REST

Log Correlation Between Microservices

Alerting with Prometheus + Alertmanager

Request/Response Logging Middleware

Service-Level Objectives + Grafana Alerts

Dynamic Log Level Switching via Actuator

Kibana Log Search with Custom Tags

Grafana Loki for Log Aggregation

Trace Sampler Config + Analysis

Business Metrics Dashboard

🧪 Security & Auth (Projects 76–85)
Focus: Secure APIs, OAuth2, Keycloak, JWT, etc.

JWT Auth System with Refresh Token Rotation

OAuth2 Authorization Code Flow (Spring Security)

SSO Integration with Keycloak

Role Hierarchies + Permissions Evaluation

Custom OAuth2 Resource Server

API Key Auth via Filter Chain

OpenID Connect Integration (Google Auth)

Encrypted Properties with Jasypt

Multi-Factor Authentication (TOTP + Email)

Session Management + Concurrent Login Prevention

🔌 Advanced Features & Real-time (Projects 86–95)
Focus: WebSockets, SSE, File Handling, etc.

WebSocket Chat Application

Server-Sent Events Streamer

Real-Time Notifications with STOMP over WebSockets

Upload Large Files with Chunking

Async Email Sender with Retry Mechanism

Rate Limiter with Redis and Lua Scripts

GeoJSON API with Map Integration

Server-side Rendering with Thymeleaf + Spring

PDF Generator API (Invoices)

Image Resizer + CDN Proxy Service

🚀 Deployment, DevOps & GitOps (Projects 96–100)
Focus: Docker, K8s, GitOps

Dockerize Monolith Spring Boot App

Docker Compose for Local Microservices

Helm Charts for Spring Boot Microservice

CI/CD with GitHub Actions + Docker + Kubernetes

GitOps with ArgoCD for Spring Boot App







































🔥 1. Real-Time Collaborative Code Editor
Tech: TypeScript (frontend), Go/Java (backend), Redis (pub-sub), WebSocket/gRPC, PostgreSQL, Docker, Kubernetes, Prometheus, Grafana
Real-time editing using WebSockets/gRPC
Sync multiple clients with OT (Operational Transformation)
Redis for real-time collaboration layer
Postgres to store user/code history
Kubernetes to deploy scalable collaboration pods
Full monitoring and alerting with Prometheus & Grafana


🧠 2. AI-Powered News Aggregator
Tech: Go + Spring Boot microservices, Kafka, PostgreSQL, Redis, GraphQL, Docker, Kubernetes, Grafana, Prometheus
Crawls news via RSS feeds or APIs
Kafka for event stream (new articles, trends)
Redis for caching trending articles
GraphQL API to fetch filtered, AI-tagged content
Prometheus for tracking crawl success rates
Grafana dashboards for ingestion stats


📦 3. Microservices eCommerce Platform
Tech: Java (Spring Boot), Go, Kafka, RabbitMQ, PostgreSQL, Redis, GraphQL, gRPC, Docker, Kubernetes, Prometheus, Grafana
Inventory, Orders, Payments, and Auth as services
Kafka for event sourcing (e.g. order placed → inventory reduced
gRPC between internal services
GraphQL gateway for frontend
Prometheus + Grafana to monitor service health
Redis for caching products/session data


💬 4. WhatsApp-like Messaging Platform
Tech: Go/Java backend, WebSockets/gRPC, Redis (message queue), PostgreSQL, Docker, Prometheus, Grafana
Real-time messaging with delivery status
Redis for message buffering and pub-sub
PostgreSQL for chat history and user data
gRPC for internal messaging coordination
Prometheus for tracking message delays/failures


📈 5. IoT Sensor Data Pipeline
Tech: Go + Spring Boot, Kafka, gRPC, PostgreSQL, Prometheus, Grafana, Docker, Kubernetes
Simulate 1000+ IoT devices sending sensor data
Kafka for ingestion pipeline
PostgreSQL for storage
Prometheus for metrics on data delay, error rate
Grafana dashboards for real-time data visualization


🗂️ 6. Open Data Portal Platform
Tech: Spring Boot + GraphQL, PostgreSQL, Redis, gRPC, Grafana, Prometheus, Docker
Import datasets from public APIs
Categorize and tag data, expose via GraphQL
Redis cache for quick previews
Monitor dataset syncs via Prometheus
Build a powerful admin dashboard with Grafana


📅 7. Event-Driven Job Application Tracker
Tech: Spring Boot, Kafka, PostgreSQL, Redis, GraphQL, Prometheus, Docker
Add job apps → triggers event → updates user dashboard
Kafka tracks application stages (applied, interview, offer)
GraphQL for advanced querying (e.g. companies by location)
Redis cache for most-recent jobs
Prometheus to monitor event flows and bottlenecks


🛡️ 8. Secure Multi-Tenant SaaS Authentication Platform
Tech: Java (Spring Security), Redis, PostgreSQL, Keycloak or custom OAuth2, Prometheus, Grafana, Docker
Support for multiple organizations (multi-tenancy)
Role-based access control
Redis for session/token caching
PostgreSQL for user/org data
Optional integration with Keycloak


📧 9. Distributed Email Campaign System
Tech: Go + Spring Boot, RabbitMQ, Redis, PostgreSQL, Grafana, Prometheus, Docker, Kubernetes
Schedule and send large batches of emails
RabbitMQ for task queues
Redis to store deduplication tokens & retry logic
Grafana for email delivery success/fail insights
Prometheus to monitor processing times


🎯 10. Gamified Coding Challenges Platform
Tech: Go/Java backend, PostgreSQL, Redis, gRPC, GraphQL, Prometheus, Grafana, Docker, Kubernetes
Users solve coding puzzles (hosted in Docker sandboxes)
Leaderboards, time tracking, hints
Redis to store real-time score updates
GraphQL API for frontend queries
Prometheus for system health + sandbox usage





# some webcomponent projects
1. 🔹 UI/UX Components
Accessible Modal Dialog
Fully accessible (keyboard trap, ARIA roles)
Easily stylable
Custom Dropdown with Search
Searchable, keyboard-navigable, ARIA-compliant
Image Carousel / Slider
Touch support, lazy loading, responsive
Snackbar / Toast Notifications
Auto-dismiss, queue support
Progress Bar / Stepper
Useful for forms, with validation hooks


2. 🔸 Form Enhancements
Tag Input Component
Add/remove tags, autocomplete integration
Credit Card Input
Auto-formatting, validation, logos
Date Range Picker
Predefined ranges, mobile-friendly
Password Strength Meter
Real-time feedback, animation
Form Validation Wrapper
Works with native forms, supports async rules


3.  🧠 Smart Components
AI Chat Bubble Component
Connect to an API or local LLM
Smart Autocomplete
Async suggestions, debouncing, caching
Content Editable WYSIWYG
Markdown output, plugin-ready
Drag & Drop Sortable List
Keyboard + mouse support
Infinite Scroller
Works with any REST API, loading indicators


4.  🔌 Utility / Framework-agnostic
Theme Switcher (Dark/Light/Auto)
System preference aware, persistent
Copy to Clipboard Button
With feedback tooltip
QR Code Generator
Input -> QR image output
Intersection Observer Wrapper
Lazy loading or animation triggers
Web Share API Button
Fallback for unsupported browsers


5.  🎁 Bonus Niche Ideas
Live Markdown Previewer
Comment Box with Threading
Syntax Highlighting Code Block
Currency Converter Widget
Web Component for OAuth Login Buttons
Google, GitHub, Twitter integrations

---->
