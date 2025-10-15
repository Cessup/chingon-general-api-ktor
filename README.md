# Chingon API 

[![License](https://img.shields.io/github/license/Cessup/chingon-general-api-ktor
)](https://opensource.org/licenses/Apache-2.0)
[![Workflow](https://img.shields.io/github/actions/workflow/status/Cessup/chingon-general-api-ktor/.github%2Fworkflows%2Fchingon-api-ci.yml
)](https://github.com/Cessup/chingon-general-api-ktor/actions)
[![Commit](https://img.shields.io/github/last-commit/Cessup/chingon-general-api-ktor
)](https://github.com/Cessup/chingon-general-api-ktor)

![Example Image App](images/chingon_app_banner.svg)

Chingon is an e-commerce platform. It's an API to different application that contain features to help you for make any applications.



> The project is also available [here](https://github.com/Cessup/chingon-general-api-ktor).
>
> The [`develop` branch](https://github.com/Cessup/chingon-general-api-ktor) showcase a stable version.
>

>I'm still developing it, so my plan is to create a system based on my knowledge, as I wanted to show you how I can create any system.
>If anyone needs help, I'd be happy to help.
>If you want to more information about me you can visit my website.
>- [cessup.com](https://www.cessup.com)

## Flow Diagram

This diagram shows the business model that exists in the system. It is a tool for understanding the business rules that exist.

```mermaid
  ---
title: Flow Diagram about business
---
flowchart TD
    subgraph session
    A((Start)) --> AUTH1[/User data/]
    AUTH1 --> AUTH2{is user exist?}
    AUTH2 -->|No| AUTH3[Register]
    AUTH2 --->|YES| AUTH4{is password correct?} 
    AUTH3 --> AUTH2
    AUTH4 ------>|YES| AUTH0[Authenticate]
    AUTH4 --->|NO| AUTH5{Forgot password?}
    AUTH5 -->|YES| AUTH6[Recovery] 
    AUTH5 ---->|NO| AUTH4
    AUTH6 ----> AUTH4
    end
    subgraph Private
    subgraph Restaurant
    AUTH0--> RES1[Receiver Order]
    RES1 --> RES2{Is it drink?}
    RES2 --> |YES| RES3[make it on beverage station]
    RES2 --> |NO| RES4{Is it meal?}
    RES4 --> |YES| RES5[make it on the line]
    RES4 --> |NO| RES6[Make it by workmate]
    RES3 --> RES7[Packaging]
    RES5 --> RES7
    RES6 --> RES7
    RES7 --> RES8(((END)))
    end
    subgraph Commerce
    AUTH0 --> COMM1[Products]
    COMM1 --> COMME1{are products there?}
    COMME1 --->|YES| COMM2[Sales]
    COMM2 ---> COMM3[Orders]
    COMM3 ---> COMME31{Is Order ended?}
    COMME31 --->|YES| COMM4[Payments]
    COMME31 --->|NO| COMM2
    COMM4 ---> COMME41{is order completed?}
    COMME41 ---->|YES| COMME42[Shipping]
    COMME41 ---->|NO| COMME43{Is order cancel}
    COMME43 ---->|YES| COMME44[Return money]
    COMME43 ---->|NO| COMME45[Check status]
    COMME42 ----> COMM5[Pick up]
    COMME42 ----> COMM6[Delivery]
    COMME44 ----> COMM7(((End)))
    COMME45 ---->COMME41 
    COMM5 -----> COMM7
    COMM6 -----> COMM7
    COMME1 -->|NO| COMM7
    end
    subgraph Profile
    AUTH0--> PRO0[User Information]
    PRO0--> PRO1{do you change user information?}
    PRO1---> |YES| PRO2{Do you email or password?}
    PRO1---> |NO| PRO3(((End)))
    PRO2----> |YES| PRO4[Close session]
    PRO2----> |NO| PRO0
    PRO4-----> PRO3
    end
    end
```

## Pre-Requirements
Before your run, you need to have a mongo db can be local or remote. If you hava any mongo database you can configure that in application.yaml.

if you need more information about it you can check the next link
- [Mongo Documentation](https://www.mongodb.com)


## Features
Here's a list of features included in this project:

| Name                                                                                          | Description                                                                                           |
|-----------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------|
| [Session](https://www.postman.com/cessupx/chingon-workspace/folder/goo6ezk/session-services)  | There are all services about session like sign in or sing up.                                         |
| [Product](https://www.postman.com/cessupx/chingon-workspace/folder/15fk1y4/products-services) | There are all services about products like insert, update, delete and every thing about products.     |
| [Eatable](https://www.postman.com/cessupx/chingon-workspace/folder/fjmlivp/eatable-services)  | There are all services about eatable like drinks or meals.                                            |
| [Sales](https://www.postman.com/cessupx/chingon-workspace/overview)                           | There are all services about sales like price or promotion. (It is in progress to do)                 |
| [Orders](https://www.postman.com/cessupx/chingon-workspace/overview)                          | There are all services about orders.                                                                  |
| [Payments](https://www.postman.com/cessupx/chingon-workspace/overview)                        | There are all services about payments like communication with the bank. (It is in progress to do)     |
| [Delivery](https://www.postman.com/cessupx/chingon-workspace/overview)                        | There are all services about delivery like pick-up, on the way and delivery.(It is in progress to do) |

## How to use it

If you want to use it you should check the next link because there is a workspace with all services by Postman.

- [Workspace](https://www.postman.com/cessupx/chingon-workspace/overview)

## Technologies

The app uses the following multiplatform dependencies in its implementation:

- [Ktor](https://ktor.io/) for networking
- [Koin](https://insert-koin.io) for dependency injection
- [MongoDB Driver](https://www.mongodb.com/docs/languages/kotlin/) It is to use the database

> The libraries are going to update when any project will absolute but before data we'll notify you. But you are free to use anything libraries in thins project because that is just a example.


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


## Development
This project is not finished, and this repository helps continue the project's development. Below is the standard to follow for new features.

```mermaid
---
config:
  logLevel: 'debug'
  theme: 'base'
  gitGraph:
    rotateCommitLabel: true
---
gitGraph
   commit id: "Initial commit"
   commit id: "update: README.md"
   branch release
   branch develop
   branch hotfix
   branch test
   checkout develop
   commit id: "Code base"
   branch feature_any
   commit id: "Add Models"
   commit id: "Add Repository"
   commit id: "Add Services"
   checkout develop
   merge feature_any tag: "Integration" type: HIGHLIGHT
   checkout test
   merge develop id: "Pull changes"
   commit id: "Test to any feature"
   checkout feature_any
   merge develop id: "Pull develop" 
   commit id: "Repeat process"
   checkout develop
   commit id: "repeat process"
   checkout release
   merge test tag: "New version" type: HIGHLIGHT
   checkout main
   merge release id:"Stable Version"
   checkout release
   checkout hotfix
   merge release id:"Any fail"
   commit id:"Fix fire time"
   checkout release
   merge hotfix id:"Update Version" type: HIGHLIGHT
   checkout main
   merge release id:"Update Stable Code"
   checkout release
   
```
