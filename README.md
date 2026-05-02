# Online-Akim-Bank

![Header](https://github.com/AKIM1001/bOnline-Akim-Bank/blob/main/assets/main-image.png)

### Online-Akim-Bank — is a large microservice backend system that handles a significant portion of the banking services offered by modern banks. The application includes functions for payments for various goods and services, transfers between accounts, currency conversion in accordance with the Central Bank of the Russian Federation exchange rate, issuing bank cards to individuals, distributing services to individuals, businesses, and companies, issuing loans, a fully functional admin panel, and much more. The bank's functionality is similar to that of a real bank. It is divided into 12 full-fledged microservices and 10 supporting microservices.

## Functional characteristics:

### 1. Autorization:
##### Triple authentication. Login via Keyclock for enhanced security. Afterwards, access the main menu where you can access your bank account and perform transactions. All subsequent actions are performed from this account. The user logs in through three services, ensuring security and reducing the load on one microservice.

### 2. Availability of user types:
##### Users are divided into three types: Individuals, Entrepreneurs, and Large Companies. This allows the bank to determine how much currency can be exchanged and received, what cashback is available, whether only individuals can apply for cards, what loans are available, what benefits are available, and so on. This is crucial because it's one of the bank's distinctive features.

### 3. Availability of a separate administrative service for convenient bank management:
##### The bank has a dedicated microservice for managing its microservices. This is necessary so that bank employees can monitor user activity, control transactions, and detect fraudulent activity. They can also detect any operational issues and contact the technical department if necessary.

### 4. Currency conversion:
##### I haven't used a separate microservice for this yet (I might do that in the future), but the account-service contains services for converting currencies in accordance with the current Central Bank of the Russian Federation exchange rate. It also includes conversion limits and other features.

### 5. Mechanics of bank cards:
##### Bank cards come in different types. There are debit cards, credit cards, and even children's cards, which are linked to the parent's main card. Parents can set the amount their children can spend. Stickers are also available. Modern banks issue stickers that act as separate cards and can be attached to phones or other items. Cards come with designs stored in Minio. Credit cards are also designed more realistically, with interest-free periods and other benefits.

### 6. Payments and transactions:
##### The bank has two separate microservices for payments and transfers between bank accounts. Different types of users can pay for different goods and services. Individuals can use bank cards. Cashback is available for users who make payments. Importantly, users receive cashback only for payments in rubles. Users can conduct transactions between accounts by sending money to one account, or individuals can send money to a card.

### 7. Loan and loan mechanics:
##### The loan service is probably one of the most technically unfinished in the bank. I'll definitely improve and optimize it in the future, but that's not the topic right now. The service allows users to submit loan applications. Applications are reviewed by bank employees. There are loan types, collateral, and much more.

### 8. Interest rate mechanics:
##### The bank takes interest rates in the same way as the exchange rate from the Central Bank of the Russian Federation website.

### 9. Notification mechanics for users:
##### Users receive notifications from the app via SMS and email. This allows users to be more informed about their banking activities and monitor their account activity.

### 10. General documentation for bank employees:
##### This feature allows bank employees to conveniently review their reports, track documents, sort them, and more.

## Technical characteristics:

### 1. Api-Gateway
##### It is a single point of entry (reverse proxy) that aggregates calls to backend microservices. In this project, I used OAuth 2.0 + OIDC as the primary authorization method between API Gateway and microservices.

### 2. Auth-Service:
##### Сentral microservice for authentication and session management. Operates as a thin wrapper over Keycloak, adding:

> Custom claims (KYC status, transaction limits, device fingerprint)
>
> Login audit (auth_audit_log table)
>
> Token blacklists (Redis + PostgreSQL)
>
> Device fingerprinting (session-to-device binding)
>
> Login attempt rate limiting (5 attempts/minute)
>
> Keycloak roles mapping → internal business roles

Tech stack: Spring Boot 3, WebClient, PostgreSQL, Redis, Kafka, Keycloak Admin API and other.

### 3. User-Service
##### Сore microservice for user management with **multi-role and multi-type architecture. Implements OOP-based hierarchy supporting three account types

> Individual — physical persons (passport, SNILS, KYC levels)
>
> Entrepreneur — sole proprietors / individual entrepreneurs (OGRNIP, tax regime, OKVED activity codes)
>
> Company — legal entities, large corporations (LLC, JSC, PJSC) with INN, KPP, OGRN, employee count

#### Key features:

#####  - Role-based access — ADMIN, USER
#####  - High level of abstraction of user types
#####  - Possibilities for bank employees to control user actions

Tech stack: Spring Boot , Spring Data JPA, PostgreSQL, Kafka, MapStruct, Specification API and other.

### 4. Account-Service
##### A microservice for managing bank accounts, currency transactions, conversion, and user limits. It uses Cassandra for limit storage (high write/read load) and PostgreSQL for master account data.

> - Checking Account — standard account (1% interest, 500k RUB/day withdrawal limit)
> - Multi-Currency Account  — balances in RUB, USD, EUR, CNY, KZT and more
> - The exchange rate is taken from the official website of the Central Bank of the Russian Federation.

#### Key features:

##### - User limits — stored in Cassandra for high-throughput writes
##### - Currency conversion — real-time rates from Central Bank
##### - Limit enforcement — daily, monthly, and per-transaction limits
##### - Account freezing — temporary or permanent block

Tech stack: Spring Boot , Spring Data JPA (PostgreSQL), Spring Data Cassandra, Kafka, Hibernate, WebClient and other.

### 5. Card-service
##### Microservice for card management

#### Card types

> Physical Card — plastic card with delivery
>
> Virtual Card — for online purchases (Wildberries/Ozon)
>
> Sticker Card — NFC sticker for contactless payments
>
> Child Card — child card linked to parent's card with budget allocation
>
> Loan Card - credit card with grace period

#### Key features

##### - Child Cards — parent allocates monthly allowance, category limits (fast food, games, YouTube)
##### - Card Design — custom design upload to MinIO
##### - Cassandra — high-throughput storage for transaction limits
##### - Blocking — temporary/permanent with reason
##### - The mechanics of credit cards

Tech stack: Spring Framework, PostgreSQL, Cassandra, MinIO, Kafka and other

Database: PostgreSQL for cards and bindings, Cassandra for category limits, MinIO for designs.

### 6. Transaction-Service
##### Microservice for internal transfers, transaction history

#### Key features:

##### - Internal transfers — instant transfers between Online Akim Bank accounts
##### - Redis distributed locks — prevents double spending and concurrent transfer conflicts
##### - Transaction history** — paginated history with date filters (cached in Redis, 5 min TTL)
##### - Rollback mechanism — automatic refund on failure

Tech stack: Spring Boot, Spring Data JPA (PostgreSQL), Redis, Cassandra, Kafka, WebClient, ACID and other

Database: PostgreSQL for complete transaction history, Redis for idempotency, locks, and hot cache, Cassandra for operational reports.

### 7. Payment-Service
#### Microservice for payment processing and cashback accrual with support for different user types.

#### Key features:

##### - Payments — pay for goods and services
##### - Cashback — accrue points  by category
##### - Tariffs — individual terms for individuals, entrepreneurs, and legal entities
##### - Points in Redis — fast balance access (persistent storage, no TTL)
##### - History in Cassandra — all accruals and redemptions for any period

Tech stack: Spring Framework, PostgreSQL, Cassandra, Redis, Kafka, WebClient ant other

Database: PostgreSQL for payments and tariffs, Cassandra for cashback history, Redis for point balance.

### 8. Loan-Service
##### Microservice for loan origination, payment schedule management, and credit scoring.

#### Key features:

##### - Loan products — consumer, mortgage, car, micro-loans with custom rates
##### - Payment schedules — annuity or differentiated with automatic generation
##### - Early repayment** — partial or full with minimal fee
##### - Delinquency tracking — automatic overdue detection and notifications
##### - Cash disbursement — automatic crediting to user's account via Account Service

Attempt:
Loan-Service - This monolith is unfinished and has many functions. It's the bank's most underdeveloped service, and it will be further refined in the future.

### 9. Notification-Service
#### Microservice for sending user notifications via SMS and Email

#### Key features:

##### - Stateless — no repository, fire-and-forget architecture
##### - Kafka consumer — all bank microservices publish events to trigger notifications
##### - Delivery channels — Twilio (SMS), JavaMail (email)

#### Supported notifications:

> Payments & transfers (success/failure, receipts
>
> Card transactions (spending, blocks, child card spending)
>
> Loans (reminders, overdue, approval)
>
> Security (login alerts)
>
> System (KYC status, limits, cashback)

Tech stack: Spring Boot 3, Kafka, Twilio SDK, JavaMailSender, Kafka and other

Integration: Subscribes to notification-events topic, sends notifications without storing history

### 10. Document-Service
##### Microservice for collecting data from microservices for documentation

### 11. Admin-Service
##### Admin panel for more convenient bank management

### 12. Eureka-Server
##### Service Discovery is a registration and discovery service in a microservice architecture. It allows microservices to find each other without hard-coded IP addresses.

## Launch examples:

![Header](https://github.com/AKIM1001/Online-Akim-Bank/blob/main/assets/work-1.jpg)
![Header](https://github.com/AKIM1001/Online-Akim-Bank/blob/main/assets/work-2.jpg)
![Header](https://github.com/AKIM1001/Online-Akim-Bank/blob/main/assets/work-3.jpg)

## Stack:

![Java](https://img.shields.io/badge/Java-%23283336?style=for-the-badge&logo=openjdk)
![Spring Framework](https://img.shields.io/badge/Spring%20Framework-%23283336?style=for-the-badge&logo=spring)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-%23283336?style=for-the-badge&logo=postgresql)
![Gradle](https://img.shields.io/badge/Gradle-%23283336?style=for-the-badge&logo=gradle)
![Hibernate](https://img.shields.io/badge/Hibernate-%23283336?style=for-the-badge&logo=hibernate)
![Apache Kafka](https://img.shields.io/badge/Apache%20Kafka-%23283336?style=for-the-badge&logo=apachekafka)
![Docker](https://img.shields.io/badge/Docker-%23283336?style=for-the-badge&logo=docker)
![Kubernetes](https://img.shields.io/badge/Kubernetes-%23283336?style=for-the-badge&logo=kubernetes)
![Grafana](https://img.shields.io/badge/Grafana-%23283336?style=for-the-badge&logo=grafana)
![Prometheus](https://img.shields.io/badge/Prometheus-%23283336?style=for-the-badge&logo=prometheus)
![Redis](https://img.shields.io/badge/Redis-%23283336?style=for-the-badge&logo=redis)
![Cassandra](https://img.shields.io/badge/Cassandra-%23283336?style=for-the-badge&logo=apachecassandra)

### Full-Stack: Java-21, Spring Boot, Spring Web, Spring Data, Hibernate JPA, Gradle, Lombok, PostgreSQL, Multithread, Redis, Apache Kafka, Zoookeper, Cassandra, Minio, Docker, Kubernetes, Eureka, Prometheus, Grafana, Keycloak, Mapstruct, Vault, Twilio, JavaMailSender.


## About plans:
##### The project isn't finalized or perfect; there's still a lot of room for improvement, but it's already functional and usable. In the future, I plan to optimize the bank, take apart the Loan Service, and the card service, among other things. I might also add a separate investment service, remove the currency service from the account, and so on :)

## Contacts:

[![Telegram](https://img.shields.io/badge/Telegram-%23283336?style=for-the-badge&logo=telegram)](https://t.me/VagyAngsty5677)
[![Gmail](https://img.shields.io/badge/Gmail-%23283336?style=for-the-badge&logo=gmail)](mailto:akimba94@gmail.com?subject=GitHub%20Contact)
[![Discord](https://img.shields.io/badge/Discord-%23283336?style=for-the-badge&logo=discord)](https://discord.com/users/1128448833869840554)


