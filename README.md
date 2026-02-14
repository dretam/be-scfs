# Backend Corsys Starter Kit

Backend Corsys starter kit dengan penerapan hexagonal architecture project for Spring Boot

## Packages

## Environment Variables

Untuk menjalankan proyek ini, Anda perlu menambahkan variabel environment berikut ke file .env

```bash
SERVER_PORT=8081
SPRING_APPLICATION_NAME=hexagonal-architecture
SPRING_PROFILES_ACTIVE=production
SPRING_SERVLET_MULTIPART_MAXFILESIZE=1MB
SPRING_SERVLET_MULTIPART_MAXREQUESTSIZE=10MB

SPRING_DATASOURCE_DRIVERCLASSNAME=com.mysql.cj.jdbc.Driver
SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/db_hexagonal?useSSL=false&serverTimezone=Asia/Jakarta
SPRING_DATASOURCE_USERNAME=
SPRING_DATASOURCE_PASSWORD=

SPRING_FLYWAY_LOCATIONS=classpath:db/migration/mysql
SPRING_JPA_PROPERTIES_HIBERNATE_DIALECT=org.hibernate.dialect.MySQLDialect
SPRING_JPA_PROPERTIES_HIBERNATE_SHOW_SQL=false
SPRING_JPA_PROPERTIES_HIBERNATE_FORMAT_SQL=false

SPRING_MAIL_HOST=sandbox.smtp.mailtrap.io
SPRING_MAIL_PORT=2525
SPRING_MAIL_USERNAME=
SPRING_MAIL_PASSWORD=
SPRING_PROPERTIES_MAIL_DEBUG=false
SPRING_PROPERTIES_SMTP_AUTH=true
SPRING_PROPERTIES_SMTP_STARTTLS_ENABLE=true

# Minimal 256 Bits (43 Char), Requirement jose
JWT_SECRET=
JWT_ALGORITHM=HmacSHA256
JWT_ISSUER=http://localhost:8080
JWT_AUDIENCE=http://localhost:8080
JWT_EXPIRES_SECONDS=100

OTEL_SERVICE_NAME=hexagonal-architecture
OTEL_RESOURCE_ATTRIBUTES=env=local,team=backend
OTEL_EXPORTER_OTLP_ENDPOINT=http://host.docker.internal:4317
OTEL_EXPORTER_OTLP_PROTOCOL=grpc

# Disable logs & metrics if only want tracing
OTEL_METRICS_EXPORTER=none
OTEL_LOGS_EXPORTER=none
```

## Installation

Setelah kloning berhasil silahkan untuk masuk ke folder root aplikasi dan lakukan perintah sebagai berikut.

```bash
mvn clean:install
```

## Run Locally

Kloning proyek from SCM Bank Mega

```bash
  git clone https://scm.bankmegadev.com/cobs/corporate-system/starter-kit/be-corsys.git
```

Arahkan ke folder aplikasi

```bash
  cd be-corsys
```

Install package dependencies

```bash
$ be-corsys/ mvn clean:install
```

## Run Locally using Docker

Sebelum mengikuti ini diharapkan pada komputer terdapat [Docker Desktop](https://www.docker.com/Contracts/docker-desktop/) yang telah terinstall [docker-compose](https://docs.docker.com/compose/install/) didalamnya.

Kloning proyek

```bash
  git clone https://scm.bankmegadev.com/cobs/corporate-system/starter-kit/be-corsys.git
```

Arahkan ke folder aplikasi

```bash
  cd be-corsys
```

Bangun image docker aplikasi dan mulaikan image.

```bash
be-corsys/ docker-compose up --build -d
```

Untuk melihat logs pada aplikasi

```bash
be-corsys/ docker logs -f --tail 100 <nama-image-aplikasi>
```

## Authors

- [@yusuf.putra](https://scm.bankmega.local/yusuf.putra)
- [@corsys-yusuf](https://github.com/corsys-yusuf)

## Contributing

Untuk bergabung dalam kontribusi pengembangan aplikasi ini dapat mengabari admin untuk ditambahkan dalam pengguna yang dapat mengakses proyek.

## License

[GNU AGPL 3.0](https://choosealicense.com/licenses/agpl-3.0/)
