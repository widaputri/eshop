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