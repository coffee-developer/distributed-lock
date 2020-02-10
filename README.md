# distributed-lock
基于redis+lua脚本实现的分布式锁,支持类似spring cache读取注解el表达式动态生成分布式key值  
支持声明式跟编码式分布式锁设置

## 如何安装
第一步 mvn clean install -Dmaven.test.skip=true 
第二步 在项目pom.xml加上
```$xslt
<dependency>
    <groupId>com.github.lock</groupId>
    <artifactId>lock</artifactId>
    <version>1.0</version>
</dependency>
```

## 如何使用
查看example项目