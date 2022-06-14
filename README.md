#### 项目结构
项目采用按功能分模块的开发方式，结构如下

- `admin-common` 为系统的公共模块，各种工具类，公共配置存在该模块

- `admin-system` 为系统核心模块也是项目入口模块，也是最终需要打包部署的模块

- `admin-logging` 为系统的日志模块，其他模块如果需要记录日志需要引入该模块

- `admin-tools` 为第三方工具模块，包含：图床、邮件、云存储、本地存储、支付宝

- `admin-generator` 为系统的代码生成模块，代码生成的模板在 system 模块中


```
本系统鉴权流程：
   1、登录接口：登录成功构建Authentication并设置到securityContext，并设置jwt token
   2、TokenFilter，校验token，根据token构建Authentication并设置到securityContext
   3、UserDetailsServiceImpl，根据实际业务获取用户信息

在单向关系中没有mappedBy,主控方相当于拥有指向另一方的外键的一方。
    1.一对一和多对一的@JoinColumn注解的都是在“主控方”，都是本表指向外表的外键名称。
    2.一对多的@JoinColumn注解在“被控方”，即一的一方，指的是外表中指向本表的外键名称。
    3.多对多中，joinColumns写的都是本表在中间表的外键名称，
                inverseJoinColumns写的是另一个表在中间表的外键名称。

双向关系中拥有mappedBy的一方相当于放弃维护关系，mappedBy的值则是当前方在关系维护方中的属性
    双向一对一关联的规则；
        1. @JoinColumn注解只能放置在映射到包含连接列的表的实体上。
        2. mappedBy元素应该在没有定义连接列的实体的@OneToOne注解中指定，就是没有持有关系的一方使用
        3. 双向一对一映射关系中只能有一方使用mappedBy属性
