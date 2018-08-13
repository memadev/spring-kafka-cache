# Push based cache mechanism
Show cases how push based cache mechanism using Spring Kafka and Apache Kafka.

There are two types of cache mechanisms, push and pull. In pull cache mechanism if there is a
change, cache will be invalidated and new values will be fetched on client request.
The advantage of pull cache is that cache will not fetch the changes that are not requested by client.
The disadvantage of it is when a client requests a value from cache which does not exist 
in the cache it must wait for cache to fetch it, means more latency.
  
In push cache mechanism changes are pushed as soon as they occur in the system so when client 
requests the value from the cache, it should not wait for cache to fetch it, cache just return it.

I have used Apache Kafka for cache, when a changes occurs the change will be published to the topic
and the subscribers of the topic will each get a copy of the change and will update their cache.

In Kafka we have topics, partitions, consumer groups, consumer and producer. Each consumer group 
will receive all the messages in a topic(since I have set AUTO_OFFSET_RESET_CONFIG to earliest), 
so if we have three applications, they each need a full set of messages in order to be able to 
build the cache, That's why we need to have unique consumerGroupId for each application. Usually 
I consider host name and port to create a unique consumer group id.

When the application starts up, Spring will create a kafka consumer and will invoke listen method
in CityCacheImpl class for every message it receives from Kafka topic called city. I have chosen 
to have ConsumerRecord's value as String to easily publish message to city topic with command line 
kafka producer. I usually choose the value type as byte[] and the key type as String or Long. 

To test the application first you need to run Zookeeper and Kafka(If you haven't yet).
Change to bin directory of Kafka and run zookeeper:
    
   ./zookeeper-server-start.sh ../config/zookeeper.properties
                         
then you need to run Kafka:
                         
   ./kafka-server-start.sh ../config/server.properties


Now run the application and publish messages to kafka using the following command:

   echo '1,{"id":1, "name":"Stockholm"}' | ./kafka-console-producer.sh  --broker-list localhost:9092 --topic city --property parse.key=true --property key.separator=,
   
   echo '1,{"id":2, "name":"Oslo"}' | ./kafka-console-producer.sh  --broker-list localhost:9092 --topic city --property parse.key=true --property key.separator=,

Every time the application starts up the cache will be created again.
