# 部门管理系统

基于 Web 的部门管理系统，实现组织信息维护、员工数据管理、考勤打卡和请假审批的数字化管理。

## 技术栈

### 后端

| 技术 | 版本 | 说明 |
| --- | --- | --- |
| Spring Boot | 2.7.18 | 应用框架 |
| MyBatis-Plus | 3.5.5 | ORM 框架 |
| MySQL | 8.x | 数据库 |
| Flyway | \- | 数据库迁移 |
| Spring Security | \- | 安全框架 |
| JWT | 0.11.5 | 认证方案 |

### 前端

| 技术 | 版本 | 说明 |
| --- | --- | --- |
| Vue | 3.5.x | 前端框架 |
| Vite | 8.x | 构建工具 |
| Element Plus | 2.14.x | UI 组件库 |
| Pinia | 3.x | 状态管理 |
| Vue Router | 5.x | 路由管理 |
| Axios | 1.x | HTTP 客户端 |

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
│   │   ├── exception/          # 异常处理
│   │   └── util/               # 工具类
│   └── src/main/resources/
│       ├── application.yml     # 主配置
│       └── db/migration/       # Flyway 迁移脚本
│
├── frontend/management-web/    # 前端项目
│   └── src/
│       ├── api/                # API 服务
│       ├── components/         # 公共组件
│       ├── router/             # 路由配置
│       ├── stores/             # 状态管理
│       ├── utils/              # 工具函数
│       └── views/              # 页面组件
│
└── specs/                      # 项目文档
    ├── spec.md                 # 产品需求规格
    ├── plan.md                 # 技术架构方案
    └── tasks.md                # 任务分解列表
```

## 快速开始

### 环境要求

- JDK 8+
- Maven 3.6+
- MySQL 8.x
- Node.js 18+

### 后端启动

1. 创建数据库

```sql
CREATE DATABASE management DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
```

2. 修改数据库配置

编辑 `backend/management-api/src/main/resources/application-dev.yml`，配置数据库连接信息。

3. 启动后端服务

```bash
cd backend/management-api
mvn spring-boot:run
```

后端服务将运行在 `http://localhost:8080`

### 前端启动

1. 安装依赖

```bash
cd frontend/management-web
npm install
```

2. 启动开发服务器

```bash
npm run dev
```

前端应用将运行在 `http://localhost:3000`

### 预置账号

| 角色 | 账号 | 密码 |
| --- | --- | --- |
| 管理员 | admin | admin123 |

## 功能模块

### 管理员功能

- 仪表盘：员工总数、部门数量、今日出勤、待审批数量
- 部门管理：新增、编辑、删除部门
- 员工管理：新增、编辑、离职处理、密码重置
- 考勤管理：查看考勤记录、考勤统计
- 请假审批：审批员工请假申请
- 补卡审批：审批员工补卡申请
- 系统配置：班制时间设置

### 员工功能

- 打卡：上班/下班打卡
- 个人信息：查看个人基本信息
- 请假申请：提交请假申请、查看请假记录
- 补卡申请：提交补卡申请
- 通知：查看审批结果通知

## 文档

- 产品需求规格
- 技术架构方案
- 任务分解列表