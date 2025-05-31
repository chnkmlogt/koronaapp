# COVID-19 Grafik Takip Uygulaması


Proje: Korona  Verileri

Projenin Amacı: Açık kaynak haberlerden text halinde bilgilerin anahtar vaka sayıları ölüm ve iyileşen sayılarını toplayıp bir ekranda grafik olarak gösterimin yapılması ve yeni veri sisteme eklendikçe grafiğin online olarak değişmesi amaçlanmaktadır.

Örnek Haber 1: 20.04.2020 tarihinde Ankara da Korona virüs salgınında yapılan testlerde 15 yeni vaka bulundu. 1 kişi korona dan vefat etti. 5 kişide taburcu oldu.

Örnek Haber 2: Korona virüs salgınında yapılan testlerde 19.04.2020 tarihinde  İstanbul da 30 yeni vaka tespit edil. İstanbul da taburcu sayısı 7 kişi .  3 kişi de vefat etti.

Örnek Haber 2: 19.04.2020 tarihinde İstanbul  için korona virüs ile ilgili yeni bir açıklama yapıldı. Korona virüs salgınında yapılan testlerde 20 yeni vaka tespit edildi. taburcu sayısı ise 7 oldu.  3 kişin de vefat ettiği öğrenildi.



İsterler:

1. Haberler de üç anahtar kelimede (Tarih, İl, vaka, vefat, taburcu) ayrı ayrı cümlelerde bulunmalı.
2. Haberde cümlelerin sırası değişebilir.
3. Tarih ve il bilgisi herhangi bir cümlede olabilir ya da ayrı bir cümlede olabilir
4. Kullanıcı yeni Haber girebilecektir.
5. Sistem girilen haberi parse edip il, iyileşen, vefat, vaka sayısı ve tarihi veri tabanına ilgili haber ile beraber yazacak.
6. Kullanıcı rapor ekranını açınca grafik olarak vefat, iyileşen ve vaka sayısını zamana göre günlük olarak görebilecek.
7. Kullanıcı isterse rapor ekranında grafiği kümülatife olarak görebilecek.
8. Kullanıcı isterse Rapor ekranında seçtiği ilin grafiğini görebilecektir.
9. Yeni haber eklendikçe grafikler güncellenecektir.
   Teknik İsterler:

10. Yazılım önyüzü ReactJS kullanılarak geliştirilecekti ve npm kullanılacaktır.
11. Sunucu tarafında SpringBoot kullanılacaktır ve maven projesi olacaktır.
12. Veri tabanı olarak MongoDB kullanılacaktır.
13. Yazılım geliştirirken birim testleri yazılmalıdır. Birim testler tüm önemli fonksiyonları kapsayıcı olmalıdır.
14. Yazılım geliştirmeleri sonucu oluşan paketler ve kodlara github üzerinden erişilebilmelidir.






## Açıklama
- Spring Boot + MongoDB backend
- ReactJS frontend
- Açık haber metinlerinden vaka, vefat, iyileşen sayısı çıkarımı ve grafik gösterimi

## Klasörler
- `backend/`: Spring Boot projesi
- `frontend/`: ReactJS arayüz

## Kurulum
1. MongoDB çalışır durumda olmalı
2. `backend` klasöründe `mvn spring-boot:run`
3. `frontend` klasöründe `npm install && npm start`
