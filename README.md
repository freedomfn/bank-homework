# 银行交易系统

## 项目启动
```aidl
到docker目录中使用如下命令构建镜像：
docker build -t cwj/bank:v1 .

然后启动镜像：
docker run -it -p 8080:8080 cwj/bank:v1

打开localhost:8080
在登录页上随便输一个用户名密码就可以登录了。
```

## 文档列表
* 脑图：[link](./docs/银行交易系统.png)
* 操作视频：[link](./docs/操作视频.mov)
* 测试用例：[link](./docs/测试用例.docx)
* 压测报告：[link](./docs/压测报告.docx)
* 

## 模块指引
```aidl
├── docker                                  docker打包文件及命令
├── k8s                                     k8s编排文件及命令
├── docs                                    文档附件
├── README.md                               入口指引
├── src                                     代码目录
```

## 类库
```aidl
spring-boot-starter-thymeleaf   模板引擎
guava                           本地缓存
Hibernate Validator             数据校验
Validation API                  数据校验
mockito-core                    单测相关
spring-boot-starter-test        单测相关
```
