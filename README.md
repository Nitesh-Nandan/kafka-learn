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
kafka-topics.sh --create --topic simple.test.topic --bootstrap-server localhost:9092 --replication-factor 1 --partitions 3
kafka-topics.sh --create --topic simple.test.topic.error --bootstrap-server localhost:9092 --replication-factor 1 --partitions 3
kafka-topics.sh --create --topic simple.test.topic.error.dlt --bootstrap-server localhost:9092 --replication-factor 1 --partitions 3
```
## List all topics
```shell
kafka-topics.sh --list --bootstrap-server localhost:9092
```

## Kafka Console Producer
```shell
# Message Publish
kafka-console-producer.sh --broker-list localhost:9092 --topic simple.test.topic
kafka-console-producer.sh --broker-list localhost:9092 --topic simple.test.topic.error
```

## Kafka Console consumer
```shell
# Message Reading
kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic simple.test.topic.error.dlt --from-beginning
```