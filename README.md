# Elastische Datenbanksysteme
## Docker Cluster einrichten (Windows)
### Installation von Docker Desktop
Zuerst muss Docker Desktop installiert sein. Falls noch nicht vorhanden ist hier der Link zum Download:
https://hub.docker.com/editions/community/docker-ce-desktop-windows/ \
Nachdem die .exe aufgerufen wurde, muss in der Konfiguration das "Enable Hyper-V Windows Features" angehakt werden. Anschließend beginnt die Installation. Um die Installation abzuschließen muss der PC neu gestartet werden.

### Cassandra Image Download
Im CMD von Windows folgenden Befehl eingeben, um das Image von Cassandra zu pullen:
```console
docker pull cassandra
```

### Aufsetzen von Cassandra Cluster
Anschließend kann im CMD der Container aufgesetzt werden. "CassandraContainer1" kann durch einen beliebigen Namen ersetzt werden.
```console
docker run --name CassandraContainer1 -d -p "9042:9042" cassandra:latest
```
Der erste Container ist somit eingerichtet und es könnte eine Cassandra-Datenbank eingerichtet werden. Um einen zweiten Knoten hinzuzufügen und somit einen Cluster einzurichten, müssende folgende Schritte erfolgen:
```console
docker inspect --format="{{ .NetworkSettings.IPAddress }}" CassandraContainer1
```
Es wird die zum Knoten zugehörige Adresse ausgegeben. Zum Beispiel 172.17.0.2. Nachfolgend muss der zweite Container erstellt und gestartet werden, dem wir diese Adresse übergeben mit:
```console
docker run --name CassandraContainer2 -d -e CASSANDRA_SEEDS="172.17.0.2" cassandra:latest
```
Für weitere Knoten im Cluster kann der letzte Schritt einfach öfter wiederholt werden.

## Cluster mit Daten füllen
Der Stand des Master-Branches besitzt Testklassen, in denen eine Datenbank erstellt werden kann und Daten generiert und in den Cluster eingefügt werden können.
Für die Datenbankerstellung muss die Testklasse unter dem Pfad "*Skal.-DB-WindMess/src/test/java/CreateDatabase*" ausgeführt werden. \
Um Daten zu generieren und in den Cluster einzufügen gibt es unter dem Pfad "*Skal.-DB-WindMess/src/test/java/GenerateData*" die zugehörige Testklasse. Die Anzahl der generierten Daten ist im Moment noch statisch im Code:
```code
private final static int USER_GENERATE_NUMBER = 100;
private final static int CAMPAIGN_GENERATE_NUMBER = 100;
....
```

## Abfragen
### Abfragen für Cassandra
Um die Daten abzufragen stellt Cassandra **CQL** bereit. Über das Docker-Dashboard wird über den CLI Button vom CassandraCointainer1 direkt das CLI geöffnet. Dort muss die CQL Shell gestartet werden mit dem Befehl:
```console
cqlsh
```
Jetzt können Anfragen eingegeben werden. CQL orientiert sich start an SQL was die Sache erleichtert. Beispielsweise können alle Keyspaces ausgegeben werden über den Befehl
```sql
SELECT * FROM system_schema.keyspaces;
```
In den generierten Daten findet man schwander3000 als Keyspace. Um sich nun Beispielsweise alle Tabellen des Keyspaces "schwander3000" ausgeben zu lassen:
```sql
SELECT * FROM system_schema.tables WHERE keyspace_name = 'schwander3000';
```
Eine der nun aufgelisteten Tabellen ist die Tabelle "user". Lassen wir uns nun den Inhalt dieser Tabelle anzeigen:
```sql
SELECT * FROM schwander3000.user;
```

## Informationen über den Cluster
Innerhalb der CLI kann man natürlich auch Informationen über den Cluster erhalten. \
Status des Datacenters
```console
nodetool status
```
