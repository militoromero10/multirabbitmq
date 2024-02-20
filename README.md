# multirabbitmq
Pipes and filters

## Broker containers
> tweets listen in 5672 port, filtered listens in 5673 port
- `docker run -p 15672:15672 -p 5672:5672 -d --hostname my-rabbit --name some-rabbit-mng rabbitmq:3-management`
- `docker run -p 15673:15672 -p 5673:5672 -d --hostname my-rabbit-2 --name some-rabbit-mng-2 rabbitmq:3-management`

> MySql container
- Inside project folder execute `docker build -t mydbservice:0.0.1 db`
- Start container `docker run --name=mydbservice -p3306:3306 -e MYSQL_ROOT_PASSWORD=prueba123 -d mydbservice:0.0.1`

## Tweet generator
- `TWEET_SIZE` environmental variable optional, default value is 100
- `java -jar target/filter-0.0.1-SNAPSHOT.jar --spring.profiles.active=tweet-generator`

## Tweets filtered ready to be persisted
`java -jar target/filter-0.0.1-SNAPSHOT.jar --spring.profiles.active=tweet-filtered`
- It is necessary to create a tweets queue manually within broker at 15673 port, as a Classic type

## Consume and Filter tweets from RabbitMQ
- `TAGS` environmental variable optional, default value is `machinelearning,ml`
- `java -jar target/filter-0.0.1-SNAPSHOT.jar --spring.profiles.active=tweet-filter`

## Publish tweets in RabbitMQ
- `java -jar target/filter-0.0.1-SNAPSHOT.jar --spring.profiles.active=tweets`

