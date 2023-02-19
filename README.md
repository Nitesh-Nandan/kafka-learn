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
kafka-topics.sh --create --topic simple.test.topic --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1
kafka-topics.sh --create --topic simple.test.topic.error --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1
kafka-topics.sh --create --topic simple.test.topic.error.dlt --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1
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

## Monitoring Offset
```shell
kafka-consumer-groups.sh --bootstrap-server localhost:9092 --describe --group simple-kafka-consumer-group-manual-ack-error-2
```
## Delete topics
```shell
kafka-topics.sh --zookeeper localhost:2181 --delete --topic simple.test.topic.error
kafka-topics.sh --zookeeper localhost:2181 --list | xargs kafka-topics.sh --zookeeper localhost:2181 --delete --topic
```