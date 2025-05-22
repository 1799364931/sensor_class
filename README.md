# 农业温湿度传感器感知网络

## 1 介绍
本程序实现了一个温湿度传感器的后端系统，从开发板中读取对应的温湿度信息，
然后存储到数据库中，最后通过前端显示对应的日志信息。

### 1.1 预期实现功能

- 从开发板中读取数据到数据库中 ✅
- 批量获取日志(带过滤器的获取) ✅
- 修改阈值(修改告警阈值) ✅
- 实现服务器异常处理(参数验证) 📌
- 批量删除废旧日志(带过滤器的删除) 📌
- 下载日志(带过滤器) 📌
- 实现前端 📌
![img.png](img.png)

### 1.2 可能可以实现的功能
- 带地图定位(前端实现一个地图的定位 可以定位不同传感器的位置) 📌
- 反向控制传感器 📌
- 传感器通过网络传输数据(不仅仅是串口) 📌

### 1.2 项目目录
```
sensor
│ 
├── src
│   ├── main #主要源代码
|   |   ├── java
|   |   |    └── com.example.sensor
|   |   |   
|   |   └── resources #一些资源配置文件   
|   |
│   └── test #测试文件
|
├── target 
└── README.md
```

### 1.3 网页路由
```
localhost:port{
    /log{ [POST]
        /log/logId [GET/PUT/DELETE]
    }
    /log_alert [POST] 
    /log_bacth [GET]
    /log_filter [POST]
}

```

## 2 ``@Controller``接口

### 2.1 ``LogDataController`` 
实现单个日志的增删查改。

### 2.2 ``LogDataBatchController``
实现批量日志的增删查改，通过前端传入的过滤器进行日期过滤(后续考虑添加温度过滤)。

### 2.3 ``LogAlertManagerController``
实现单例类``LogAlertManager``的控制接口，通过前端的控制返回对应的日志告警范围。

## 3 ``@Service``

### 3.1 ``LogDataService``
实现日志增删查改的服务。

### 3.2 ``LogDataBatchService``
实现批量查询服务。

### 3.3 ``SerialLogService``
实现从串口中读取信息的服务，并且进行日志信息的过滤，写入数据库。