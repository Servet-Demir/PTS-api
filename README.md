# PTS API - Personel Takip Sistemi Backend

Bu repository, Personel Takip Sistemi projesinin backend tarafını içermektedir. Backend uygulaması Java Spring Boot kullanılarak geliştirilmiştir ve frontend uygulamasına REST API üzerinden veri sağlamaktadır.

## Proje Hakkında

PTS API; kullanıcı girişi, birim yönetimi, personel yönetimi, mesai kayıtları ve maaş hesaplama işlemlerini yöneten backend servisidir. Veriler PostgreSQL veritabanında tutulmaktadır.

Frontend repository: `pts-ui`

## Kullanılan Teknolojiler

* Java
* Spring Boot
* Spring Data JPA
* PostgreSQL
* Maven
* Lombok
* REST API

## Özellikler

* Kullanıcı girişi
* Birim ekleme, güncelleme, silme ve listeleme
* Personel ekleme, güncelleme, silme ve listeleme
* Personelin yönetici olup olmadığını belirleme
* Mesai kaydı ekleme, güncelleme, silme ve listeleme
* Mesai geçerlilik kontrolü
* Personel ve dönem bazlı mesai özeti
* Maaş hesaplama
* Döneme göre maaş listeleme

## Proje Yapısı

```text
pts-api
├── src
│   └── main
│       ├── java
│       │   └── com.servet
│       │       ├── controller
│       │       ├── entities
│       │       ├── repository
│       │       ├── services
│       │       └── dto
│       │
│       └── resources
│           └── application.properties
│
├── pom.xml
└── README.md
```

## Kurulum

Repoyu klonlayın:

```bash
git clone https://github.com/kullanici-adi/pts-api.git
```

Proje klasörüne gidin:

```bash
cd pts-api
```

Maven bağımlılıklarını yükleyip projeyi çalıştırın:

```bash
mvn spring-boot:run
```

Backend varsayılan olarak şu adreste çalışır:

```text
http://localhost:8080
```

## Veritabanı Ayarları

Projede PostgreSQL kullanılmaktadır. `application.properties` dosyasında veritabanı bağlantı bilgileri tanımlanmalıdır.

Örnek yapı:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/pts_db
spring.datasource.username=postgres
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

Güvenlik nedeniyle gerçek veritabanı şifresi GitHub'a yüklenmemelidir.

## Temel Tablolar

* kullanicilar
* birimler
* personeller
* mesai_kayitlari
* maas_hesaplari

## API Endpointleri

### Kullanıcı

```text
POST /rest/api/kullanici/login
```

### Birim

```text
GET    /rest/api/birim/list
GET    /rest/api/birim/list/{id}
POST   /rest/api/birim/save
PUT    /rest/api/birim/update/{id}
DELETE /rest/api/birim/delete/{id}
```

### Personel

```text
GET    /rest/api/personel/list
GET    /rest/api/personel/list/{id}
POST   /rest/api/personel/save
PUT    /rest/api/personel/update/{id}
DELETE /rest/api/personel/delete/{id}
GET    /rest/api/personel/{id}/mesai?donem=2026-06-01
```

### Mesai

```text
GET    /rest/api/mesai/list
GET    /rest/api/mesai/personel/{personelId}?donem=2026-06-01
POST   /rest/api/mesai/save
PUT    /rest/api/mesai/update/{id}
DELETE /rest/api/mesai/delete/{id}
```

### Maaş

```text
GET    /rest/api/maas/list
GET    /rest/api/maas/donem?donem=2026-06-01
POST   /rest/api/maas/hesapla/{personelId}?donem=2026-06-01
DELETE /rest/api/maas/delete/{id}
```

## Maaş Hesaplama Mantığı

Sistemde maaş hesaplama işlemi personelin seçilen dönemdeki mesai kayıtlarına göre yapılır.

Örnek kurallar:

```text
Personel maaşı: 30000 TL
Yönetici maaşı: 40000 TL
Geçersiz mesai cezası: 500 TL
```

Net maaş hesaplama:

```text
Net Maaş = Brüt Maaş - Ceza
```

## Geliştirilebilecek Özellikler

* Rol bazlı yetkilendirme
* JWT authentication
* Swagger/OpenAPI dokümantasyonu
* Gelişmiş hata yönetimi
* Raporlama servisleri
* Excel veya PDF çıktı alma

## Geliştirici

Servet Demir

Bu backend projesi, staj sürecinde Spring Boot, REST API, PostgreSQL ve katmanlı mimari konularını uygulamalı olarak geliştirmek amacıyla hazırlanmıştır.
