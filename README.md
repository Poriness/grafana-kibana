# Zespół
- Dorota Płachno
- Anna Matuszek

# Monitorowanie aplikacji z wykorzystaniem narzędzi grafana i kibana.
Administracja systemów komputerowych  
semestr VIII, 2021/2022

W projekcie użyto aplikacji poradni dietetycznej na bazie mikroserwisów, która umożliwia tworzenie klientów i dietetyków i umawiania konsultacji między nimi.

Wyróżniamy w niej 3 główne mikroserwisy:
- authentication-service
- users-service
- appointements-service

Aplikacja jest uruchomiona lokalnie za pomocą Intellij IDEA. \
Połączona jest z dwoma bazami danych PostgreSQL - users-db, appointments-db, które są postawione na dockerze, obrazie. \
W celu monitoringu aplikacji dodano do zależności Spring Boot Actuator i Micrometer dla każdego mikroserwisu.


## Grafana
Grafanę również pobrano jako obraz dockerowy. 

Dodano dwie wyżej wspomniane bazy danych jako datasource, a także prometheusa, którego zainstalowano lokalnie i powiązano z endpointami wystawionymi przez Actuator. 

Wykorzystano dashboard do monitorowania statystyk aplikacji, który jest ogólnie dostępny (https://grafana.com/grafana/dashboards/6756).

## Kibana
Logstash, ElasticSearch i Kibanę (ELK) zainstalowano lokalnie. 

W celu połączenia aplikacji z Logstashem, dodano do poradni dietetycznej do każdego mikroserwisu plik ‘logback-spring.xml’, które odpowiadaja za generowanie logów do pliku. Następnie po stronie Logstasha skonfigurowano połączenie w pliku ‘pipelines.yml’. Tam również jest zdefiniowane połączenie z ElasticSearch. ElasticSearch dostają w zrozumiałym dla siebie formacie logi, tworzy indeksy, które są wysyłane i obrazowane przez Kibane. 

W Kibanie dla każdego mikroserwisu stworzono index pattern, który pozwala na czytelne wyświetlanie logów. 
