# RocketMQ

## Quick Start
This quick start guide is a detailed instruction of setting up RocketMQ messaging system on your local machine to send and receive messages.

## Prerequisite
The following softwares are assumed installed:
   1. 64bit OS, Linux/Unix/Mac is recommended;
   2. 64bit JDK 1.8+;
   3. Maven 3.2.x;
   4. Git;
   5. 4g+ free disk for Broker server
   
## Download & Build from Release
Click [here](https://www.apache.org/dyn/closer.cgi?path=rocketmq/4.4.0/rocketmq-all-4.4.0-source-release.zip) to download the 4.4.0 source release. Also you could download a binary release from [here](http://rocketmq.apache.org/release_notes/release-notes-4.4.0/).

Now execute the following commands to unpack 4.4.0 source release and build the binary artifact.

```shell
     > unzip rocketmq-all-4.4.0-source-release.zip
     > cd rocketmq-all-4.4.0/
     > mvn -Prelease-all -DskipTests clean install -U
     > cd distribution/target/apache-rocketmq
```

## Start Name Server
```shell
    > nohup sh bin/mqnamesrv &
    > tail -f ~/logs/rocketmqlogs/namesrv.log
    The Name Server boot success...
```

## Start Broker
```shell
    > nohup sh bin/mqbroker -n localhost:9876 &
    > tail -f ~/logs/rocketmqlogs/broker.log 
    The broker[%s, 172.30.30.233:10911] boot success...
```

## Send & Receive Messages
Before sending/receiving messages, we need to tell clients the location of name servers. RocketMQ provides multiple ways to achieve this. For simplicity, we use environment variable NAMESRV_ADDR
```shell
    > export NAMESRV_ADDR=localhost:9876
    > sh bin/tools.sh org.apache.rocketmq.example.quickstart.Producer
    SendResult [sendStatus=SEND_OK, msgId= ...
    
    > sh bin/tools.sh org.apache.rocketmq.example.quickstart.Consumer
    ConsumeMessageThread_%d Receive New Messages: [MessageExt...
```

### Create Topic
执行上面命令的时候可能会碰到如下报错:
```java
    //org.apache.rocketmq.client.exception.MQClientException: No route info of this topic, TopicTest
```
解决方案:
```shell
    > sh bin/mqadmin updateTopic -b localhost:10911 -t TopicTest -n localhost:9876
    create topic to localhost:10911 success.
    
    # 参数解析
    # -b broker_ip:broker_port
    # -n namesrv_ip:namesrv_port
    # -t topic_name
```

上面是手动创建Topic但是还是不行, 使用下面的方法解决
```shell
    > nohup sh mqbroker -n localhost:9876 autoCreateTopicEnable=true &
    # 启动broker的时候允许自动创建Topic
```



## Shutdown Servers
```shell
    > sh bin/mqshutdown broker
    The mqbroker(36695) is running...
    Send shutdown request to mqbroker(36695) OK
    
    > sh bin/mqshutdown namesrv
    The mqnamesrv(36664) is running...
    Send shutdown request to mqnamesrv(36664) OK
```

## Simple Message Example

- 使用RocketMQ以三种方式发送消息: 可靠同步、可靠异步和单向传输
- 使用RocketMQ来消费信息

### 1. Add Dependency
Maven:
```xml
    <dependency>
        <groupId>org.apache.rocketmq</groupId>
        <artifactId>rocketmq-client</artifactId>
        <version>4.3.0</version>
    </dependency>
```

Gradle:
```
    compile 'org.apache.rocketmq:rocketmq-client:4.3.0'
```

### 2.1 发送同步消息
可靠的同步传输用于广泛的场景，如重要的通知消息，短信通知，短信营销系统等。
```java

```

