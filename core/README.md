## For Branch Master
[![pipeline status](https://gitlab.cs.ui.ac.id/AdvProg/reguler-2022/student/kelas-a/2006473951-Steven-Novaryo/adpro-a16/ui-reader/badges/master/pipeline.svg)](https://gitlab.cs.ui.ac.id/AdvProg/reguler-2022/student/kelas-a/2006473951-Steven-Novaryo/adpro-a16/ui-reader/-/commits/master)
[![coverage report](https://gitlab.cs.ui.ac.id/AdvProg/reguler-2022/student/kelas-a/2006473951-Steven-Novaryo/adpro-a16/ui-reader/badges/master/coverage.svg)](https://gitlab.cs.ui.ac.id/AdvProg/reguler-2022/student/kelas-a/2006473951-Steven-Novaryo/adpro-a16/ui-reader/-/commits/master)

## For Branch Development (Staging)
[![pipeline status](https://gitlab.cs.ui.ac.id/AdvProg/reguler-2022/student/kelas-a/2006473951-Steven-Novaryo/adpro-a16/ui-reader/badges/development/pipeline.svg)](https://gitlab.cs.ui.ac.id/AdvProg/reguler-2022/student/kelas-a/2006473951-Steven-Novaryo/adpro-a16/ui-reader/-/commits/development)
[![coverage report](https://gitlab.cs.ui.ac.id/AdvProg/reguler-2022/student/kelas-a/2006473951-Steven-Novaryo/adpro-a16/ui-reader/badges/development/coverage.svg)](https://gitlab.cs.ui.ac.id/AdvProg/reguler-2022/student/kelas-a/2006473951-Steven-Novaryo/adpro-a16/ui-reader/-/commits/development)

<div id="top"></div>

<!-- PROJECT SHIELDS -->
<!--
*** I'm using markdown "reference style" links for readability.
*** Reference links are enclosed in brackets [ ] instead of parentheses ( ).
*** See the bottom of this document for the declaration of the reference variables
*** for contributors-url, forks-url, etc. This is an optional, concise syntax you may use.
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
-->
[![Contributors][contributors-shield]][contributors-url]
[![Issues][issues-shield]][issues-url]



<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="https://github.com/github_username/repo_name">
    <img src="https://acakadul.files.wordpress.com/2013/08/makara-ui-fasilkom.png" alt="Logo" width="80" height="80">
  </a>

<h3 align="center">UI READER</h3>

  <p align="center">
    A News Aggregator Website
    <br />
  </p>
</div>



<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project

A scraper, news aggregrator, and news reader project for the class Advanced Programming.
<p align="right">(<a href="#top">back to top</a>)</p>



### Built With

* [Springboot](https://spring.io/projects/spring-boot)

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- GETTING STARTED -->
## Getting Started

### Build

Build the app with

```
gradle build
```

Run the app with

```
java -jar (Resolve-Path "./build/libs/*-SNAPSHOT.jar")
```
## Development

These are the local variables you need to fill before running the application. Generally, you can fill out the template `run.ps1` and run the application with `./run.ps1` in powershell.

### Database

This application uses postgres sql, you can use local or remote database.

It is mandatory to declare a local environment `JDBC_DATABASE_URL` with database credential as follow.

```
jdbc:postgresql://<host>:<port>/<database-name>?sslmode=require&user=<username>&password=<password>
```

For example, in windows powershell the command will be as follow.

```
$env:JDBC_DATABASE_URL="jdbc:postgresql://localhost:5432/postgres?sslmode=require&user=postgres&password=postgres"
```

### Sonarqube

There are three local environment for Sonarqube credentials as follow.

```
SONAR_URL                   - sonarqube host (ex. sonarqube.cs.ui.ac.id)
CORE_SONAR_PROJECT_KEY      - your peoject key
CORE_SONAR_LOGIN_KEY        - your login key
```


<!-- USAGE EXAMPLES -->
## Usage


<p align="right">(<a href="#top">back to top</a>)</p>



<!-- ROADMAP -->
## Roadmap
# Sprint 1
-  Homepage : membuat tampilan halaman depan yang menampilkan berita-berita populer dan terbaru (masih dummy data) serta dilengkapi navbar
-  Reader Usability : menampilkan data dari news pada halaman berita yang diakses
-  Reader Design : membuat tampilan dari halaman berita yang diakses
-  Api Scraper : fetch berita dari sumber news dan disimpan ke database
-  Membuat microservices dan design pattern (template) untuk api_fetcher
# Sprint 2
-  User SignUp : membuat halaman registrasi akun user, menyimpan hasil registrasi ke database sebagai objek user 
-  User Login : membuat halaman login user dengan implementasi WebSecurityConfig
-  User Subscribe : membuat fitur subscribe pada halaman berita (reader news)
-  Notification :  mengatur agar user mendapatkan notifikasi news baru sesuai dengan kategori yang mereka subscribe
-  Membuat microservices email_blaster
-  Membuat design pattern (singleton) untuk fitur subscribe
# Sprint 3 :
-  Filter Search : membuat filter search by title agar user dapat mencari berita sesuai judul yang diingikan
-  Filter Daterange & category : membuat filter yang membuat halaman news menampilkan berita sesuai dengan daterange atau category yang dipilih
-  Bookmark News : setiap news yang dibookmark oleh user akan ditampilkan pada halaman bookmark news
-  Improvisasi Homepage : membuat algoritma berita populer dan terbaru serta menghubungkannya dengan halaman berita (reader-news)
-  Membuat beberapa unit test
-  Refactor code (mengimplementasikan clean code dan mengurangi code smell) 

## Dokumentasi Api
# api_fetcher :
- Mengembalikan kumpulan berita dalam bentuk array of dictionary
- HTTP Request (GET): https://ui-reader.herokuapp.com/api/news
- Request Body : Tidak membutuhkan request body
- Response : [{"createdAt": .. ,"updatedAt":"...","id":"...","title":"...","description":"...","content":"...","imageUrl":"...","videoUrl":...,"category":"...","source":"...","publishedDate":"...","provider":{"id":"...","subscribedUser":[{"id":..,"email":"...","password":"...","firstName":"..","lastName":"..."}]},"viewCount":...,"active":...}]



See the [open issues](https://gitlab.cs.ui.ac.id/AdvProg/reguler-2022/student/kelas-a/2006473951-Steven-Novaryo/adpro-a16/ui-reader/-/issues?sort=created_date&state=opened) for a full list of proposed features (and known issues).


<p align="right">(<a href="#top">back to top</a>)</p>


<!-- CONTACT -->
## Contact

- Steven Novaryo - [@steven.novaryo](https://gitlab.cs.ui.ac.id/steven.novaryo)
- Mochammad Agus Yahya - [@mochammad.agus](https://gitlab.cs.ui.ac.id/mochammad.agus)
- Izzan Nufail Arvin - [@izzan.nufail](https://gitlab.cs.ui.ac.id/izzan.nufail)
- Pradipta Davi Valendra - [@pradipta.davi](https://gitlab.cs.ui.ac.id/pradipta.davi)

Project Link: [https://gitlab.cs.ui.ac.id/AdvProg/reguler-2022/student/kelas-a/2006473951-Steven-Novaryo/adpro-a16/ui-reader](https://gitlab.cs.ui.ac.id/AdvProg/reguler-2022/student/kelas-a/2006473951-Steven-Novaryo/adpro-a16/ui-reader)

<p align="right">(<a href="#top">back to top</a>)</p>



<p align="right">(<a href="#top">back to top</a>)</p>



<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/github_username/repo_name.svg?style=for-the-badge
[contributors-url]: https://github.com/github_username/repo_name/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/github_username/repo_name.svg?style=for-the-badge
[forks-url]: https://github.com/github_username/repo_name/network/members
[stars-shield]: https://img.shields.io/github/stars/github_username/repo_name.svg?style=for-the-badge
[stars-url]: https://github.com/github_username/repo_name/stargazers
[issues-shield]: https://img.shields.io/github/issues/github_username/repo_name.svg?style=for-the-badge
[issues-url]: https://github.com/github_username/repo_name/issues
[license-shield]: https://img.shields.io/github/license/github_username/repo_name.svg?style=for-the-badge
[license-url]: https://github.com/github_username/repo_name/blob/master/LICENSE.txt
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://linkedin.com/in/linkedin_username
[product-screenshot]: images/screenshot.png

