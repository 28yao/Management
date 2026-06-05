# 部门管理系统 - 技术架构方案

## 1. 技术选型

### 1.1 技术栈总览

| 层级 | 技术 | 版本 | 说明 |
|------|------|------|------|
| 后端框架 | Spring Boot | 2.7.18 | 与现有项目一致 |
| ORM | MyBatis-Plus | 3.5.5 | 简化 CRUD 操作 |
| 数据库 | MySQL | 8.x | 主数据存储 |
| 数据库迁移 | Flyway | - | 版本化管理 SQL |
| 认证授权 | Spring Security + JWT | - | 无状态认证 |
| 前端框架 | Vue 3 | 3.5.x | 组合式 API |
| 构建工具 | Vite | 8.x | 快速开发构建 |
| UI 组件库 | Element Plus | 2.14.x | 企业级组件 |
| 状态管理 | Pinia | 3.x | 轻量级状态管理 |
| HTTP 客户端 | Axios | 1.x | 前后端通信 |

### 1.2 Java 环境约束

遵循 `AGENTS.md` 规范：
- Java 8 环境
- 所有类必须包含标准 JavaDoc 注释
- 禁止嵌套循环
- 数据库操作必须使用参数化查询
- 禁止生成 DROP TABLE/DROP DATABASE 语句

---

## 2. 项目结构

### 2.1 后端目录结构

```
backend/management-api/
├── src/main/java/com/management/
│   ├── ManagementApplication.java          # 启动类
│   ├── config/                             # 配置类
│   │   ├── SecurityConfig.java             # 安全配置
│   │   ├── JwtConfig.java                  # JWT 配置
│   │   ├── MybatisPlusConfig.java          # MyBatis-Plus 配置
│   │   └── WebConfig.java                  # 跨域配置
│   ├── controller/                         # 控制器层
│   │   ├── AuthController.java             # 认证接口
│   │   ├── DepartmentController.java       # 部门管理接口
│   │   ├── EmployeeController.java         # 员工管理接口
│   │   ├── AttendanceController.java       # 考勤打卡接口
│   │   ├── LeaveController.java            # 请假管理接口
│   │   └── NotificationController.java     # 通知接口
│   ├── service/                            # 服务层
│   │   ├── AuthService.java
│   │   ├── DepartmentService.java
│   │   ├── EmployeeService.java
│   │   ├── AttendanceService.java
│   │   ├── LeaveService.java
│   │   └── NotificationService.java
│   ├── mapper/                             # 数据访问层
│   │   ├── DepartmentMapper.java
│   │   ├── EmployeeMapper.java
│   │   ├── AttendanceMapper.java
│   │   ├── LeaveRecordMapper.java
│   │   ├── MakeupClockMapper.java
│   │   └── NotificationMapper.java
│   ├── entity/                             # 实体类
│   │   ├── Department.java
│   │   ├── Employee.java
│   │   ├── Attendance.java
│   │   ├── LeaveRecord.java
│   │   ├── MakeupClock.java
│   │   └── Notification.java
│   ├── dto/                                # 数据传输对象
│   │   ├── request/
│   │   └── response/
│   ├── vo/                                 # 视图对象
│   ├── enums/                              # 枚举类
│   │   ├── RoleEnum.java                   # 角色枚举
│   │   ├── AttendanceStatusEnum.java       # 打卡状态枚举
│   │   ├── LeaveTypeEnum.java              # 假期类型枚举
│   │   └── ApprovalStatusEnum.java         # 审批状态枚举
│   ├── exception/                          # 异常处理
│   │   ├── BusinessException.java
│   │   └── GlobalExceptionHandler.java
│   └── util/                               # 工具类
│       ├── JwtUtil.java
│       └── SecurityUtil.java
├── src/main/resources/
│   ├── application.yml                     # 主配置
│   ├── application-dev.yml                 # 开发环境配置
│   └── db/migration/                       # Flyway 迁移脚本
│       ├── V1__init_department.sql
│       ├── V2__init_employee.sql
│       ├── V3__init_attendance.sql
│       ├── V4__init_leave.sql
│       ├── V5__init_makeup_clock.sql
│       ├── V6__init_notification.sql
│       └── V7__init_admin.sql              # 预置管理员账号
└── pom.xml
```

### 2.2 前端目录结构

```
frontend/management-web/
├── src/
│   ├── main.ts                             # 入口文件
│   ├── App.vue                             # 根组件
│   ├── router/                             # 路由配置
│   │   └── index.ts
│   ├── stores/                             # Pinia 状态管理
│   │   ├── auth.ts                         # 认证状态
│   │   └── app.ts                          # 应用状态
│   ├── api/                                # API 接口封装
│   │   ├── auth.ts
│   │   ├── department.ts
│   │   ├── employee.ts
│   │   ├── attendance.ts
│   │   ├── leave.ts
│   │   └── notification.ts
│   ├── views/                              # 页面组件
│   │   ├── login/                          # 登录页
│   │   ├── admin/                          # 管理员页面
│   │   │   ├── dashboard/                  # 仪表盘
│   │   │   ├── department/                 # 部门管理
│   │   │   ├── employee/                   # 员工管理
│   │   │   ├── attendance/                 # 考勤管理
│   │   │   ├── leave/                      # 请假审批
│   │   │   └── notification/               # 通知管理
│   │   └── employee/                       # 员工页面
│   │       ├── dashboard/                  # 员工首页
│   │       ├── clock/                      # 打卡
│   │       ├── profile/                    # 个人信息
│   │       ├── leave/                      # 请假申请
│   │       └── notification/               # 我的通知
│   ├── components/                         # 公共组件
│   │   ├── Layout/                         # 布局组件
│   │   ├── Table/                          # 表格组件
│   │   └── Form/                           # 表单组件
│   └── utils/                              # 工具函数
│       ├── request.ts                      # Axios 封装
│       └── auth.ts                         # Token 管理
├── package.json
└── vite.config.ts
```

---

## 3. 数据库设计

### 3.1 ER 图

```
┌─────────────┐       ┌─────────────┐
│ department  │       │  employee   │
├─────────────┤       ├─────────────┤
│ id (PK)     │◄──┐   │ id (PK)     │
│ name        │   └───│ dept_id(FK) │
│ created_at  │       │ emp_no      │
│ updated_at  │       │ name        │
└─────────────┘       │ phone       │
                      │ email       │
                      │ hire_date   │
                      │ position    │
                      │ account     │
                      │ password    │
                      │ role        │
                      │ status      │
                      │ created_at  │
                      │ updated_at  │
                      └──────┬──────┘
                             │
        ┌────────────────────┼────────────────────┐
        │                    │                    │
        ▼                    ▼                    ▼
┌──────────────┐    ┌──────────────┐    ┌──────────────┐
│  attendance  │    │ leave_record │    │ makeup_clock │
├──────────────┤    ├──────────────┤    ├──────────────┤
│ id (PK)      │    │ id (PK)      │    │ id (PK)      │
│ emp_id (FK)  │    │ emp_id (FK)  │    │ emp_id (FK)  │
│ date         │    │ type         │    │ date         │
│ clock_in     │    │ start_date   │    │ period       │
│ clock_out    │    │ end_date     │    │ reason       │
│ status       │    │ reason       │    │ status       │
│ created_at   │    │ status       │    │ created_at   │
│ updated_at   │    │ approver_id  │    │ updated_at   │
└──────────────┘    │ reject_reason│    └──────────────┘
                    │ created_at   │
                    │ updated_at   │
                    └──────────────┘

┌──────────────┐
│ notification │
├──────────────┤
│ id (PK)      │
│ emp_id (FK)  │
│ type         │
│ content      │
│ is_read      │
│ created_at   │
└──────────────┘
```

### 3.2 表结构详细设计

#### 3.2.1 department（部门表）

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| id | BIGINT | 是 | 主键，自增 |
| name | VARCHAR(50) | 是 | 部门名称，唯一 |
| created_at | DATETIME | 是 | 创建时间 |
| updated_at | DATETIME | 是 | 更新时间 |

**约束**：
- `name` 字段添加唯一索引

#### 3.2.2 employee（员工表）

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| id | BIGINT | 是 | 主键，自增 |
| dept_id | BIGINT | 是 | 所属部门 ID |
| emp_no | VARCHAR(20) | 是 | 工号，唯一 |
| name | VARCHAR(50) | 是 | 姓名 |
| phone | VARCHAR(20) | 是 | 手机号 |
| email | VARCHAR(100) | 否 | 邮箱 |
| hire_date | DATE | 是 | 入职日期 |
| position | VARCHAR(50) | 是 | 职位 |
| account | VARCHAR(50) | 是 | 登录账号，唯一 |
| password | VARCHAR(100) | 是 | 密码（BCrypt 加密） |
| role | TINYINT | 是 | 角色：0-员工，1-管理员 |
| status | TINYINT | 是 | 状态：0-离职，1-在职 |
| created_at | DATETIME | 是 | 创建时间 |
| updated_at | DATETIME | 是 | 更新时间 |

**约束**：
- `emp_no` 字段添加唯一索引
- `account` 字段添加唯一索引
- `dept_id` 外键关联 department 表

#### 3.2.3 attendance（考勤记录表）

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| id | BIGINT | 是 | 主键，自增 |
| emp_id | BIGINT | 是 | 员工 ID |
| date | DATE | 是 | 打卡日期 |
| clock_in | TIME | 否 | 上班打卡时间 |
| clock_out | TIME | 否 | 下班打卡时间 |
| status | TINYINT | 是 | 状态：0-缺勤，1-正常，2-迟到，3-早退，4-迟到且早退 |
| created_at | DATETIME | 是 | 创建时间 |
| updated_at | DATETIME | 是 | 更新时间 |

**约束**：
- `emp_id` + `date` 添加唯一联合索引

#### 3.2.4 leave_record（请假记录表）

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| id | BIGINT | 是 | 主键，自增 |
| emp_id | BIGINT | 是 | 申请人 ID |
| type | TINYINT | 是 | 假期类型：1-事假，2-病假 |
| start_date | DATE | 是 | 开始日期 |
| end_date | DATE | 是 | 结束日期 |
| reason | VARCHAR(500) | 是 | 请假事由 |
| status | TINYINT | 是 | 状态：0-待审批，1-已通过，2-已驳回 |
| approver_id | BIGINT | 否 | 审批人 ID |
| reject_reason | VARCHAR(500) | 否 | 驳回原因 |
| created_at | DATETIME | 是 | 创建时间 |
| updated_at | DATETIME | 是 | 更新时间 |

#### 3.2.5 makeup_clock（补卡申请表）

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| id | BIGINT | 是 | 主键，自增 |
| emp_id | BIGINT | 是 | 申请人 ID |
| date | DATE | 是 | 补卡日期 |
| period | TINYINT | 是 | 时段：1-上班，2-下班 |
| reason | VARCHAR(500) | 是 | 补卡原因 |
| status | TINYINT | 是 | 状态：0-待审批，1-已通过，2-已驳回 |
| approver_id | BIGINT | 否 | 审批人 ID |
| reject_reason | VARCHAR(500) | 否 | 驳回原因 |
| created_at | DATETIME | 是 | 创建时间 |
| updated_at | DATETIME | 是 | 更新时间 |

**约束**：
- `emp_id` + `date` + `period` 添加唯一联合索引

#### 3.2.6 notification（通知表）

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| id | BIGINT | 是 | 主键，自增 |
| emp_id | BIGINT | 是 | 接收人 ID |
| type | TINYINT | 是 | 通知类型：1-请假申请，2-请假通过，3-请假驳回，4-补卡申请，5-补卡通过，6-补卡驳回 |
| content | VARCHAR(500) | 是 | 通知内容 |
| is_read | TINYINT | 是 | 是否已读：0-未读，1-已读 |
| created_at | DATETIME | 是 | 创建时间 |

#### 3.2.7 system_config（系统配置表）

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| id | BIGINT | 是 | 主键，自增 |
| config_key | VARCHAR(50) | 是 | 配置键，唯一 |
| config_value | VARCHAR(100) | 是 | 配置值 |
| description | VARCHAR(200) | 否 | 配置说明 |
| updated_at | DATETIME | 是 | 更新时间 |

**初始数据**：
- `work_start_time` = `09:00`
- `work_end_time` = `18:00`

---

## 4. 接口设计

### 4.1 接口规范

- **Base URL**: `/api`
- **认证方式**: Bearer Token（JWT）
- **响应格式**:

```json
{
  "code": 200,
  "message": "success",
  "data": {}
}
```

- **错误码**:

| 错误码 | 说明 |
|--------|------|
| 200 | 成功 |
| 400 | 请求参数错误 |
| 401 | 未认证 |
| 403 | 无权限 |
| 404 | 资源不存在 |
| 500 | 服务器内部错误 |

### 4.2 接口列表

#### 4.2.1 认证接口 `/api/auth`

| 方法 | 路径 | 说明 | 权限 |
|------|------|------|------|
| POST | /login | 登录 | 公开 |
| POST | /logout | 登录 | 已认证 |
| GET | /me | 获取当前用户信息 | 已认证 |
| PUT | /password | 修改密码 | 已认证 |

#### 4.2.2 部门接口 `/api/departments`

| 方法 | 路径 | 说明 | 权限 |
|------|------|------|------|
| GET | / | 获取部门列表 | 管理员 |
| POST | / | 新增部门 | 管理员 |
| PUT | /{id} | 修改部门 | 管理员 |
| DELETE | /{id} | 删除部门 | 管理员 |

#### 4.2.3 员工接口 `/api/employees`

| 方法 | 路径 | 说明 | 权限 |
|------|------|------|------|
| GET | / | 获取员工列表（分页、筛选） | 管理员 |
| GET | /{id} | 获取员工详情 | 管理员 |
| POST | / | 新增员工 | 管理员 |
| PUT | /{id} | 修改员工信息 | 管理员 |
| PUT | /{id}/resign | 标记离职 | 管理员 |
| PUT | /{id}/reset-password | 重置密码 | 管理员 |

#### 4.2.4 考勤接口 `/api/attendance`

| 方法 | 路径 | 说明 | 权限 |
|------|------|------|------|
| POST | /clock-in | 上班打卡 | 员工 |
| POST | /clock-out | 下班打卡 | 员工 |
| GET | /my | 获取个人考勤记录 | 员工 |
| GET | /my/statistics | 获取个人考勤统计 | 员工 |
| GET | / | 获取所有考勤记录（分页、筛选） | 管理员 |
| GET | /statistics | 获取考勤统计（按部门/员工） | 管理员 |

#### 4.2.5 请假接口 `/api/leaves`

| 方法 | 路径 | 说明 | 权限 |
|------|------|------|------|
| POST | / | 提交请假申请 | 员工 |
| GET | /my | 获取我的请假记录 | 员工 |
| GET | /pending | 获取待审批列表 | 管理员 |
| GET | / | 获取所有请假记录 | 管理员 |
| PUT | /{id}/approve | 审批通过 | 管理员 |
| PUT | /{id}/reject | 审批驳回 | 管理员 |

#### 4.2.6 补卡接口 `/api/makeup-clocks`

| 方法 | 路径 | 说明 | 权限 |
|------|------|------|------|
| POST | / | 提交补卡申请 | 员工 |
| GET | /my | 获取我的补卡记录 | 员工 |
| GET | /pending | 获取待审批列表 | 管理员 |
| GET | / | 获取所有补卡记录 | 管理员 |
| PUT | /{id}/approve | 审批通过 | 管理员 |
| PUT | /{id}/reject | 审批驳回 | 管理员 |

#### 4.2.7 通知接口 `/api/notifications`

| 方法 | 路径 | 说明 | 权限 |
|------|------|------|------|
| GET | /my | 获取我的通知列表 | 已认证 |
| GET | /my/unread-count | 获取未读通知数量 | 已认证 |
| PUT | /{id}/read | 标记通知已读 | 已认证 |
| PUT | /read-all | 标记所有通知已读 | 已认证 |

#### 4.2.8 系统配置接口 `/api/config`

| 方法 | 路径 | 说明 | 权限 |
|------|------|------|------|
| GET | /attendance | 获取考勤配置 | 管理员 |
| PUT | /attendance | 修改考勤配置 | 管理员 |

---

## 5. 核心业务逻辑

### 5.1 打卡流程

```
员工点击打卡
    │
    ▼
检查员工状态是否在职
    │
    ├─ 否 → 返回错误：离职员工不可打卡
    │
    ▼ 是
检查今日是否已打卡（上班/下班）
    │
    ├─ 是 → 返回错误：今日已打卡
    │
    ▼ 否
记录打卡时间
    │
    ▼
判断打卡状态
    │
    ├─ 上班打卡：时间 > work_start_time → 迟到
    ├─ 下班打卡：时间 < work_end_time → 早退
    └─ 其他 → 正常
    │
    ▼
保存考勤记录
    │
    ▼
返回打卡结果
```

### 5.2 请假审批流程

```
员工提交请假申请
    │
    ▼
验证参数（日期、类型、事由）
    │
    ▼
检查是否有时间重叠的请假
    │
    ├─ 是 → 返回错误：时间段重叠
    │
    ▼ 否
创建请假记录（状态：待审批）
    │
    ▼
发送通知给管理员
    │
    ▼
返回提交成功

管理员审批
    │
    ▼
检查申请状态是否为待审批
    │
    ├─ 否 → 返回错误：该申请已处理
    │
    ▼ 是
更新申请状态（通过/驳回）
    │
    ├─ 通过 → 更新考勤记录（标记请假）
    │
    ▼
发送通知给员工
```

### 5.3 补卡审批流程

```
员工提交补卡申请
    │
    ▼
验证参数（日期、时段、原因）
    │
    ▼
检查补卡日期是否在7天内
    │
    ├─ 否 → 返回错误：只能补7天内的记录
    │
    ▼ 是
检查该日期时段是否已有补卡申请
    │
    ├─ 是 → 返回错误：已有待审批的补卡申请
    │
    ▼ 否
创建补卡记录（状态：待审批）
    │
    ▼
发送通知给管理员
    │
    ▼
返回提交成功

管理员审批
    │
    ▼
检查申请状态是否为待审批
    │
    ├─ 否 → 返回错误：该申请已处理
    │
    ▼ 是
更新申请状态（通过/驳回）
    │
    ├─ 通过 → 更新/创建考勤记录
    │
    ▼
发送通知给员工
```

---

## 6. 认证授权设计

### 6.1 JWT Token 结构

```json
{
  "sub": "employee_id",
  "account": "admin",
  "role": 1,
  "iat": 1234567890,
  "exp": 1234567890
}
```

### 6.2 权限控制

使用 Spring Security 的 `@PreAuthorize` 注解：

```java
// 仅管理员可访问
@PreAuthorize("hasRole('ADMIN')")

// 仅员工可访问
@PreAuthorize("hasRole('EMPLOYEE')")

// 已认证用户可访问
@PreAuthorize("isAuthenticated()")
```

### 6.3 密码加密

使用 BCrypt 加密算法，Spring Security 内置支持。

---

## 7. 前端路由设计

### 7.1 路由配置

```typescript
const routes = [
  {
    path: '/login',
    component: LoginView
  },
  {
    path: '/admin',
    component: AdminLayout,
    meta: { requiresAuth: true, role: 'ADMIN' },
    children: [
      { path: 'dashboard', component: AdminDashboard },
      { path: 'departments', component: DepartmentManage },
      { path: 'employees', component: EmployeeManage },
      { path: 'employees/:id', component: EmployeeDetail },
      { path: 'attendance', component: AttendanceManage },
      { path: 'attendance/statistics', component: AttendanceStatistics },
      { path: 'leaves', component: LeaveApproval },
      { path: 'makeup-clocks', component: MakeupClockApproval },
      { path: 'notifications', component: NotificationList },
      { path: 'config', component: SystemConfig }
    ]
  },
  {
    path: '/employee',
    component: EmployeeLayout,
    meta: { requiresAuth: true, role: 'EMPLOYEE' },
    children: [
      { path: 'dashboard', component: EmployeeDashboard },
      { path: 'clock', component: ClockView },
      { path: 'profile', component: ProfileView },
      { path: 'leaves', component: LeaveApply },
      { path: 'leaves/history', component: LeaveHistory },
      { path: 'makeup-clocks', component: MakeupClockApply },
      { path: 'notifications', component: MyNotifications }
    ]
  }
]
```

---

## 8. 页面设计

### 8.1 管理员页面

| 页面 | 功能 | 对应 Spec |
|------|------|-----------|
| 仪表盘 | 员工总数、部门数量、今日出勤、待审批数量 | - |
| 部门管理 | 部门列表、新增/编辑/删除部门 | Spec 2.2 |
| 员工管理 | 员工列表、新增/编辑/离职处理、筛选查询 | Spec 3.2 |
| 考勤管理 | 考勤记录列表、按月/部门/员工筛选 | Spec 4.4 |
| 考勤统计 | 出勤统计报表 | Spec 4.4 |
| 请假审批 | 待审批列表、审批操作 | Spec 5.3 |
| 补卡审批 | 待审批列表、审批操作 | Spec 4.3 |
| 通知管理 | 通知列表 | Spec 6.2 |
| 系统配置 | 班制时间设置 | Spec 4.1 |

### 8.2 员工页面

| 页面 | 功能 | 对应 Spec |
|------|------|-----------|
| 员工首页 | 今日打卡状态、本月考勤统计、待办事项 | - |
| 打卡 | 上班/下班打卡按钮、打卡记录 | Spec 4.2 |
| 个人信息 | 查看个人信息（只读） | Spec 3.3 |
| 请假申请 | 填写请假表单、提交申请 | Spec 5.2 |
| 请假记录 | 我的请假历史 | Spec 5.2 |
| 补卡申请 | 填写补卡表单、提交申请 | Spec 4.3 |
| 我的通知 | 通知列表、标记已读 | Spec 6.2 |

---

## 9. 关键设计决策

### 9.1 考勤状态判定

打卡状态在服务端判定，不在前端计算，确保一致性：

```java
public AttendanceStatusEnum determineStatus(LocalTime clockTime, boolean isClockIn) {
    LocalTime workStart = getWorkStartTime();
    LocalTime workEnd = getWorkEndTime();

    if (isClockIn) {
        return clockTime.isAfter(workStart)
            ? AttendanceStatusEnum.LATE
            : AttendanceStatusEnum.NORMAL;
    } else {
        return clockTime.isBefore(workEnd)
            ? AttendanceStatusEnum.EARLY_LEAVE
            : AttendanceStatusEnum.NORMAL;
    }
}
```

### 9.2 删除部门时的员工处理

删除部门前检查是否有员工，如果有则拒绝删除并提示：

```java
public void deleteDepartment(Long deptId) {
    long employeeCount = employeeMapper.countByDeptId(deptId);
    if (employeeCount > 0) {
        throw new BusinessException("该部门下有 " + employeeCount + " 名员工，请先转移员工");
    }
    departmentMapper.deleteById(deptId);
}
```

### 9.3 请假天数计算

V1 版本简化处理，按自然日计算（开始日期到结束日期的天数），不处理节假日：

```java
public int calculateLeaveDays(LocalDate startDate, LocalDate endDate) {
    return (int) ChronoUnit.DAYS.between(startDate, endDate) + 1;
}
```

---

## 10. 实现阶段

### Phase 1: 基础框架（预计 2 天）

- [ ] 后端项目初始化（Spring Boot + MyBatis-Plus + Security）
- [ ] 前端项目初始化（Vue 3 + Element Plus + Router + Pinia）
- [ ] 数据库迁移脚本
- [ ] JWT 认证授权实现
- [ ] 登录/登出功能

### Phase 2: 组织与员工管理（预计 2 天）

- [ ] 部门 CRUD 接口和页面
- [ ] 员工 CRUD 接口和页面
- [ ] 员工离职处理
- [ ] 密码重置功能

### Phase 3: 考勤打卡（预计 2 天）

- [ ] 打卡接口实现
- [ ] 打卡页面
- [ ] 考勤记录查询
- [ ] 考勤统计功能
- [ ] 系统配置（班制时间）

### Phase 4: 请假与补卡（预计 2 天）

- [ ] 请假申请/审批接口和页面
- [ ] 补卡申请/审批接口和页面
- [ ] 审批流程完整实现

### Phase 5: 通知与收尾（预计 1 天）

- [ ] 通知功能实现
- [ ] 未读通知提醒
- [ ] 边界条件测试
- [ ] 整体联调

---

## 11. 与 Spec 追踪矩阵

| Spec 章节 | Spec 功能点 | 实现位置 |
|-----------|-------------|----------|
| 2.2 | 部门管理 | DepartmentController + DepartmentManage.vue |
| 3.1 | 员工信息字段 | Employee 实体类 |
| 3.2 | 员工管理功能 | EmployeeController + EmployeeManage.vue |
| 3.3 | 员工自助 | ProfileView.vue |
| 4.1 | 班制设置 | SystemConfig 表 + SystemConfig.vue |
| 4.2 | 打卡功能 | AttendanceController + ClockView.vue |
| 4.3 | 补卡申请 | MakeupClockController + MakeupClockApply.vue |
| 4.4 | 考勤统计 | AttendanceController + AttendanceStatistics.vue |
| 5.1 | 假期类型 | LeaveTypeEnum |
| 5.2 | 请假申请 | LeaveController + LeaveApply.vue |
| 5.3 | 请假审批 | LeaveController + LeaveApproval.vue |
| 6.1 | 通知类型 | NotificationTypeEnum |
| 6.2 | 通知展示 | NotificationController + NotificationList.vue |
| 7.1 | 账号与登录 | AuthController + LoginView.vue |
| 7.2 | 数据安全 | Spring Security + BCrypt |
