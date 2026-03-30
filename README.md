# ⚙️ API Automation Testing Framework (Java & JUnit)

## 📌 Project Overview
Proyek ini adalah framework otomasi pengujian API (Application Programming Interface) yang dibangun menggunakan **Java dan JUnit**. Proyek ini bertujuan untuk memvalidasi respons dari berbagai *endpoint* API secara otomatis, memastikan integritas data, dan mengecek kesesuaian kode status HTTP. Framework ini didesain untuk mempercepat siklus pengujian *backend* sebelum integrasi UI dilakukan.

## 🛠️ Tech Stack & Tools
* **Programming Language:** Java
* **Testing Framework:** JUnit 5
* **HTTP Client:** RestAssured / Apache HTTP Client *(sesuaikan dengan yang kamu pakai)*
* **Build Tool:** Gradle
* **CI/CD:** GitHub Actions
* **Reporting:** Allure Report / HTML Report

## 🧪 Test Automation Strategy
Pengujian API ini dirancang untuk mencakup berbagai skenario kritis:
1. **Positive Testing:** Mengirim *request* dengan parameter dan *body* yang valid, lalu memvalidasi *response code* (200 OK, 201 Created) dan *payload* JSON yang dikembalikan.
2. **Negative Testing:** Menguji ketahanan API dengan mengirim tipe data yang salah, parameter yang tidak lengkap, atau *endpoint* yang tidak valid (memvalidasi *error code* 400 Bad Request, 404 Not Found, dll).
3. **Boundary Testing:** Menguji batasan nilai input, seperti nilai maksimum/minimum untuk parameter *request*.

## 🏗️ Framework Structure
* `src/test/java/requests/`: Menyimpan konfigurasi *endpoint* dan *method* HTTP (GET, POST, PUT, DELETE).
* `src/test/java/tests/`: Berisi *test cases* JUnit yang memvalidasi *response*.
* `src/test/java/utils/`: *Utility classes* untuk tugas umum seperti membaca data *test* atau autentikasi *token*.

## ▶️ How to Run the Tests

1. **Clone the repository:**
   ```bash
   git clone [https://github.com/caseyzeldagerica/API-Automation-Testing-Java.git](https://github.com/caseyzeldagerica/API-Automation-Testing-Java.git)
