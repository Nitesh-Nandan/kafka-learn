# Kafka Server Startup
```shell
# start zookeeper server
./zookeeper-server-start.sh ../config/zookeeper.properties
```

```shell
# start kafka server
./kafka-server-start.sh ../config/server.properties
```

## Topics Creations
```shell
# topic creation
kafka-topics.sh --create --topic simple-kafka-topic --bootstrap-server localhost:9092 --replication-factor 1 --partitions 3
```

## Kafka ConsoleMessage Producer
```shell
# Message Publish in simple-kafka-topic
kafka-console-producer.sh --broker-list localhost:9092 --topic simple-kafka-topic
```