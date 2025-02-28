# Reflection 1
Berikut prinsip-prinsip clean code yang saya rasa telah saya terapkan di pengerjaan exercise ini:

## Meaningful Names
Penamaan variabel dan fungsi yang saya buat sudah deskriptif dan dapat dimengerti. Salah satu penamaan yang mungkin saya rasa kurang cocok adalah fungsi update di ProductRepository. Saya membuat fungsi dengan nama tersebut karena mengikuti konvensi penamaan CRUD (Create, Read, Update, Delete) tetapi ternyata penamaan tersebut memunculkan inkonsistensi dengan fungsi-fungsi yang menggunakannya, seperti **edit**Product.
## Functions
Pembuatan function di kode saya juga sudah fokus kepada satu tujuan dan cukup pendek, tidak lebih dari satu layar ketika dibaca. Penamaannya juga sudah cukup deskriptif dan *concise*.
## Comments
Untuk exercise kali ini, saya tidak menggunakan comment sama sekali karena fungsionalitas masih sangat mendasar dan masih cukup mudah dimengerti tanpa comments.
## Objects and Data Structures
Prinsip ini tidak terlalu menonjol di kode saya, mungkin hanya terlihat di ProductService dimana kita 'menyembunyikan' implementasi dari interface ProductService.
## Error handling
Aspek ini masih dapat ditingkatkan di pekerjaan saya. Untuk sekarang, masih banyak penggunaan return null di kode saya. Saya juga belum menerapkan input validation yang lengkap di kode saya. Hal ini tentunya perlu ditingkatkan ke depannya.

# Reflection 2
## Nomor 1
### After writing the unit test, how do you feel?
Saat membuat, saya sedikit terkendala saat memikirkan cases-cases yang harus dicover. Tetapi, setelah melihat test-test yang saya buat sukses, saya merasa senang.
### How many unit tests should be made in a class?
Tidak ada aturan konkrit untuk banyak unit test yang harus dibuat, tetapi tiap kelas setidaknya harus memiliki unit test yang meng-cover kasus-kasus:
- Semua kemungkinan conditional
- Edge case
- Exception yang sudah disangka akan muncul (seperti input yang invalid)
- Berbagai macam kasus positif dan negatif
### How to make sure that our unit tests are enough to verify our program?
Ada yang dinamakan *code coverage*. Metric ini menunjukkan seberapa banyak kode kita yang dijalankan saat di-test. Ada *Line Coverage* yang memastikan tiap line tereksekusi dan *branch coverage* yang memeriksa tiap *branch* di *control statements*(if-else, switch, dll.). Best practice di industri diantaranya yaitu menargetkan setidaknya 80% code coverage, fokuskan tests pada bagian paling penting dari aplikasi (misal: business logic), dan hindari membuat test untuk bagian-bagian yang sederhana (misal: setter/getters)
### If you have 100% code coverage, does that mean your code has no bugs or errors? 
Belum tentu, karena mungkin saja tes yang kita buat tidak meng-cover edge cases. Bisa juga test yang kita buat tidak berkualitas atau memiliki logical error, sehingga masih ada kemungkinan bug atau error di aplikasi kita.
## Nomor 2
Suppose that after writing the CreateProductFunctionalTest.java along with the corresponding test case, you were asked to create another functional test suite that verifies the number of items in the product list. You decided to create a new Java class similar to the prior functional test suites with the same setup procedures and instance variables.
### What do you think about the cleanliness of the code of the new functional test suite? Will the new code reduce the code quality? Identify the potential clean code issues, explain the reasons, and suggest possible improvements to make the code cleaner!
Cleanliness dari kode tersebut berkurang karena adanya penduplikasian kode. Ketika kita meng-copy-paste langkah-langkah dan variabel ke banyak test class, kita melanggar prinsip "*Don't Repeat Yourself*". Selain juga meningkatkan kompleksitas pemeliharaan aplikasi kita, hal tersebut membuat kode kita kurang *readable*. Implementasi ini juga akan menimbulkan ketidakkonsistenan ketika kita mengubah base functionality bagian tersebut. Peningkatan dapat dilakukan dengan membuat class abstrak base untuk test dimana kita mengumpulkan logic untuk setup dan variabel-variabel yang diperlukan. Class ini nantinya dapat diextend oleh tests lain dan kita bisa langsung mendefinisikan method tes-tes yang spesifik kepada kelas test konkret tersebut.


# Reflection 3

### SRP
#### Peningkatan:
- Controller hanya meng-handle request HTTP, tidak handle business logic
- Services yang handle business logic
- Repository hanya handle penyimpanan data
- Memisahkan ProductController dan CarController

#### Advantage
Dengan SRP, setiap kelas hanya memiliki satu alasan untuk berubah, sehingga kode lebih mudah dirawat, diextend, dan dites. 

Sebelumnya, CarController inherit dari ProductController, yang membuat kedua kelas saling terkait. Setelah dipisah, ProductController hanya menangani produk, dan CarController hanya menangani mobil. Sekarang, jika ada perubahan dalam fitur produk, tidak akan mempengaruhi mobil.

Selain itu, CarServiceImpl awalnya mencampur business logic dan operasi database. Dengan SRP, CarServiceImpl hanya menangani logika bisnis, sementara CarRepository menghandle penyimpanan data. Ini membuat kode lebih bersih dan mudah dipahami.

Ketika melakukan testing, sebelumnya ProductController sulit diuji karena juga menangani CarController. Setelah SRP diterapkan, setiap bagian bisa diuji secara independen, sehingga lebih efisien.

#### Disadvantage
Tanpa SRP, kode menjadi sulit dipelihara dan dikembangkan. Jika ProductController berubah, CarController juga bisa terpengaruh, meskipun seharusnya tidak ada hubungannya.

Jika proyek berkembang, misalnya ingin menambahkan MotorcycleController, tanpa SRP kita mungkin tergoda untuk memasukkannya ke dalam CarController, yang akan memperumit kode.

Testing juga menjadi sulit. Jika ada bug di CarRepository, bisa menyebabkan kegagalan di CarServiceImpl, meskipun kesalahan bukan di sana. Dengan SRP, masalah dapat diisolasi dengan lebih baik.

