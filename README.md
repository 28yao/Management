# 部门管理系统

基于 Web 的部门管理系统，实现组织信息维护、员工数据管理、考勤打卡和请假审批的数字化管理。

## 核心特性

- **组织架构管理**：部门、职位的增删改查
- **员工管理**：员工信息维护、离职处理、密码重置
- **考勤打卡**：上班/下班打卡、迟到早退自动标记
- **请假管理**：事假/病假申请、审批流程
- **补卡申请**：忘记打卡可申请补卡
- **通知系统**：审批结果实时通知
- **权限控制**：管理员/员工角色分离

## 技术栈

| 层级 | 技术 | 版本 |
|------|------|------|
| 后端框架 | Spring Boot | 2.7.18 |
| ORM | MyBatis-Plus | 3.5.5 |
| 数据库 | MySQL | 8.x |
| 数据库迁移 | Flyway | - |
| 安全框架 | Spring Security + JWT | - |
| 前端框架 | Vue 3 | 3.5.x |
| UI 组件 | Element Plus | 2.14.x |
| 构建工具 | Vite | 8.x |

## 安装指南

### 环境要求

- JDK 8+
- Maven 3.6+
- MySQL 8.x
- Node.js 18+

### 从源码构建

**1. 克隆项目**

```bash
git clone https://github.com/28yao/Management.git
cd Management
```

**2. 打包后端**

```bash
cd backend/management-api
mvn clean package -DskipTests
```

生成文件：`target/management-api-1.0.0-SNAPSHOT.jar`

**3. 打包前端**

```bash
cd frontend/management-web
npm install
npm run build
```

生成目录：`dist/`

**4. 整合产物**

```bash
mkdir -p dist/backend dist/frontend dist/config
cp backend/management-api/target/management-api-1.0.0-SNAPSHOT.jar dist/backend/management-api.jar
cp -r frontend/management-web/dist/* dist/frontend/
```

## 使用方法

### 开发模式

**1. 创建数据库**

```sql
CREATE DATABASE management DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
```

**2. 启动后端**

```bash
cd backend/management-api
mvn spring-boot:run
```

后端运行在 `http://localhost:8080`

**3. 启动前端**

```bash
cd frontend/management-web
npm install
npm run dev
```

前端运行在 `http://localhost:3000`

### 生产部署

**1. 配置环境变量**

```bash
export DB_PASSWORD=your_database_password
export JWT_SECRET=your_jwt_secret_key
export ADMIN_PASSWORD=your_admin_password
```

**2. 启动后端**

```bash
cd dist/backend
java -jar management-api.jar
```

**3. 部署前端**

方式一：使用后端代理访问 `http://localhost:8080`

方式二：使用 Nginx（参考 `dist/config/nginx.conf`）

### 默认账号

| 角色 | 账号 | 密码 |
|------|------|------|
| 管理员 | admin | admin123 |

## 功能模块

### 管理员功能

| 功能 | 说明 |
|------|------|
| 仪表盘 | 员工总数、部门数量、今日出勤、待审批数量 |
| 部门管理 | 新增、编辑、删除部门 |
| 职位管理 | 新增、编辑、删除职位 |
| 员工管理 | 新增、编辑、离职处理、密码重置 |
| 考勤管理 | 查看考勤记录 |
| 请假审批 | 审批员工请假申请 |
| 补卡审批 | 审批员工补卡申请 |
| 通知管理 | 查看系统通知 |
| 系统配置 | 班制时间设置 |

### 员工功能

| 功能 | 说明 |
|------|------|
| 首页 | 今日打卡状态、本月统计、最近7天记录 |
| 打卡 | 上班/下班打卡 |
| 个人信息 | 查看个人基本信息 |
| 请假申请 | 提交请假申请、查看记录 |
| 补卡申请 | 提交补卡申请 |
| 我的通知 | 查看审批结果 |

## 项目结构

```
Management/
├── backend/management-api/     # 后端项目
│   ├── src/main/java/com/management/
│   │   ├── config/             # 配置类
│   │   ├── controller/         # 控制器
│   │   ├── service/            # 服务层
│   │   ├── mapper/             # 数据访问层
│   │   ├── entity/             # 实体类
│   │   ├── dto/                # 数据传输对象
│   │   ├── enums/              # 枚举类
│   │   └── exception/          # 异常处理
│   └── src/main/resources/
│       └── db/migration/       # Flyway 迁移脚本
├── frontend/management-web/    # 前端项目
│   └── src/
│       ├── api/                # API 服务
│       ├── views/              # 页面组件
│       └── router/             # 路由配置
├── dist/                       # 打包产物
│   ├── backend/                # 后端 JAR
│   ├── frontend/               # 前端静态文件
│   └── config/                 # 配置模板
└── specs/                      # 项目文档
    ├── spec.md                 # 产品需求
    ├── plan.md                 # 技术方案
    └── tasks.md                # 任务列表
```

## 文档

- [产品需求规格](specs/spec.md)
- [技术架构方案](specs/plan.md)
- [任务分解列表](specs/tasks.md)
- [部署指南](dist/README.md)
