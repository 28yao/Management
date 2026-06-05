# 部门管理系统 - 任务分解列表

> **约定**：
> - `[P]` 标记表示可并行执行（无依赖）
> - `[TDD]` 标记表示需先写测试
> - 每个任务对应一个文件的创建或修改
> - 依赖关系通过缩进和顺序体现

---

## Phase 1: Foundation & Skeleton

> 解决方案骨架、项目结构、基础配置、依赖注入、日志、环境配置、前端基础工程初始化
> **不实现具体业务功能**

### 1.1 后端项目骨架

| # | 任务 | 文件路径 | 依赖 | 追踪 |
|---|------|----------|------|------|
| 1.1.1 | [P] 创建 Maven pom.xml（Spring Boot 2.7.18 + MyBatis-Plus + Security + JWT + Flyway + MySQL） | `backend/management-api/pom.xml` | - | plan §1.1 |
| 1.1.2 | [P] 创建 application.yml 主配置文件 | `backend/management-api/src/main/resources/application.yml` | - | plan §2.1 |
| 1.1.3 | [P] 创建 application-dev.yml 开发环境配置 | `backend/management-api/src/main/resources/application-dev.yml` | - | plan §2.1 |
| 1.1.4 | [P] 创建 Spring Boot 启动类 | `backend/management-api/src/main/java/com/management/ManagementApplication.java` | - | plan §2.1 |
| 1.1.5 | [P] 创建跨域配置类 | `backend/management-api/src/main/java/com/management/config/WebConfig.java` | - | plan §2.1 |
| 1.1.6 | [P] 创建 MyBatis-Plus 配置类 | `backend/management-api/src/main/java/com/management/config/MybatisPlusConfig.java` | - | plan §2.1 |
| 1.1.7 | [P] 创建自定义业务异常类 | `backend/management-api/src/main/java/com/management/exception/BusinessException.java` | - | plan §2.1 |
| 1.1.8 | [P] 创建全局异常处理器 | `backend/management-api/src/main/java/com/management/exception/GlobalExceptionHandler.java` | 1.1.7 | plan §2.1 |
| 1.1.9 | [P] 创建统一响应包装类 | `backend/management-api/src/main/java/com/management/common/ApiResponse.java` | - | plan §4.1 |
| 1.1.10 | 验证后端项目可启动 | - | 1.1.1-1.1.9 | - |

### 1.2 前端项目骨架

| # | 任务 | 文件路径 | 依赖 | 追踪 |
|---|------|----------|------|------|
| 1.2.1 | [P] 创建 package.json（Vue 3 + Element Plus + Pinia + Router + Axios） | `frontend/management-web/package.json` | - | plan §1.1 |
| 1.2.2 | [P] 创建 Vite 配置文件 | `frontend/management-web/vite.config.ts` | - | plan §2.2 |
| 1.2.3 | [P] 创建 TypeScript 配置 | `frontend/management-web/tsconfig.json` | - | plan §2.2 |
| 1.2.4 | [P] 创建前端入口文件 | `frontend/management-web/src/main.ts` | 1.2.1 | plan §2.2 |
| 1.2.5 | [P] 创建根组件 | `frontend/management-web/src/App.vue` | 1.2.4 | plan §2.2 |
| 1.2.6 | [P] 创建 Axios 请求封装（拦截器、Token 注入、错误处理） | `frontend/management-web/src/utils/request.ts` | 1.2.1 | plan §2.2 |
| 1.2.7 | [P] 创建 Token 管理工具 | `frontend/management-web/src/utils/auth.ts` | - | plan §2.2 |
| 1.2.8 | 创建路由配置（空路由骨架） | `frontend/management-web/src/router/index.ts` | 1.2.4 | plan §7 |
| 1.2.9 | 创建 Pinia 认证状态 Store | `frontend/management-web/src/stores/auth.ts` | 1.2.7 | plan §2.2 |
| 1.2.10 | 验证前端项目可启动 | - | 1.2.1-1.2.9 | - |

---

## Phase 2: Domain Model & Domain Tests (TDD)

> 领域实体、值对象、枚举、仓储接口
> **必须先生成测试任务，再生成实现任务**

### 2.1 枚举类

| # | 任务 | 文件路径 | 依赖 | 追踪 |
|---|------|----------|------|------|
| 2.1.1 | [P] 创建角色枚举（EMPLOYEE=0, ADMIN=1） | `backend/.../enums/RoleEnum.java` | - | plan §6.2 |
| 2.1.2 | [P] 创建考勤状态枚举（ABSENT=0, NORMAL=1, LATE=2, EARLY_LEAVE=3, LATE_AND_EARLY=4） | `backend/.../enums/AttendanceStatusEnum.java` | - | plan §5.1 |
| 2.1.3 | [P] 创建假期类型枚举（PERSONAL=1, SICK=2） | `backend/.../enums/LeaveTypeEnum.java` | - | spec §5.1 |
| 2.1.4 | [P] 创建审批状态枚举（PENDING=0, APPROVED=1, REJECTED=2） | `backend/.../enums/ApprovalStatusEnum.java` | - | plan §5.2 |
| 2.1.5 | [P] 创建通知类型枚举（LEAVE_SUBMIT=1, LEAVE_APPROVE=2, LEAVE_REJECT=3, MAKEUP_SUBMIT=4, MAKEUP_APPROVE=5, MAKEUP_REJECT=6） | `backend/.../enums/NotificationTypeEnum.java` | - | spec §6.1 |
| 2.1.6 | [P] 创建打卡时段枚举（CLOCK_IN=1, CLOCK_OUT=2） | `backend/.../enums/ClockPeriodEnum.java` | - | plan §3.2.5 |

### 2.2 实体类（先测试后实现）

| # | 任务 | 文件路径 | 依赖 | 追踪 |
|---|------|----------|------|------|
| **Department 实体** | | | |
| 2.2.1 | [TDD] Department 实体单元测试 | `backend/.../test/entity/DepartmentTest.java` | - | plan §3.2.1 |
| 2.2.2 | 创建 Department 实体类 | `backend/.../entity/Department.java` | 2.2.1 | plan §3.2.1 |
| **Employee 实体** | | | |
| 2.2.3 | [TDD] Employee 实体单元测试 | `backend/.../test/entity/EmployeeTest.java` | - | plan §3.2.2 |
| 2.2.4 | 创建 Employee 实体类 | `backend/.../entity/Employee.java` | 2.2.3, 2.1.1 | plan §3.2.2 |
| **Attendance 实体** | | | |
| 2.2.5 | [TDD] Attendance 实体单元测试 | `backend/.../test/entity/AttendanceTest.java` | - | plan §3.2.3 |
| 2.2.6 | 创建 Attendance 实体类 | `backend/.../entity/Attendance.java` | 2.2.5, 2.1.2 | plan §3.2.3 |
| **LeaveRecord 实体** | | | |
| 2.2.7 | [TDD] LeaveRecord 实体单元测试 | `backend/.../test/entity/LeaveRecordTest.java` | - | plan §3.2.4 |
| 2.2.8 | 创建 LeaveRecord 实体类 | `backend/.../entity/LeaveRecord.java` | 2.2.7, 2.1.3, 2.1.4 | plan §3.2.4 |
| **MakeupClock 实体** | | | |
| 2.2.9 | [TDD] MakeupClock 实体单元测试 | `backend/.../test/entity/MakeupClockTest.java` | - | plan §3.2.5 |
| 2.2.10 | 创建 MakeupClock 实体类 | `backend/.../entity/MakeupClock.java` | 2.2.9, 2.1.4, 2.1.6 | plan §3.2.5 |
| **Notification 实体** | | | |
| 2.2.11 | [TDD] Notification 实体单元测试 | `backend/.../test/entity/NotificationTest.java` | - | plan §3.2.6 |
| 2.2.12 | 创建 Notification 实体类 | `backend/.../entity/Notification.java` | 2.2.11, 2.1.5 | plan §3.2.6 |
| **SystemConfig 实体** | | | |
| 2.2.13 | [TDD] SystemConfig 实体单元测试 | `backend/.../test/entity/SystemConfigTest.java` | - | plan §3.2.7 |
| 2.2.14 | 创建 SystemConfig 实体类 | `backend/.../entity/SystemConfig.java` | 2.2.13 | plan §3.2.7 |

### 2.3 Mapper 接口

| # | 任务 | 文件路径 | 依赖 | 追踪 |
|---|------|----------|------|------|
| 2.3.1 | [P] 创建 DepartmentMapper 接口 | `backend/.../mapper/DepartmentMapper.java` | 2.2.2 | plan §2.1 |
| 2.3.2 | [P] 创建 EmployeeMapper 接口 | `backend/.../mapper/EmployeeMapper.java` | 2.2.4 | plan §2.1 |
| 2.3.3 | [P] 创建 AttendanceMapper 接口 | `backend/.../mapper/AttendanceMapper.java` | 2.2.6 | plan §2.1 |
| 2.3.4 | [P] 创建 LeaveRecordMapper 接口 | `backend/.../mapper/LeaveRecordMapper.java` | 2.2.8 | plan §2.1 |
| 2.3.5 | [P] 创建 MakeupClockMapper 接口 | `backend/.../mapper/MakeupClockMapper.java` | 2.2.10 | plan §2.1 |
| 2.3.6 | [P] 创建 NotificationMapper 接口 | `backend/.../mapper/NotificationMapper.java` | 2.2.12 | plan §2.1 |
| 2.3.7 | [P] 创建 SystemConfigMapper 接口 | `backend/.../mapper/SystemConfigMapper.java` | 2.2.14 | plan §2.1 |

---

## Phase 3: Application Use Cases & Application Tests (TDD)

> 应用服务、业务编排、事务边界
> **必须先生成测试任务，再生成实现任务**

### 3.1 认证服务

| # | 任务 | 文件路径 | 依赖 | 追踪 |
|---|------|----------|------|------|
| 3.1.1 | [TDD] AuthService 单元测试（登录、登出、获取当前用户、修改密码） | `backend/.../test/service/AuthServiceTest.java` | - | spec §7.1 |
| 3.1.2 | 创建 AuthService 实现 | `backend/.../service/AuthService.java` | 3.1.1, 2.3.2 | spec §7.1 |

### 3.2 部门服务

| # | 任务 | 文件路径 | 依赖 | 追踪 |
|---|------|----------|------|------|
| 3.2.1 | [TDD] DepartmentService 单元测试（CRUD、删除前检查员工） | `backend/.../test/service/DepartmentServiceTest.java` | - | spec §2.2 |
| 3.2.2 | 创建 DepartmentService 实现 | `backend/.../service/DepartmentService.java` | 3.2.1, 2.3.1, 2.3.2 | spec §2.2 |

### 3.3 员工服务

| # | 任务 | 文件路径 | 依赖 | 追踪 |
|---|------|----------|------|------|
| 3.3.1 | [TDD] EmployeeService 单元测试（CRUD、离职、重置密码、筛选查询） | `backend/.../test/service/EmployeeServiceTest.java` | - | spec §3.2 |
| 3.3.2 | 创建 EmployeeService 实现 | `backend/.../service/EmployeeService.java` | 3.3.1, 2.3.2 | spec §3.2 |

### 3.4 考勤服务

| # | 任务 | 文件路径 | 依赖 | 追踪 |
|---|------|----------|------|------|
| 3.4.1 | [TDD] AttendanceService 单元测试（上班打卡、下班打卡、状态判定、记录查询、统计） | `backend/.../test/service/AttendanceServiceTest.java` | - | spec §4.2, §4.4 |
| 3.4.2 | 创建 AttendanceService 实现 | `backend/.../service/AttendanceService.java` | 3.4.1, 2.3.3, 2.3.7 | spec §4.2, §4.4 |

### 3.5 请假服务

| # | 任务 | 文件路径 | 依赖 | 追踪 |
|---|------|----------|------|------|
| 3.5.1 | [TDD] LeaveService 单元测试（提交申请、时间重叠检查、审批通过/驳回、通知发送） | `backend/.../test/service/LeaveServiceTest.java` | - | spec §5.2, §5.3 |
| 3.5.2 | 创建 LeaveService 实现 | `backend/.../service/LeaveService.java` | 3.5.1, 2.3.4, 2.3.3, 2.3.6 | spec §5.2, §5.3 |

### 3.6 补卡服务

| # | 任务 | 文件路径 | 依赖 | 追踪 |
|---|------|----------|------|------|
| 3.6.1 | [TDD] MakeupClockService 单元测试（提交申请、7天限制检查、重复检查、审批通过/驳回） | `backend/.../test/service/MakeupClockServiceTest.java` | - | spec §4.3 |
| 3.6.2 | 创建 MakeupClockService 实现 | `backend/.../service/MakeupClockService.java` | 3.6.1, 2.3.5, 2.3.3, 2.3.6 | spec §4.3 |

### 3.7 通知服务

| # | 任务 | 文件路径 | 依赖 | 追踪 |
|---|------|----------|------|------|
| 3.7.1 | [TDD] NotificationService 单元测试（发送通知、获取通知、标记已读、未读计数） | `backend/.../test/service/NotificationServiceTest.java` | - | spec §6.1, §6.2 |
| 3.7.2 | 创建 NotificationService 实现 | `backend/.../service/NotificationService.java` | 3.7.1, 2.3.6 | spec §6.1, §6.2 |

### 3.8 系统配置服务

| # | 任务 | 文件路径 | 依赖 | 追踪 |
|---|------|----------|------|------|
| 3.8.1 | [TDD] SystemConfigService 单元测试（获取/更新考勤配置） | `backend/.../test/service/SystemConfigServiceTest.java` | - | spec §4.1 |
| 3.8.2 | 创建 SystemConfigService 实现 | `backend/.../service/SystemConfigService.java` | 3.8.1, 2.3.7 | spec §4.1 |

---

## Phase 4: API Contracts & Web API (TDD)

> API DTO、Controller、请求校验、接口测试
> **必须先生成接口测试任务，再生成实现任务**

### 4.1 DTO 类（可并行创建）

| # | 任务 | 文件路径 | 依赖 | 追踪 |
|---|------|----------|------|------|
| **请求 DTO** | | | |
| 4.1.1 | [P] 创建登录请求 DTO | `backend/.../dto/request/LoginRequest.java` | - | plan §4.2.1 |
| 4.1.2 | [P] 创建修改密码请求 DTO | `backend/.../dto/request/ChangePasswordRequest.java` | - | plan §4.2.1 |
| 4.1.3 | [P] 创建部门请求 DTO（新增/修改） | `backend/.../dto/request/DepartmentRequest.java` | - | plan §4.2.2 |
| 4.1.4 | [P] 创建员工请求 DTO（新增/修改） | `backend/.../dto/request/EmployeeRequest.java` | - | plan §4.2.3 |
| 4.1.5 | [P] 创建员工查询 DTO（分页、筛选） | `backend/.../dto/request/EmployeeQueryRequest.java` | - | plan §4.2.3 |
| 4.1.6 | [P] 创建请假请求 DTO | `backend/.../dto/request/LeaveRequest.java` | - | plan §4.2.5 |
| 4.1.7 | [P] 创建补卡请求 DTO | `backend/.../dto/request/MakeupClockRequest.java` | - | plan §4.2.6 |
| 4.1.8 | [P] 创建审批请求 DTO（驳回原因） | `backend/.../dto/request/RejectRequest.java` | - | plan §4.2.5 |
| 4.1.9 | [P] 创建考勤配置请求 DTO | `backend/.../dto/request/AttendanceConfigRequest.java` | - | plan §4.2.8 |
| 4.1.10 | [P] 创建考勤查询请求 DTO（月份、部门、员工） | `backend/.../dto/request/AttendanceQueryRequest.java` | - | plan §4.2.4 |
| **响应 DTO** | | | |
| 4.1.11 | [P] 创建登录响应 DTO（Token） | `backend/.../dto/response/LoginResponse.java` | - | plan §4.2.1 |
| 4.1.12 | [P] 创建用户信息响应 DTO | `backend/.../dto/response/UserInfoResponse.java` | - | plan §4.2.1 |
| 4.1.13 | [P] 创建部门响应 DTO | `backend/.../dto/response/DepartmentResponse.java` | - | plan §4.2.2 |
| 4.1.14 | [P] 创建员工响应 DTO | `backend/.../dto/response/EmployeeResponse.java` | - | plan §4.2.3 |
| 4.1.15 | [P] 创建考勤记录响应 DTO | `backend/.../dto/response/AttendanceResponse.java` | - | plan §4.2.4 |
| 4.1.16 | [P] 创建考勤统计响应 DTO | `backend/.../dto/response/AttendanceStatisticsResponse.java` | - | plan §4.2.4 |
| 4.1.17 | [P] 创建请假响应 DTO | `backend/.../dto/response/LeaveResponse.java` | - | plan §4.2.5 |
| 4.1.18 | [P] 创建补卡响应 DTO | `backend/.../dto/response/MakeupClockResponse.java` | - | plan §4.2.6 |
| 4.1.19 | [P] 创建通知响应 DTO | `backend/.../dto/response/NotificationResponse.java` | - | plan §4.2.7 |
| 4.1.20 | [P] 创建考勤配置响应 DTO | `backend/.../dto/response/AttendanceConfigResponse.java` | - | plan §4.2.8 |

### 4.2 Controller（先测试后实现）

| # | 任务 | 文件路径 | 依赖 | 追踪 |
|---|------|----------|------|------|
| **AuthController** | | | |
| 4.2.1 | [TDD] AuthController 接口测试（登录、登出、获取用户信息、修改密码） | `backend/.../test/controller/AuthControllerTest.java` | - | plan §4.2.1 |
| 4.2.2 | 创建 AuthController | `backend/.../controller/AuthController.java` | 4.2.1, 3.1.2, 4.1.1-4.1.2, 4.1.11-4.1.12 | plan §4.2.1 |
| **DepartmentController** | | | |
| 4.2.3 | [TDD] DepartmentController 接口测试（列表、新增、修改、删除） | `backend/.../test/controller/DepartmentControllerTest.java` | - | plan §4.2.2 |
| 4.2.4 | 创建 DepartmentController | `backend/.../controller/DepartmentController.java` | 4.2.3, 3.2.2, 4.1.3, 4.1.13 | plan §4.2.2 |
| **EmployeeController** | | | |
| 4.2.5 | [TDD] EmployeeController 接口测试（列表、详情、新增、修改、离职、重置密码） | `backend/.../test/controller/EmployeeControllerTest.java` | - | plan §4.2.3 |
| 4.2.6 | 创建 EmployeeController | `backend/.../controller/EmployeeController.java` | 4.2.5, 3.3.2, 4.1.4-4.1.5, 4.1.14 | plan §4.2.3 |
| **AttendanceController** | | | |
| 4.2.7 | [TDD] AttendanceController 接口测试（打卡、个人记录、统计、全部记录） | `backend/.../test/controller/AttendanceControllerTest.java` | - | plan §4.2.4 |
| 4.2.8 | 创建 AttendanceController | `backend/.../controller/AttendanceController.java` | 4.2.7, 3.4.2, 4.1.10, 4.1.15-4.1.16 | plan §4.2.4 |
| **LeaveController** | | | |
| 4.2.9 | [TDD] LeaveController 接口测试（提交、我的记录、待审批、全部、通过、驳回） | `backend/.../test/controller/LeaveControllerTest.java` | - | plan §4.2.5 |
| 4.2.10 | 创建 LeaveController | `backend/.../controller/LeaveController.java` | 4.2.9, 3.5.2, 4.1.6-4.1.8, 4.1.17 | plan §4.2.5 |
| **MakeupClockController** | | | |
| 4.2.11 | [TDD] MakeupClockController 接口测试（提交、我的记录、待审批、全部、通过、驳回） | `backend/.../test/controller/MakeupClockControllerTest.java` | - | plan §4.2.6 |
| 4.2.12 | 创建 MakeupClockController | `backend/.../controller/MakeupClockController.java` | 4.2.11, 3.6.2, 4.1.7-4.1.8, 4.1.18 | plan §4.2.6 |
| **NotificationController** | | | |
| 4.2.13 | [TDD] NotificationController 接口测试（我的通知、未读数、标记已读、全部已读） | `backend/.../test/controller/NotificationControllerTest.java` | - | plan §4.2.7 |
| 4.2.14 | 创建 NotificationController | `backend/.../controller/NotificationController.java` | 4.2.13, 3.7.2, 4.1.19 | plan §4.2.7 |
| **SystemConfigController** | | | |
| 4.2.15 | [TDD] SystemConfigController 接口测试（获取/更新考勤配置） | `backend/.../test/controller/SystemConfigControllerTest.java` | - | plan §4.2.8 |
| 4.2.16 | 创建 SystemConfigController | `backend/.../controller/SystemConfigController.java` | 4.2.15, 3.8.2, 4.1.9, 4.1.20 | plan §4.2.8 |

---

## Phase 5: Infrastructure & Integration

> 数据库迁移、认证落地、安全配置、预置数据、集成测试

### 5.1 数据库迁移脚本

| # | 任务 | 文件路径 | 依赖 | 追踪 |
|---|------|----------|------|------|
| 5.1.1 | [P] 创建 department 表迁移脚本 | `backend/.../db/migration/V1__init_department.sql` | - | plan §3.2.1 |
| 5.1.2 | [P] 创建 employee 表迁移脚本 | `backend/.../db/migration/V2__init_employee.sql` | 5.1.1 | plan §3.2.2 |
| 5.1.3 | [P] 创建 attendance 表迁移脚本 | `backend/.../db/migration/V3__init_attendance.sql` | 5.1.2 | plan §3.2.3 |
| 5.1.4 | [P] 创建 leave_record 表迁移脚本 | `backend/.../db/migration/V4__init_leave.sql` | 5.1.2 | plan §3.2.4 |
| 5.1.5 | [P] 创建 makeup_clock 表迁移脚本 | `backend/.../db/migration/V5__init_makeup_clock.sql` | 5.1.2 | plan §3.2.5 |
| 5.1.6 | [P] 创建 notification 表迁移脚本 | `backend/.../db/migration/V6__init_notification.sql` | 5.1.2 | plan §3.2.6 |
| 5.1.7 | [P] 创建 system_config 表迁移脚本及初始数据 | `backend/.../db/migration/V7__init_system_config.sql` | - | plan §3.2.7 |
| 5.1.8 | 创建预置管理员账号数据脚本 | `backend/.../db/migration/V8__init_admin.sql` | 5.1.2 | spec §7.1 |

### 5.2 安全与认证配置

| # | 任务 | 文件路径 | 依赖 | 追踪 |
|---|------|----------|------|------|
| 5.2.1 | 创建 Spring Security 配置类（JWT Filter、权限规则、白名单） | `backend/.../config/SecurityConfig.java` | 5.2.3 | plan §6 |
| 5.2.2 | 创建 JWT 配置属性类 | `backend/.../config/JwtConfig.java` | - | plan §6.1 |
| 5.2.3 | 创建 JWT 工具类（生成Token、解析Token、验证Token） | `backend/.../util/JwtUtil.java` | 5.2.2 | plan §6.1 |
| 5.2.4 | 创建 Security 工具类（获取当前用户、检查角色） | `backend/.../util/SecurityUtil.java` | 5.2.3 | plan §6.2 |
| 5.2.5 | 创建 JWT 认证过滤器 | `backend/.../filter/JwtAuthenticationFilter.java` | 5.2.3, 5.2.4 | plan §6 |
| 5.2.6 | 创建 UserDetailsService 实现（从数据库加载用户） | `backend/.../service/CustomUserDetailsService.java` | 2.3.2 | plan §6 |

### 5.3 集成测试

| # | 任务 | 文件路径 | 依赖 | 追踪 |
|---|------|----------|------|------|
| 5.3.1 | [TDD] 认证流程集成测试（登录→获取Token→访问接口） | `backend/.../test/integration/AuthIntegrationTest.java` | 5.2.1-5.2.6 | plan §6 |
| 5.3.2 | [TDD] 部门管理集成测试（CRUD + 删除有员工部门） | `backend/.../test/integration/DepartmentIntegrationTest.java` | 5.3.1 | spec §2.2 |
| 5.3.3 | [TDD] 员工管理集成测试（CRUD + 离职 + 权限隔离） | `backend/.../test/integration/EmployeeIntegrationTest.java` | 5.3.1 | spec §3.2 |
| 5.3.4 | [TDD] 打卡流程集成测试（打卡 + 重复打卡 + 迟到早退判定） | `backend/.../test/integration/AttendanceIntegrationTest.java` | 5.3.1 | spec §4.2 |
| 5.3.5 | [TDD] 请假流程集成测试（申请→审批→通知→考勤更新） | `backend/.../test/integration/LeaveIntegrationTest.java` | 5.3.1 | spec §5.2, §5.3 |
| 5.3.6 | [TDD] 补卡流程集成测试（申请→审批→记录更新） | `backend/.../test/integration/MakeupClockIntegrationTest.java` | 5.3.1 | spec §4.3 |
| 5.3.7 | [TDD] 通知集成测试（发送、查询、标记已读） | `backend/.../test/integration/NotificationIntegrationTest.java` | 5.3.1 | spec §6 |

---

## Phase 6: Frontend UI & Interaction

> 路由、页面、组件、布局、API Service、表单处理、页面状态、鉴权态、联调任务

### 6.1 公共组件

| # | 任务 | 文件路径 | 依赖 | 追踪 |
|---|------|----------|------|------|
| 6.1.1 | [P] 创建管理员布局组件（侧边栏 + 顶部导航 + 通知徽章） | `frontend/.../components/Layout/AdminLayout.vue` | - | plan §8.1 |
| 6.1.2 | [P] 创建员工布局组件（侧边栏 + 顶部导航） | `frontend/.../components/Layout/EmployeeLayout.vue` | - | plan §8.2 |
| 6.1.3 | [P] 创建通知徽章组件 | `frontend/.../components/NotificationBadge.vue` | - | spec §6.2 |

### 6.2 API 服务层（可并行创建）

| # | 任务 | 文件路径 | 依赖 | 追踪 |
|---|------|----------|------|------|
| 6.2.1 | [P] 创建认证 API 服务（登录、登出、获取用户、修改密码） | `frontend/.../api/auth.ts` | 1.2.6 | plan §4.2.1 |
| 6.2.2 | [P] 创建部门 API 服务（列表、新增、修改、删除） | `frontend/.../api/department.ts` | 1.2.6 | plan §4.2.2 |
| 6.2.3 | [P] 创建员工 API 服务（列表、详情、新增、修改、离职、重置密码） | `frontend/.../api/employee.ts` | 1.2.6 | plan §4.2.3 |
| 6.2.4 | [P] 创建考勤 API 服务（打卡、记录、统计） | `frontend/.../api/attendance.ts` | 1.2.6 | plan §4.2.4 |
| 6.2.5 | [P] 创建请假 API 服务（申请、记录、审批） | `frontend/.../api/leave.ts` | 1.2.6 | plan §4.2.5 |
| 6.2.6 | [P] 创建补卡 API 服务（申请、记录、审批） | `frontend/.../api/makeup-clock.ts` | 1.2.6 | plan §4.2.6 |
| 6.2.7 | [P] 创建通知 API 服务（列表、未读数、标记已读） | `frontend/.../api/notification.ts` | 1.2.6 | plan §4.2.7 |
| 6.2.8 | [P] 创建系统配置 API 服务（获取/更新配置） | `frontend/.../api/config.ts` | 1.2.6 | plan §4.2.8 |

### 6.3 登录页面

| # | 任务 | 文件路径 | 依赖 | 追踪 |
|---|------|----------|------|------|
| 6.3.1 | 创建登录页面（账号密码表单、登录逻辑、角色跳转） | `frontend/.../views/login/LoginView.vue` | 6.2.1, 1.2.9 | spec §7.1 |

### 6.4 管理员页面

| # | 任务 | 文件路径 | 依赖 | 追踪 |
|---|------|----------|------|------|
| 6.4.1 | [P] 创建管理员仪表盘（员工总数、部门数量、今日出勤、待审批数量） | `frontend/.../views/admin/dashboard/AdminDashboard.vue` | 6.2.3, 6.2.4, 6.2.5 | plan §8.1 |
| 6.4.2 | [P] 创建部门管理页面（部门列表、新增/编辑/删除对话框） | `frontend/.../views/admin/department/DepartmentManage.vue` | 6.2.2 | spec §2.2 |
| 6.4.3 | [P] 创建员工管理页面（员工列表、筛选、新增/编辑对话框、离职操作） | `frontend/.../views/admin/employee/EmployeeManage.vue` | 6.2.3, 6.2.2 | spec §3.2 |
| 6.4.4 | [P] 创建员工详情页面（查看员工信息、重置密码） | `frontend/.../views/admin/employee/EmployeeDetail.vue` | 6.2.3 | spec §3.2 |
| 6.4.5 | [P] 创建考勤管理页面（考勤记录列表、按月/部门/员工筛选） | `frontend/.../views/admin/attendance/AttendanceManage.vue` | 6.2.4, 6.2.2, 6.2.3 | spec §4.4 |
| 6.4.6 | [P] 创建考勤统计页面（出勤统计报表展示） | `frontend/.../views/admin/attendance/AttendanceStatistics.vue` | 6.2.4 | spec §4.4 |
| 6.4.7 | [P] 创建请假审批页面（待审批列表、审批操作、驳回原因） | `frontend/.../views/admin/leave/LeaveApproval.vue` | 6.2.5 | spec §5.3 |
| 6.4.8 | [P] 创建补卡审批页面（待审批列表、审批操作） | `frontend/.../views/admin/makeup-clock/MakeupClockApproval.vue` | 6.2.6 | spec §4.3 |
| 6.4.9 | [P] 创建系统配置页面（班制时间设置） | `frontend/.../views/admin/config/SystemConfig.vue` | 6.2.8 | spec §4.1 |

### 6.5 员工页面

| # | 任务 | 文件路径 | 依赖 | 追踪 |
|---|------|----------|------|------|
| 6.5.1 | [P] 创建员工仪表盘（今日打卡状态、本月统计、待办） | `frontend/.../views/employee/dashboard/EmployeeDashboard.vue` | 6.2.4, 6.2.5 | plan §8.2 |
| 6.5.2 | [P] 创建打卡页面（上班/下班打卡按钮、今日打卡记录） | `frontend/.../views/employee/clock/ClockView.vue` | 6.2.4 | spec §4.2 |
| 6.5.3 | [P] 创建个人信息页面（只读展示个人信息） | `frontend/.../views/employee/profile/ProfileView.vue` | 6.2.1 | spec §3.3 |
| 6.5.4 | [P] 创建请假申请页面（假期类型、日期选择、事由填写、提交） | `frontend/.../views/employee/leave/LeaveApply.vue` | 6.2.5 | spec §5.2 |
| 6.5.5 | [P] 创建请假记录页面（我的请假历史列表） | `frontend/.../views/employee/leave/LeaveHistory.vue` | 6.2.5 | spec §5.2 |
| 6.5.6 | [P] 创建补卡申请页面（日期、时段、原因、提交） | `frontend/.../views/employee/makeup-clock/MakeupClockApply.vue` | 6.2.6 | spec §4.3 |

### 6.6 通知页面（通用）

| # | 任务 | 文件路径 | 依赖 | 追踪 |
|---|------|----------|------|------|
| 6.6.1 | [P] 创建通知列表页面（管理员版） | `frontend/.../views/admin/notification/NotificationList.vue` | 6.2.7 | spec §6.2 |
| 6.6.2 | [P] 创建通知列表页面（员工版） | `frontend/.../views/employee/notification/MyNotifications.vue` | 6.2.7 | spec §6.2 |

### 6.7 路由完善与鉴权守卫

| # | 任务 | 文件路径 | 依赖 | 追踪 |
|---|------|----------|------|------|
| 6.7.1 | 完善路由配置（所有页面路由 + 路由守卫 + 角色权限） | `frontend/.../router/index.ts` | 6.3.1, 6.4.1-6.4.9, 6.5.1-6.5.6, 6.6.1-6.6.2 | plan §7 |
| 6.7.2 | 完善 Pinia Store（Token 持久化、用户信息缓存、登出清理） | `frontend/.../stores/auth.ts` | 6.2.1 | plan §2.2 |

### 6.8 前后端联调

| # | 任务 | 文件路径 | 依赖 | 追踪 |
|---|------|----------|------|------|
| 6.8.1 | 联调：登录流程（登录→Token→角色跳转） | - | 6.3.1, 4.2.2 | spec §7.1 |
| 6.8.2 | 联调：部门管理 CRUD | - | 6.4.2, 4.2.4 | spec §2.2 |
| 6.8.3 | 联调：员工管理（新增、编辑、离职、重置密码） | - | 6.4.3, 4.2.6 | spec §3.2 |
| 6.8.4 | 联调：打卡流程（上班打卡、下班打卡、状态显示） | - | 6.5.2, 4.2.8 | spec §4.2 |
| 6.8.5 | 联调：请假流程（申请→审批→通知） | - | 6.5.4, 6.4.7, 4.2.10 | spec §5.2, §5.3 |
| 6.8.6 | 联调：补卡流程（申请→审批→记录更新） | - | 6.5.6, 6.4.8, 4.2.12 | spec §4.3 |
| 6.8.7 | 联调：通知功能（发送、查看、标记已读） | - | 6.6.1-6.6.2, 6.1.3, 4.2.14 | spec §6 |
| 6.8.8 | 联调：考勤统计与报表 | - | 6.4.5, 6.4.6, 4.2.8 | spec §4.4 |

---

## 任务统计

| 阶段 | 任务数 | 可并行任务 |
|------|--------|-----------|
| Phase 1: Foundation & Skeleton | 20 | 16 |
| Phase 2: Domain Model & Domain Tests | 28 | 24 |
| Phase 3: Application Use Cases & Tests | 16 | 0 |
| Phase 4: API Contracts & Web API | 36 | 20 |
| Phase 5: Infrastructure & Integration | 21 | 8 |
| Phase 6: Frontend UI & Interaction | 38 | 30 |
| **总计** | **159** | **98** |

---

## 依赖关系图（关键路径）

```
Phase 1 (骨架)
    │
    ▼
Phase 2 (实体 + Mapper)
    │
    ▼
Phase 3 (Service)
    │
    ├──────────────────┐
    ▼                  ▼
Phase 4 (Controller)  Phase 5 (基础设施)
    │                  │
    └────────┬─────────┘
             ▼
        Phase 6 (前端)
```

**关键路径**：1.1.1 → 2.2.2 → 2.3.1 → 3.2.2 → 4.2.4 → 5.2.1 → 6.4.2 → 6.8.2

**预估工时**：
- Phase 1: 1 天
- Phase 2: 1 天
- Phase 3: 1.5 天
- Phase 4: 1.5 天
- Phase 5: 1 天
- Phase 6: 3 天
- **总计：约 9 天**
