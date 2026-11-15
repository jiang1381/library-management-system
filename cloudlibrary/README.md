# 云借阅-图书管理系统（SSM）

广西科技师范学院《企业级项目实践》实验报告6 对应的完整工程实现：**Spring 5.2.8 + SpringMVC + MyBatis 3.5.2 + JSP + MySQL 8 + Tomcat 9**。

> 说明：实验报告中的技术栈是 **SSM（Spring + SpringMVC + MyBatis）+ JSP + Tomcat**，不是 Spring Boot。
> 本项目严格按报告中的 `pom.xml`、`SpringConfig`、`SpringMvcConfig`、`BookMapper`、`BookServiceImpl`、`BookController`、
> `UserController`、`ResourcesInterceptor` 等源码还原，因此没有使用 Spring Boot。

---

## 1. 环境要求

| 项目 | 版本 / 说明 |
| --- | --- |
| JDK | 1.8 及以上（本机验证：JDK 17 编译为 1.8 字节码） |
| Maven | 3.6+（本机：3.9.6） |
| MySQL | 8.0（本机：8.0.44，root / 123456） |
| Tomcat | 9.x（必须是 9，Tomcat 10 用 jakarta 包名，本项目的 `javax.servlet` 无法运行） |
| 部署路径 | `/cloudlibrary` |

## 2. 数据库准备

```bash
mysql -uroot -p123456 < sql/cloudlibrary.sql
```

脚本会创建数据库 `cloudlibrary` 及三张表：`book`（图书）、`user`（用户/员工）、`record`（借阅记录），
并写入测试数据。

数据库连接配置在 `src/main/resources/jdbc.properties`：

```properties
jdbc.driverClassName=com.mysql.cj.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/cloudlibrary?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai
jdbc.username=root
jdbc.password=123456
```

## 3. 默认账号

| 角色 | 账号 | 密码 | 说明 |
| --- | --- | --- | --- |
| 管理员 | `root` | `123456` | 登录框可填用户名 `root`，也可填邮箱 `root@cloudlibrary.com` |
| 普通用户 | `张三` | `123456` | 邮箱 `zhangsan@itcast.cn` |
| 普通用户 | `李四` | `123456` | 邮箱 `lisi@itcast.cn` |

登录框是「账号」，后端 `UserMapper.login` 的查询条件是
`user_email = #{email} OR user_name = #{email}`，所以**邮箱和用户名都能登录**，
并且会排除已办理离职（`user_status = '1'`）的账号。

## 4. 编译与运行

### 4.1 打包

```bash
mvn clean package
```

生成 `target/cloudlibrary.war`。若当前机器没有外网，需加 `-o`（离线）参数：
`mvn -o clean package`。

### 4.2 方式一：部署到自己的 Tomcat 9

把 `target/cloudlibrary.war` 复制到 Tomcat 的 `webapps` 目录，启动 Tomcat，访问：

```
http://localhost:8080/cloudlibrary/
```

### 4.3 方式二：使用项目内的运行目录（推荐，已配置好）

项目里的 `deploy/` 是一个 Tomcat 的 **CATALINA_BASE**（只含配置和部署文件，Tomcat 程序本体仍用
`E:\apache-tomcat-9.0.118-windows-x64\apache-tomcat-9.0.118`）。

Windows 下直接双击或在命令行执行：

```bat
start.bat     :: 启动（前台运行，关闭窗口即停止）
stop.bat      :: 停止
build.bat     :: 重新编译打包，并把 war 复制到 deploy\webapps
```

也可以手动执行：

```bat
set JAVA_HOME=E:\jdk\jdk-17
set CATALINA_HOME=E:\apache-tomcat-9.0.118-windows-x64\apache-tomcat-9.0.118
set CATALINA_BASE=<项目路径>\deploy
call "%CATALINA_HOME%\bin\catalina.bat" run
```

访问地址：<http://localhost:8080/cloudlibrary/>（会自动跳到登录页）。

## 5. 目录结构

```
cloudlibrary
├─ pom.xml                      Maven 配置（war 打包）
├─ sql/cloudlibrary.sql         建库建表 + 测试数据
├─ start.bat / stop.bat / build.bat
├─ deploy/                      Tomcat CATALINA_BASE
│  ├─ conf/                    Tomcat 配置（server.xml 等）
│  └─ webapps/cloudlibrary.war 部署包
├─ src/main/java/com/itheima
│  ├─ config/                  SpringConfig、SpringMvcConfig、JdbcConfig、
│  │                           MyBatisConfig、ServletContainersInitConfig、EncodingFilter
│  ├─ controller/              BookController、UserController、RecordController
│  ├─ domain/                  Book、User、Record
│  ├─ entity/                  PageResult、Result
│  ├─ interceptor/             ResourcesInterceptor（登录/权限拦截）
│  ├─ mapper/                  BookMapper、UserMapper、RecordMapper
│  └─ service/impl/            BookServiceImpl、UserServiceImpl、RecordServiceImpl
├─ src/main/resources
│  ├─ jdbc.properties          数据库连接
│  ├─ ignoreUrl.properties     免登录/普通用户可访问的路径白名单
│  ├─ log4j.properties         SQL 日志（com.itheima.mapper = DEBUG）
│  └─ com/itheima/mapper/*.xml BookMapper.xml、UserMapper.xml、RecordMapper.xml
└─ src/main/webapp
   ├─ index.jsp                入口，转发到登录页
   ├─ admin/                   login.jsp、main.jsp、books.jsp、books_new.jsp、
   │                           book_borrowed.jsp、book_modal.jsp、record.jsp、user.jsp
   ├─ css/ js/ img/            AdminLTE + Bootstrap 后台模板资源、my.js、pagination.js
```

## 6. 功能清单

| 模块 | 说明 |
| --- | --- |
| 登录 / 注销 | Session 保存 `USER_SESSION`，账号可为邮箱或用户名，离职账号禁止登录 |
| 新书推荐 | 首页 iframe 默认展示最新上架的 5 本书（按上架时间倒序） |
| 图书借阅 | 分页 + 按名称/作者/出版社模糊查询；管理员可见「新增/编辑/删除」，普通用户只可借阅 |
| 图书新增 / 编辑 | Ajax 提交，上架时间自动取当天；`BookMapper.xml` 用 `<trim>`+`<if>` 动态更新 |
| 图书删除 | 逻辑删除（`book_status='3'`），管理员权限校验，借阅中/归还中不可删除 |
| 借阅 | 记录借阅人、借阅时间（当天）、预计归还时间；前端 `cg()` 校验归还日期不早于今天 |
| 归还 | 仅借阅人本人可发起，状态改为「归还中」(2) |
| 归还确认 | 管理员确认后状态回到「可借阅」(0)，清空借阅信息，并写入一条借阅记录 |
| 当前借阅 | 管理员看自己借阅中 + 所有待确认归还；普通用户只看自己的 |
| 借阅记录 | 分页 + 按借阅人/图书名查询；管理员可删除记录 |
| 员工管理 | 仅管理员：新增员工（姓名/邮箱/密码/角色）、办理离职（状态改为离职） |
| 分页插件 | PageHelper 5.1.10 + 自定义 `pagination.js`，翻页时携带查询条件 |
| 权限控制 | `ResourcesInterceptor` 拦截 `/**`，放行 `/css/**`、`/js/**`、`/img/**` |

## 7. 与实验报告的对应关系（几处必要修正）

代码按报告还原，以下几处是为让「报告里写的效果」真正生效而做的最小修正：

1. **登录账号**：报告里管理员是「黑马程序员 / 12345」，按你的要求改为 **root / 123456**；
   `UserMapper.login` 增加「用户名或邮箱」匹配，因此登录框填 `root` 即可。
2. **归还确认清空借阅信息**：报告中的 `BookServiceImpl.returnConfirm` 把借阅人置为 `null`，
   而 `BookMapper.xml` 的动态 SQL 判断是 `!= null`，null 会被忽略、根本不会更新。
   这里改成空字符串 `""`，才能真正把借阅人、借阅时间、预计归还时间清空。
3. **归还确认生成借阅记录**：报告 5.1 写的是「归还确认时同时生成借阅记录」，
   但原 `BookServiceImpl` 只注入了 `BookMapper`。这里注入 `RecordMapper`，在确认归还时
   用 `addRecord` 写入一条记录，借阅记录页面才能看到真实数据。
4. **权限白名单**：原 `ignoreUrl.properties` 里写的是 `/search`、`/findById` 等短路径，
   拦截器用 `uri.indexOf(url) >= 0` 判断，导致普通用户访问 `/user/search` 也能通过
   （报告里却写「拦截生效」）。改为带模块前缀的路径（`/book/search`、`/record/searchRecords` 等），
   普通用户访问员工管理才会被真正拦截。
5. **数据库字段**：`UserMapper.xml` 的 `editUser` 用到 `user_hiredate`、`user_departuredate`，
   但原 SQL 备份里 user 表没有这两列。建表脚本补齐了这两个可空字段，办理离职时会记录离职日期。
6. **员工角色值**：`user.jsp` 的角色下拉框原值是 `普通用户`（中文），与后端 `admin`/`user` 判断不一致，
   改为 `value="user"`；`record.jsp` 里的 `role == 'ADMIN'` 改为 `'admin'`。
7. **pom.xml**：报告里用的是 `tomcat7-maven-plugin` 跑内置 Tomcat，它不支持 JDK 17 且需要联网下载。
   改为标准的 `maven-war-plugin` 打包，部署到 Tomcat 9（与报告「配置 Tomcat 8.5 服务器部署」等价）。

## 8. 常见问题

- **端口 8080 被占用**：本机若已有 Tomcat 在跑，先停掉它；或修改 `deploy/conf/server.xml` 中
  `<Connector port="8080" ...>` 与 `<Server port="8005" ...>` 两个端口号，再同步修改访问地址。
- **离线环境打包**：`mvn -o clean package`。本项目依赖已全部存在于本机仓库
  `D:\maven_Local_repo`（settings.xml 中配置的 localRepository），无需联网。

- **启动报 `ClassNotFoundException: javax.servlet.*`**：用了 Tomcat 10，请换成 Tomcat 9。
- **页面 500、JSP 编译失败**：Tomcat 用 JRE 运行时缺少编译器，请用 JDK（含 `javac`）启动 Tomcat。
- **中文乱码**：确认 `EncodingFilter` 生效、数据库/表为 `utf8mb4`、`jdbc.url` 带 `characterEncoding=utf-8`。
- **登录后一直跳回登录页**：检查 `main.jsp` 是否通过 `/cloudlibrary/` 访问（Session 与路径相关），
  以及数据库 `user_status` 是否为 `0`。
- **翻页后查询条件丢失**：翻页由 `my.js` 的 `changePage` 拼隐藏表单提交，`bookVO`/`recordVO`
  由各页面底部的脚本回填，请勿删除页面底部 `pageargs`/`bookVO` 相关脚本。
