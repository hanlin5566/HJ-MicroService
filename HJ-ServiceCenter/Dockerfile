FROM java:8-jre
MAINTAINER Hanson huhanlin.com
#添加文件到app目录下
ADD ./target/HJ-ServiceCenter.jar /app/
#启动命令参数
CMD ["java", "-Xmx200m", "-jar", "/app/HJ-ServiceCenter.jar"]
#声明允许暴露的端口
EXPOSE 8761