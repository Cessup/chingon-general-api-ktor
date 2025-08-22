# cacao

Cacao is a example of an e-commerce. It's API gives mobile applications all services in ktor by kotlin so that's the backend part. 

I'm still developing it so my idea is make a system by my knowledge because I wanted to show you how I can make any system. If any one needs it for consult that will a pleasure be reference.

If you want to more information about me you can visit my website.
- [cessup.com](https://www.cessup.com)




## Pre-Requirements
Before your run, you need to have a mongo db can be local or remote. If you hava any mongo database you can configure that in application.yaml.

if you need more information about it you can check the next link
- [Mongo Documentation](https://www.mongodb.com)


## Features
Here's a list of features included in this project:

| Name                                                                                                                                      | Description                                                                                       |
|-------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------|
| [Session](https://www.postman.com/cessupx/cacao-workspace/folder/goo6ezk/session-services) | There are all services about session like sign in or sing up.                                     |
| [Product](https://www.postman.com/cessupx/cacao-workspace/folder/15fk1y4/products-services) | There are all services about products like insert, update, delete and every thing about products. |

## Building & Running

To build or run the project, use one of the following tasks:

| Task                          | Description                                                          |
| -------------------------------|---------------------------------------------------------------------- |
| `./gradlew test`              | Run the tests                                                        |
| `./gradlew build`             | Build everything                                                     |
| `buildFatJar`                 | Build an executable JAR of the server with all dependencies included |
| `buildImage`                  | Build the docker image to use with the fat JAR                       |
| `publishImageToLocalRegistry` | Publish the docker image locally                                     |
| `run`                         | Run the server                                                       |
| `runDocker`                   | Run using the local docker image                                     |

If the server starts successfully, you'll see the following output:

```
2025-07-22 17:10:27.080 [main] INFO  Application - Autoreload is disabled because the development mode is off.
2025-07-22 17:10:27.733 [main] INFO  org.mongodb.driver.client - MongoClient with metadata {"driver": {"name": "mongo-java-driver|reactive-streams|kmongo", "version": "4.11.0"}, "os": {"type": "Darwin", "name": "Mac OS X", "architecture": "x86_64", "version": "10.16"}, "platform": "Java/Oracle Corporation/15.0.2+7-27|Kotlin/2.1.20"} created with settings MongoClientSettings{readPreference=primary, writeConcern=WriteConcern{w=null, wTimeout=null ms, journal=null}, retryWrites=true, retryReads=true, readConcern=ReadConcern{level=null}, credential=null, transportSettings=null, streamFactoryFactory=null, commandListeners=[], codecRegistry=ProvidersCodecRegistry{codecProviders=[ProvidersCodecRegistry{codecProviders=[ValueCodecProvider{}, BsonValueCodecProvider{}, DBRefCodecProvider{}, DBObjectCodecProvider{}, DocumentCodecProvider{}, CollectionCodecProvider{}, IterableCodecProvider{}, MapCodecProvider{}, GeoJsonCodecProvider{}, GridFSFileCodecProvider{}, Jsr310CodecProvider{}, JsonObjectCodecProvider{}, BsonCodecProvider{}, EnumCodecProvider{}, com.mongodb.client.model.mql.ExpressionCodecProvider@f1dd5b4, com.mongodb.Jep395RecordCodecProvider@73809e7, com.mongodb.KotlinCodecProvider@5f96f6a2]}, org.litote.kmongo.service.CustomCodecProvider@48df4071, ProvidersCodecRegistry{codecProviders=[org.litote.kmongo.jackson.JacksonCodecProvider@290e8cab]}]}, loggerSettings=LoggerSettings{maxDocumentLength=1000}, clusterSettings={hosts=[localhost:27017], srvServiceName=mongodb, mode=SINGLE, requiredClusterType=UNKNOWN, requiredReplicaSetName='null', serverSelector='null', clusterListeners='[]', serverSelectionTimeout='30000 ms', localThreshold='15 ms'}, socketSettings=SocketSettings{connectTimeoutMS=10000, readTimeoutMS=0, receiveBufferSize=0, proxySettings=ProxySettings{host=null, port=null, username=null, password=null}}, heartbeatSocketSettings=SocketSettings{connectTimeoutMS=10000, readTimeoutMS=10000, receiveBufferSize=0, proxySettings=ProxySettings{host=null, port=null, username=null, password=null}}, connectionPoolSettings=ConnectionPoolSettings{maxSize=100, minSize=0, maxWaitTimeMS=120000, maxConnectionLifeTimeMS=0, maxConnectionIdleTimeMS=0, maintenanceInitialDelayMS=0, maintenanceFrequencyMS=60000, connectionPoolListeners=[], maxConnecting=2}, serverSettings=ServerSettings{heartbeatFrequencyMS=10000, minHeartbeatFrequencyMS=500, serverListeners='[]', serverMonitorListeners='[]'}, sslSettings=SslSettings{enabled=false, invalidHostNameAllowed=false, context=null}, applicationName='null', compressorList=[], uuidRepresentation=UNSPECIFIED, serverApi=null, autoEncryptionSettings=null, dnsClient=null, inetAddressResolver=null, contextProvider=null}
2025-07-22 17:10:27.750 [cluster-ClusterId{value='68800c53c8d03b1550000000', description='null'}-localhost:27017] INFO  org.mongodb.driver.cluster - Monitor thread successfully connected to server with description ServerDescription{address=localhost:27017, type=STANDALONE, state=CONNECTED, ok=true, minWireVersion=0, maxWireVersion=25, maxDocumentSize=16777216, logicalSessionTimeoutMinutes=30, roundTripTimeNanos=16577885}
2025-07-22 17:10:27.765 [main] INFO  Application - Application started in 0.926 seconds.
2025-07-22 17:10:27.921 [DefaultDispatcher-worker-1] INFO  Application - Responding at http://0.0.0.0:8080
```

## Use

If you want to use it you should check the next link because there is a workspace with all services by Postman.

- [Workspace](https://www.postman.com/cessupx/cacao-workspace/overview)