# multirabbitmq
Pipes and filters

## Broker containers
> tweets listen in 5672 port, filtered listens in 5673 port
- `docker run -p 15672:15672 -p 5672:5672 -d --hostname my-rabbit --name some-rabbit-mng rabbitmq:3-management`
- `docker run -p 15673:15672 -p 5673:5672 -d --hostname my-rabbit-2 --name some-rabbit-mng-2 rabbitmq:3-management`

> MariaDB container
- `docker run -p 8880:8080 -p 3306:3306 -d --name mariadbpf -e MYSQL_ROOT_PASSWORD=mypass --volume ~/db/data:/var/lib/mysql --name mariadb mariadb:latest`

## Tweet generator
`java -jar target/filter-0.0.1-SNAPSHOT.jar --spring.profiles.active=tweet-generator`

## Publish tweets in RabbitMQ
`java -jar target/filter-0.0.1-SNAPSHOT.jar --spring.profiles.active=tweets`

## Consume and Filter tweets from RabbitMQ
`java -jar target/filter-0.0.1-SNAPSHOT.jar --spring.profiles.active=tweet-filter`

## Tweets filtered ready to be persisted
`java -jar target/filter-0.0.1-SNAPSHOT.jar --spring.profiles.active=tweet-filtered`
