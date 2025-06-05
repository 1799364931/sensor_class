package com.example.sensor.service;

import com.example.sensor.pojo.LogAlertManager;
import com.example.sensor.pojo.LogData;
import com.example.sensor.repository.LogDataRepository;
import jssc.SerialPort;
import jssc.SerialPortException;
import org.hibernate.IdentifierLoadAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Properties;

@Service
public class SerialLogService {
    private final SerialPort serialPort;
    private final LogDataRepository logDataRepository;
    private final LogAlertManager logAlertManager;

    @Autowired
    public SerialLogService(LogDataRepository logDataRepository,LogAlertManager logAlertManager) {
        this.logDataRepository = logDataRepository;
        this.logAlertManager = logAlertManager;

        SerialPort serialPortTemp;
        Properties properties = new Properties();
        try {
            // 读取配置文件
            properties.load(new FileInputStream("config.properties"));
            String portName = properties.getProperty("serial.port");
            // 初始化串口
            serialPortTemp = new SerialPort(portName);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("无法加载配置文件，使用默认端口...");
            // 如果配置文件加载失败，使用默认端口
            serialPortTemp = new SerialPort("COM1"); // 替换为你的默认串口
        }
        serialPort = serialPortTemp;
    }

    public void startListening() {
        new Thread(() -> {
            try {
                serialPort.openPort();
                serialPort.setParams(115200, 8, 1, 0); // 设置波特率、数据位、停止位、校验位
                StringBuilder buffer = new StringBuilder();
                while (true) {

                    byte[] data = serialPort.readBytes(); // 读取串口数据
                    if (data != null) {
                        buffer.append(new String(data));
                        if (buffer.toString().contains("\r\n")) { // 当数据完整时才处理
                            saveLog(buffer.toString().split(","));
                            buffer.setLength(0); // 清空缓冲区
                        }
                    }
                }
            } catch (SerialPortException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private LogData parserLogMessage(String[] logMessage){
        /*
            格式是: Temp = XX.XX , Humidity = XX.XX
         */
        for (String s : logMessage) {
            System.out.println(s);
        }
        if(logMessage.length < 2){
            return null;
        }

        var logData = new LogData();
        logData.setLogTime(new Timestamp(System.currentTimeMillis()));
        for(int i = 0 ; i< logMessage.length ; i++){
            //解析字符串
            var splitRes = logMessage[i].split("\\s*=\\s*");

            if(i == 0){
                //Temp
                double temp = 0;
                try{
                    temp = Double.parseDouble(splitRes[1]);
                }catch (NumberFormatException e){
                    System.out.println("转换失败: " + e.getMessage());
                }
                logData.setTemperature(temp);
            }

            if(i == 1){
                //Humidity
                double humidity = 0;
                try{
                    humidity = Double.parseDouble(splitRes[1]);
                }catch (NumberFormatException e){
                    System.out.println("转换失败: " + e.getMessage());
                }
                logData.setHumidity(humidity);
            }
        }
        return logData;
    }

    public void saveLog(String[] logMessage) {
        var logData = parserLogMessage(logMessage);
        if (logData != null) {
            logData.setAlert(logAlertManager.isAlert(logData));
            logDataRepository.save(logData);
        }else{
            System.out.println("FAIL!");
        }
    }

}
