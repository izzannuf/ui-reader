$env:CORE_SONAR_LOGIN_KEY="4ef7ead38c2bab9e50ca9d8db23d1915ff6e15ec"
$env:CORE_SONAR_PROJECT_KEY="adpro-a16_ui-reader_AYBO42twmTzPxwcesGUk"
$env:SONAR_URL="https://sonarqube.cs.ui.ac.id"
$env:JDBC_DATABASE_URL="jdbc:postgresql://ec2-3-229-252-6.compute-1.amazonaws.com:5432/d2ss7g8hv5m3pa?sslmode=require&user=unsbyfcqutyeef&password=49b9e8d3ed17155bf0cb78313744c87118114f28ae6557a4d99c7c177e97870a"
$env:API_KEY="4KzFCb5nqx"

gradle build

java -jar (Resolve-Path "./build/libs/*-SNAPSHOT.jar")