# SpringBoot-CRUD

Poznań Adam Mickiewicz Üniversitesi'nde aldığım [Programming Laboratory](http://mw.home.amu.edu.pl/zajecia/PRA2020/PRAENG.html) dersinin son projesidir.

Spring Boot framework kullanılarak MySql gibi bir **veritabanı** altyapısı üzerinde _CRUD_ işlemlerinin yapılabildiği ayrıca **harici** bir server üzerinde çalışabilen (çünkü halihazırda Spring Boot gömülü bir Tomcat Server'a sahiptir ve onu kullanır), **MVC** modeli kullanılarak bir **REST API** backend uygulaması yazılması istenilmiştir. Ayrıca veritabanı tabloları üzerinde bazı ilişkisel şartlar belirlenmiştir.

"frontend/" dizinindeki html sayfaları aracılığıyla uygulama ile etkileşime geçilebildiği gibi **Postman** da kullanılabilmektedir. "Request" ve "Response"lar json veri formatındadır ve malum html sayfalarında Ajax teknolojisi kullanılmıştır. CSS kullanılmadığı için frontend ilkel bir görünüme sahiptir.

Veritabanı altyapısı olarak _MySql_ kullanılmıştır. **Hibernate** veya **JPA** kullanımında başarısız olunduğu için vertabanı manipülasyonları, MVC implementasyonu geleneksel yollarla yapılmıştır.

### Veri Modeli
![Veri Modeli](/ss/data-model.png)

### Harici Sunucu Kullanımı
![Harici sunucu](/ss/external.png)

### Items tablosu için olan Front-End Sayfası
![Items](/ss/items.png)
![Items](/ss/items-.png)

### Postman ile REST API etkileşimi
![Postman Kullanımı](/ss/postman.png)

---
![Ratings](/ss/ratings.png)
