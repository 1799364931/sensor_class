# 农业温湿度传感器感知网络

## 1 介绍
本程序实现了一个温湿度传感器的后端系统，从开发板中读取对应的温湿度信息，
然后存储到数据库中，最后通过前端显示对应的日志信息。

### 1.1 预期实现功能

- 从开发板中读取数据到数据库中 ✅
- 批量获取日志(带过滤器的获取) ✅
- 修改阈值(修改告警阈值) ✅
- 实现服务器异常处理(参数验证) ✅
- 批量删除废旧日志(带过滤器的删除) 📌
- 下载日志(带过滤器) 📌
- 实现前端 ✅
![img.png](img.png)

### 1.2 可能可以实现的功能
- 带地图定位(前端实现一个地图的定位 可以定位不同传感器的位置) 📌
- 反向控制传感器 📌
- 传感器通过网络传输数据(不仅仅是串口) 📌
- 用户登陆注册，鉴权 📌

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
localhost:port
```

## 2 ``@Controller``接口
此处是后端所实现的接口，前端实现所需要的信息请从对应的``URL``中构造对应的``HTTP``请求，同时包含对应的``json``请求体进行
后端的信息请求。

后端存储的时间戳均为UTC时区，前端需要将其转换为对应的时区(前端可以通过``JS``获取当前时区，然后转换对应的时间戳)。

可以通过运行项目后，进入[接口文档](http://localhost:5000/swagger-ui/index.html)查看更多接口信息。

### ~~2.1 ``LogDataController``~~ 
~~实现单个日志的增删查改。~~

### 2.2 ``LogDataBatchController``
实现批量日志的获取、统计、删除等功能。

#### 2.2.1 获取批量的日志信息
```
接口名称: 获取批量的日志信息 [日期格式: yyyy-MM-dd'T'HH:mm:ss]
请求方式: POST
请求 URL: /log_batch
请求头:
  - Content-Type: application/json
请求体:
{
  "startDate": "2025-01-01T00:00:00",
  "endDate":"2025-06-01T00:00:00",
  "temperatureMax": 30,
  "temperatureMin": 20,
  "humidityMax":58.4,
  "humidityMin":58.4,
  "isAlert":2
}

成功响应数据:
{
	"code": 200,
	"message": "success",
	"data": [
		{
			"logId": 51,
			"logTime": "2025-05-22T11:42:15.577+00:00",
			"temperature": 28.9,
			"humidity": 58.4,
			"alert": true
		},
		{
			"logId": 52,
			"logTime": "2025-05-22T11:42:16.578+00:00",
			"temperature": 28.9,
			"humidity": 58.4,
			"alert": true
		},
		{
			"logId": 53,
			"logTime": "2025-05-22T11:42:17.577+00:00",
			"temperature": 28.9,
			"humidity": 58.4,
			"alert": true
		}
	]
}
```

### 2.3 ``LogAlertManagerController``
实现告警管理器的控制，获取当前告警设置以及设置告警范围。

#### 2.3.1 获取当前告警设置
```
接口名称: 设置告警范围
请求方式: GET
请求 URL: api/log_alert
请求头:
  - Content-Type: application/json
成功响应数据:
{
	"code": 200,
	"message": "success",
	"data": {
		"setTime": "2025-05-24T07:00:23.808+00:00",
		"temperatureMax": 10,
		"temperatureMin": 0,
		"humidityMax": 100,
		"humidityMin": 0
	}
}
```

#### 2.3.2 设置当前告警设置
```
接口名称: 设置告警范围
请求方式: POST
请求 URL: api/log_alert
请求头:
  - Content-Type: application/json
请求体:
{
    "temperatureMax": 50,
    "temperatureMin": 0,
    "humidityMax": 60,
    "humidityMin": 0
} 

成功响应数据:
{
	"code": 200,
	"message": "success",
	"data": {
		"setTime": "2025-05-24T07:12:21.748+00:00",
		"temperatureMax": 50,
		"temperatureMin": 0,
		"humidityMax": 60,
		"humidityMin": 0
	}
}
```

#### 2.4 统计日志信息
```
接口名称: 统计日志信息 [根据日志ID列表统计]
请求方式: POST
请求 URL: api/log_batch/statistic
请求头:
  - Content-Type: application/json
请求体:
{
    "ids":[1,2,3,4,5,6,7]
}

成功响应数据:
{
	"code": 200,
	"message": "success",
	"data": {
		"averageTemperature": 28.800000000000004,
		"averageHumidity": 57.62857142857143,
		"maxTemperature": 28.8,
		"minTemperature": 28.8,
		"maxHumidity": 57.7,
		"minHumidity": 57.6,
		"alertCount": 7
	}
}
```
#### 2.5 删除日志信息
```

接口名称: 删除日志信息 [根据日志ID列表删除]
请求方式: POST
请求 URL: api/log_batch/delete
请求头:
  - Content-Type: application/json
请求体:
{
    "ids":[1,2,3,4,5,6,7]
}
成功响应数据:
{
    "code": 200,
    "message": "success",
    "data": null
}
```


## 3 前端实现

### 3.1 基本显示功能

+ 首先实现最基本的显示，即不带过滤器(前端写死过滤逻辑)的显示，在网页中显示批量的日志。同时，网页并非静态写死的，
``JS``脚本应该轮询数据库信息，如果新增了对应的日志，要及时刷新网页。
(此处的逻辑可以写死成一个巨大的范围，比如1970年1月1日到2100年1月1日、温度从-10000 ~ 10000，湿度从0 ~ 100)
+ 其次实现带过滤器的显示，即提供**温度范围**、**湿度范围**的输入栏，以及**是否为告警日志**的勾选栏，
用户可以选择对应的范围，然后前端获取之后构造一个对应的json发送给后端，得到批量的日志。
+ 最后实现一个侧边栏，通过``年 -> 月 -> 日 -> 小时``四级目录，让用户更方便地进行日期过滤，同时上述功能也应保留。
由于此时网页的侧边栏是动态的，所以``HTML``文件需要从``JS``中动态构建(获取一头一尾的日期，然后构建)。

### 3.2 告警范围选择

+ 增加一个告警范围自定义，用户可以通过前端自定义后续的日志告警范围，其实现和上述相同，只需要提交对应json到对应的接口即可。

### 3.3 统计功能

+ 点击统计功能后，每个日志前增加一个勾选栏目，用户勾选完需要统计的日志后确认，前端发送被勾选的日志到后端，后端统计出
被勾选日志的信息(包含平均温度，平均湿度，最高温，最低温)返回给前端。
+ (可能可以实现)，返回一个温度趋势表。