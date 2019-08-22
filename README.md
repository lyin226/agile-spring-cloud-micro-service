
 说明文档

一个入门的基于spring boot的服务提供者服务消费者的demo
03-spring-cloud-provider  服务提供者
03-spring-cloud-consumer  服务消费者
分别启动两个服务，访问 http://localhost:8000/user/1和http://localhost:8010/user/1，消费者服务可以通过RestTemplate调用提供者服务的API
测试整合Actuator，访问 http://localhost:8000/health/

微服务注册与发现的demo
04-spring-cloud-eureka Eureka注册中心服务
04-spring-cloud-eureka-ha Eureka注册中心服务(集群模式)
04-spring-cloud-provider 服务提供者，注册到Eureka集群
04-spirng-cloud-consumer 服务消费者，注册到Eureka集群
04-spring-cloud-eureka-authenticating 添加用户认证的注册中心服务
04-spring-cloud-provider-my-metadata 服务提供者，自定义元数据服务
04-spring-cloud-provider-my-metadata 服务消费者，自定义元数据服务
启动04-spring-cloud-eureka，访问http://localhost:8761/,可以看到Eureka界面。
测试微服务注册到Eureka Server，启动04-spring-cloud-eureka 、04-spring-cloud-provider、04-spirng-cloud-consumer，访问http://localhost:8761/，可以通过Eureka界面看到服务提供者和服务消费者注册到注册中心。
测试微服务注册中心服务的高可用，集群模式，启动04-spring-cloud-eureka和04-spring-cloud-eureka-ha两个服务
测试注册中心添加用户认证，启动04-spring-cloud-eureka-authenticating，访问http://localhost:8763/
测试自定义元数据，启动04-spring-cloud-eureka、04-spring-cloud-provider-my-metadata、04-spring-cloud-consumer-my-metadata，访问http://localhost:8761/eureka/apps，查看Eureka的metadata，访问http://localhost:8010/user/user-instance/，查看使用DiscoveryClient的API获取微服务的各种信息，其中包括标准元数据和自定义元数据。

为服务消费者整合Ribbon
启动04-spring-cloud-eureka注册中心服务，启动 05-spring-cloud-provider和05-spring-cloud-provider-ha两个服务实例注册到注册中心，启动05-spring-cloud-consumer-ribbon，访问http://localhost:8010/user/1 会有正常的返回结果，通过控制台可以看到两个微服务实例都会打印日志，多次访问http://localhost:8010/user/user-instance，通过控制台日志看到请求均匀打印到两个微服务。注意：虚拟主机名称不能包含"_"之类的字符，否则Ribbon在带哦用时会返回异常。

使用Java代码自定义Ribbon配置
启动04-spring-cloud-eureka注册中心服务，启动 05-spring-cloud-provider和05-spring-cloud-provider-ha两个服务实例注册到注册中心，启动05-spring-cloud-consumer-ribbon-custom，访问http://localhost:8010/user/1 会有正常的返回结果，通过控制台可以看到两个微服务实例都会打印日志，多次访问http://localhost:8010/user/user-instance，通过控制台日志看到请求随机到两个微服务。RibbonConfiguration配置类不能包含在@ComponentScan中，否则该类中的配置信息就被所有的@RibbonClient共享。

使用属性自定义Ribbon配置
启动04-spring-cloud-eureka注册中心服务，启动  05-spring-cloud-provider和05-spring-cloud-provider-ha两个服务实例注册到注册中心，启动05-spring-cloud-consumer-ribbon-custom-properties，访问http://localhost:8010/user/1 会有正常的返回结果，通过控制台可以看到两个微服务实例都会打印日志，多次访问http://localhost:8010/user/user-instance，通过控制台日志看到请求随机到两个微服务。

为服务消费者整合feign
启动04-spring-cloud-eureka注册中心服务，启动05-spring-cloud-provider和06-spring-cloud-consumer-feign两个服务注册到注册中心，访问http://localhost:8010/user/1，得到返回结果

自定义feign
启动04-spring-cloud-eureka注册中心服务，启动05-spring-cloud-provider和06-spring-cloud-consumer-feign-custom两个服务注册到注册中心，访问http://localhost:8010/user/1，得到返回结果

手动创建feign
启动04-spring-cloud-eureka注册中心服务，启动06-spring-cloud-provider和06-spring-cloud-consumer-feign-manual两个服务注册到注册中心，访问http://localhost:8010/user-user/1和http://localhost:8010/user-admin/1，得到返回结果

前面的Eureka实现了微服务的注册与发现，Ribbon实现了客户端侧的负载均衡，Feign实现了声明式的API调用。
基础服务故障导致级联故障的现象称为雪崩效应。雪崩效应描述的是提供者不可用导致消费者不可用，并将不可用逐渐放大的过程。
避免雪崩效应，必须有一个强大的容错机制。
如何容错
1、为网络请求设置超时。
2、使用断路器模式，可以理解为对容易导致错误的操作的代理，这种代理能够统计一段时间内调用失败的次数，并决定是正常请求依赖的服务还是直接返回，也会自动诊断服务是否已经恢复正常。
Hystrix是一个实现了超时机制和断路器模式的工具类库。

通用方式整合Hystrix
启动04-spring-cloud-eureka注册中心服务，不用启动06-spring-cloud-provider服务，启动07-spring-cloud-consumer-ribbon-hystrix服务注册到注册中心，访问http://localhost:8010/user/1，查看返回结果。

Feign整合Hystrix
启动04-spring-cloud-eureka注册中心服务，启动05-spring-cloud-provider服务，启动07-spring-cloud-consumer-feign-hystrix-fallback服务注册到注册中心，访问http://localhost:8010/user/1，查看返回结果。使用@FeignClient的fallback属性即可指定回退类。

使用Hystrix Dashboard可视化监控数据
启动07-spring-cloud-hystrix-dashboard服务，访问localhost:8030/hystrix.stream

使用Turbine聚合监控数据
启动04-spring-cloud-eureka注册中心服务，启动07-spring-cloud-consumer-ribbon-hystrix和07-spring-cloud-consumer-feign-hystrix-fallback-stream两个服务消费者，启动07-spring-cloud-hystrix-turbine聚合监控两个消费者服务，启动07-spring-cloud-hystrix-dashboard服务，url输入地址http://localhost:8031/turbine.stream，添加监控延时和标题，点击Monitor Stream，进入监控仪表盘。(采坑记录：原来为了方便代码编写，pom文件添加了存在spring-boot-starter-security依赖的jar包导致监控数据需要安全认证)。
使用微服务网关的好处：
易于监控、易于认证、减少了客户端与各个微服务之间的交互次数。

使用Zuul微服务网关
启动04-spring-cloud-eureka 04-spring-cloud-provider 08-spring-cloud-gateway-zuul三个服务，zuul网关服务配置服务提供者的路由，http://localhost:8040/04-spring-cloud-provider/user/1即可通过路由访问服务提供者服务，也可以实现负载均衡、Hystrix容错与监控。

编写自定义过滤器
启动04-spring-cloud-eureka 04-spring-cloud-provider 08-spring-cloud-gateway-zuul-filter三个服务，zuul网关服务配置服务提供者的路由，http://localhost:8040/04-spring-cloud-provider/user/1即可通过路由访问服务提供者服务，查看控制台日志，发现send.....request...日志，说明过滤器被执行了。
Zuul也可以实现回退，实现ZuulFallbackProvider接口。
Zuul高可用可以实现多个Zuul服务注册到注册中心即可。

使用Sidecar作为中转，可以应用到非JVM服务、非Spring cloud项目等
对于非JVM服务，无法注册到Eureka，可以使用Sidecar作为中转，把服务注册到注册中心。
启动04-spring-cloud-eureka 04-spring-cloud-provider 08-spring-cloud-gateway-zuul 08-spring-cloud-sidecar四个服务，访问http://localhost:8040/08-spring-cloud-sidecar/user/1，得到返回值，可以得出结论，zuul网关通过sidecar服务调用provider服务。
使用Spring Cloud Config可以统一管理微服务的配置(略)，现在更加可用的是Apollo配置服务，后面会讲解。

Spring Cloud Sleuth使用
启动10-spring-cloud-provider-trace服务，访问http://localhost:8000/user/1 ，得到返回值，查看控制台日志
Spring Cloud Sleuth可以与ELK配合使用，把sleuth的跟踪日志打印输出到elk中去,这里提供一种思路，具体实现略。

使用Zipkin 服务
Zipkin是Twitter开源的分布式跟踪系统，基于Dapper的论文设计而来，主要功能是收集系统的时序数据，从而在追踪微服务架构的系统延时等问题，还提供了一个非常友好的界面，来帮助分析追踪数据。
启动10-spring-cloud-trace-zipkin-server服务，访问http://localhost:9411/  查看界面

Spring Cloud Sleuth整合Zipkin，利用http请求直接收集数据
启动10-spring-cloud-trace-zipkin-server、10-spring-cloud-provider-trace-zipkin、10-spring-cloud-consumer-trace-zipkin三个服务，访问http://localhost:8010/user/1 可以得到正常返回结果，访问http://localhost:9411/  点击Find a trace按钮，可以查看链路跟踪的详细信息span。

Spring Cloud Sleuth整合Zipkin，利用消息中间件收集数据
启动10-spring-cloud-trace-zipkin-server-stream、10-spring-cloud-provider-trace-zipkin-stream两个服务，访问http://localhost:8000/user/1 可以得到正常返回结果，访问http://localhost:9411/  点击Find a trace按钮，可以查看链路跟踪的详细信息span。（需要自己搭建rabbitmq服务略）

存储跟踪数据到Elasticsearch
启动10-spring-cloud-trace-zipkin-server-stream-elasticsearch、10-spring-cloud-provider-trace-zipkin-stream、Elasticsearch两个服务，访问http://localhost:8000/user/1 可以得到正常返回结果，访问http://localhost:9411/  点击Find a trace按钮，可以查看链路跟踪的详细信息span,如何重启Zipkin Server,仍可查询到历史数据，说明可以正常从Elasticsearch中读取数据。（需要自己搭建Elasticsearch服务略）
