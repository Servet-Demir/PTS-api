# PTS API - Personel Takip Sistemi Backend

Bu repository, Personel Takip Sistemi projesinin backend tarafını içermektedir. Backend uygulaması Java Spring Boot kullanılarak geliştirilmiştir ve frontend uygulamasına REST API üzerinden veri sağlamaktadır.

Frontend repository: [PTS-ui](https://github.com/Servet-Demir/PTS-ui)

## Proje Hakkında

PTS API; kullanıcı girişi, birim yönetimi, personel yönetimi, mesai kayıtları ve maaş özeti işlemlerini yöneten backend servisidir. Veriler PostgreSQL veritabanında tutulmaktadır.

Bu projede personellerin mesai kayıtları üzerinden dönem bazlı maaş özeti oluşturulmaktadır. Seçilen dönemde mesai kaydı bulunan personeller için toplam mesai kaydı, geçersiz mesai sayısı, brüt maaş, ceza ve net maaş bilgileri hesaplanmaktadır.

## Kullanılan Teknolojiler

* Java 17
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
* Personelin yönetici veya personel olarak belirlenmesi
* Mesai kaydı ekleme, güncelleme, silme ve listeleme
* Mesai geçerlilik kontrolü
* Personel ve dönem bazlı mesai özeti
* Seçilen dönemde mesai kaydı bulunan personeller için maaş özeti oluşturma
* Geçersiz mesai kayıtlarına göre ceza hesaplama
* Yönetici personeller için farklı maaş hesaplama kuralı
* REST API mimarisi

## Proje Yapısı

```text
PTS-api
├── .mvn
│   └── wrapper
├── src
│   └── main
│       ├── java
│       │   └── com.servet
│       │       ├── controller
│       │       ├── dto
│       │       ├── entities
│       │       ├── repository
│       │       └── services
│       │
│       └── resources
│           └── application.properties
│
├── .gitignore
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
```

## Kurulum

Repoyu klonlayın:

```bash
git clone https://github.com/Servet-Demir/PTS-api.git
```

Proje klasörüne gidin:

```bash
cd PTS-api
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

Projede PostgreSQL kullanılmaktadır. Veritabanı bağlantı bilgileri `src/main/resources/application.properties` dosyası üzerinden tanımlanır.

Örnek yapı:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/pts_db
spring.datasource.username=postgres
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

Güvenlik nedeniyle gerçek veritabanı şifresi GitHub'a yüklenmemelidir. Gerekirse örnek ayarlar için `application-example.properties` dosyası oluşturulabilir.

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
GET    /rest/api/mesai/list/{tarih}
GET    /rest/api/mesai/personel/{personelId}?donem=2026-06-01
POST   /rest/api/mesai/save
PUT    /rest/api/mesai/update/{id}
DELETE /rest/api/mesai/delete/{id}
```

### Maaş

```text
GET    /rest/api/maas/list
GET    /rest/api/maas/donem?donem=2026-06-01
GET    /rest/api/maas/donem-ozeti?donem=2026-06-01
POST   /rest/api/maas/hesapla/{personelId}?donem=2026-06-01
DELETE /rest/api/maas/delete/{id}
```

## Dönem Bazlı Maaş Özeti Mantığı

Maaş özeti, seçilen dönemde mesai kaydı bulunan personeller üzerinden oluşturulur.

Sistem seçilen ayın başlangıç ve bitiş tarihlerini belirler. Ardından bu tarih aralığında mesai kaydı bulunan personelleri bulur ve her personel için maaş özetini hesaplar.

Hesaplanan bilgiler:

* Personel adı ve soyadı
* Personelin yönetici olup olmadığı
* Dönem bilgisi
* Toplam mesai kaydı
* Geçersiz gün sayısı
* Brüt maaş
* Ceza tutarı
* Net maaş

Örnek maaş kuralları:

```text
Personel maaşı: 30000 TL
Yönetici maaşı: 40000 TL
Geçersiz mesai cezası: 500 TL
```

Net maaş hesaplama:

```text
Net Maaş = Brüt Maaş - Ceza
```

Yönetici personeller için geçersiz mesai cezası uygulanmaz.

## Örnek Maaş Özeti Cevabı

```json
[
  {
    "personelId": 1,
    "ad": "Servet",
    "soyad": "Demir",
    "yonetici": false,
    "donem": "2026-06-01",
    "toplamMesaiKaydi": 20,
    "gecersizGun": 2,
    "brutMaas": 30000,
    "ceza": 1000,
    "netMaas": 29000
  }
]
```

## Geliştirilebilecek Özellikler

* JWT authentication
* Rol bazlı yetkilendirme
* Swagger/OpenAPI dokümantasyonu
* Gelişmiş hata yönetimi
* Raporlama servisleri
* Excel veya PDF çıktı alma
* Loglama ve merkezi hata takibi
* Birim bazlı maaş ve mesai raporları

## Geliştirici

Servet Demir

Bu backend projesi, staj sürecinde Spring Boot, REST API, PostgreSQL, JPA ve katmanlı mimari konularını uygulamalı olarak geliştirmek amacıyla hazırlanmıştır.
