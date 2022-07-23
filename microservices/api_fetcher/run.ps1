$env:API_KEY="4KzFCb5nqx"
$env:CORE_HOST="http://localhost:8080"
$env:NEWS_DATA_API_KEY="pub_6806ea794e5709f613a07aded8fdf6b12492"
$env:NEWS_API_ORG_API_KEY="f1490664bd904b7c902db2f768fbd12f"

gradle build

java -jar (Resolve-Path "./build/libs/*-SNAPSHOT.jar")