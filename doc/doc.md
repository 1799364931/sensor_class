# 农业温湿度传感器感知网络

## 1 项目介绍

### 1.1 项目简介
本项目旨在构建一个农业温湿度传感器感知网络，利用传感器收集环境数据，并通过网络将数据传输到云端进行分析和处理。
该系统可以帮助农民实时监测农田的温湿度变化，从而优化作物生长条件，提高农业生产效率。

### 1.2 技术栈
- **传感器**：本项目使用谷雨科技的开发板进行温湿度数据采集。
- **通信协议**：暂且采用串口通信协议，后续可能会改为更适合的协议。
- **后端**: 使用``Java Spring Boot``框架开发后端服务，处理传感器数据并提供API接口。
- **前端**: 使用``html + css + js``开发前端界面，展示传感器数据和分析结果。

## 2 项目配置
### 2.1 硬件配置
本项目需要利用上述开发板设备，烧录相应的固件以实现温湿度数据采集功能。具体的硬件配置和连接方式请参考设备手册。
### 2.2 软件配置
本项目的后端服务需要配置数据库连接、Java环境等。具体的配置步骤如下：

#### 2.2.1 Java环境配置
此项略。

#### 2.2.2 数据库配置
本项目使用MySQL数据库存储传感器数据。请按照以下步骤进行配置：
+ 安装MySQL数据库并为数据库用户提供必要的权限。
+ 在本项目的``application.properties``文件中修改配置数据库的连接信息，例如：
```properties
#database configuration
spring.datasource.url = jdbc:mysql://localhost:3306/sensor?useSSL=false&serverTimezone=UTC&characterEncoding=utf8&useUnicode=true&allowPublicKeyRetrieval=true
spring.datasource.username = sensor
spring.datasource.password = password
spring.datasource.driver-class-name = com.mysql.cj.jdbc.Driver
```
请将``spring.datasource.url``项的``localhost:3306/sensor``替换为实际的数据库地址和数据库名称，同时
将``username``和``password``替换为实际的数据库用户名和密码。

#### 2.2.3 后端串口通信配置
本项目后端使用串口通信协议与传感器进行数据交互。请按照以下步骤进行配置：
+ 项目已添加对应的依赖库，无需手动添加。
+ 保证运行主机上已安装``Jlink``驱动。
+ 在``application.properties``文件中配置串口参数，例如：
```properties
#将 COM4 替换为实际的串口号
serial.port = COM4
```

#### 2.2.4 后端服务配置
本项目后端服务使用Spring Boot框架开发。请按照以下步骤进行网络配置：
+ 在``application.properties``文件中配置服务端口，例如：
```properties
#将 8080 替换为实际的服务端口
server.port = 8080
```

## 3 核心功能

+ **数据采集** 
  - 通过传感器采集温湿度数据，并将数据发送到后端服务。
  - 后端服务接收数据并存储到数据库中。
+ **数据展示**
  - 前端界面展示传感器采集的温湿度数据。
  - 提供图表和统计信息，帮助用户了解环境变化趋势。
  - 提供日志过滤筛选功能，用户可以按时间、温湿度等条件筛选数据。
+ **预警系统**
  - 当温湿度数据超过设定阈值时，系统自动发送预警通知。
  - 用户可以设置自定义的预警阈值。
+ **数据转存**
  - 支持将传感器数据导出为Excel格式，方便用户进行离线分析。

## 4 后端接口
后端接口请运行项目后访问[接口文档](http://localhost:8080/swagger-ui.html)。

## 5 云部署
略。