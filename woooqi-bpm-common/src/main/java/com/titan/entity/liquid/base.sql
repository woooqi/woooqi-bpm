insert into BPM_USER (ID, CREATE_TIME, LOGIN_NAME, NAME, PASSWORD, SALT, STATUS) values ('1', sysdate, 'admin', '管理员', '670b14728ad9902aecba32e22fa4f6bd', null, 1);

insert into BPM_ROLE (ID, CREATE_TIME, NAME, ROLE_CODE, STATUS) values ('1', sysdate, '用户', 'USER', 1);

insert into BPM_USER_ROLE (ROLE_ID, USER_ID) values ('1', '1');

insert into bpm_menu (ID, CREATE_TIME, NAME, PARENT_ID, SORT, STATUS, URL, DIVISION) values ('0', sysdate, '全部菜单', null, 1, 1, null, null);

insert into BPM_DEPT (ID, CREATE_TIME, NAME, PARENT_ID, SORT, STATUS) values ('0', sysdate, '全部部门', null, 1, 1);

insert into bpm_menu (ID, CREATE_TIME, DIVISION, NAME, PARENT_ID, SORT, STAMP, STATUS, URL)
values ('0', '29-8月 -17 05.22.37.000000 下午', null, '全部菜单', null, 0, null, 1, null);

insert into bpm_menu (ID, CREATE_TIME, DIVISION, NAME, PARENT_ID, SORT, STAMP, STATUS, URL)
values ('297e39e45e11e100015e1307bcf50000', '25-8月 -17 10.11.42.180000 上午', null, '个人任务', '0', 1, null, 1, '#');

insert into bpm_menu (ID, CREATE_TIME, DIVISION, NAME, PARENT_ID, SORT, STAMP, STATUS, URL)
values ('297e39e45e11e100015e130a12630004', '31-8月 -17 03.11.15.415000 下午', null, '流程管理', '297e39e45e11e100015e1307bcf50000', 1, 'work_bpm', 1, '#');

insert into bpm_menu (ID, CREATE_TIME, DIVISION, NAME, PARENT_ID, SORT, STAMP, STATUS, URL)
values ('297e39e45e11e100015e130b20700006', '31-8月 -17 01.53.11.782000 下午', null, '表单设计', '297e39e45e11e100015e130825170001', 1, 'form', 1, '#');

insert into bpm_menu (ID, CREATE_TIME, DIVISION, NAME, PARENT_ID, SORT, STAMP, STATUS, URL)
values ('297e39e45e11e100015e130bca420008', '31-8月 -17 03.08.20.489000 下午', null, '用户配置', '297e39e45e35f074015e35fb849f0000', 1, 'user_manage', 1, 'main_business?path=organization/user_manage');

insert into bpm_menu (ID, CREATE_TIME, DIVISION, NAME, PARENT_ID, SORT, STAMP, STATUS, URL)
values ('297e39e45e11e100015e130e645f000e', '24-8月 -17 03.04.23.903000 下午', null, '基础数据', '297e39e45e11e100015e130971570003', 1, null, 1, '#');

insert into bpm_menu (ID, CREATE_TIME, DIVISION, NAME, PARENT_ID, SORT, STAMP, STATUS, URL)
values ('297e39e45e35f074015e35fb849f0000', '31-8月 -17 03.07.57.932000 下午', null, '组织配置', '297e39e45e11e100015e130949100002', 1, 'organization_manage', 1, '#');

insert into bpm_menu (ID, CREATE_TIME, DIVISION, NAME, PARENT_ID, SORT, STAMP, STATUS, URL)
values ('297e39e45e363aa6015e36be3dd80000', '31-8月 -17 03.11.38.515000 下午', null, '发起流程', '297e39e45e11e100015e130a12630004', 1, 'bpm_start', 1, 'main_business?path=work/bpm/bpm_start');

insert into bpm_menu (ID, CREATE_TIME, DIVISION, NAME, PARENT_ID, SORT, STAMP, STATUS, URL)
values ('297e39e45e363aa6015e36bff8820003', '31-8月 -17 03.11.54.607000 下午', null, '待办任务', '297e39e45e11e100015e130aa7430005', 1, 'work_started', 1, 'main_business?path=work/work/work_started');

insert into bpm_menu (ID, CREATE_TIME, DIVISION, NAME, PARENT_ID, SORT, STAMP, STATUS, URL)
values ('297e39e45e36c981015e36d9754b0000', '31-8月 -17 03.12.09.444000 下午', null, '自定义表', '297e39e45e11e100015e130b20700006', 1, 'table_manage', 1, 'main_business?path=bpm/form/table_manage');

insert into bpm_menu (ID, CREATE_TIME, DIVISION, NAME, PARENT_ID, SORT, STAMP, STATUS, URL)
values ('297e39e45e36c981015e36db32210002', '31-8月 -17 01.54.48.481000 下午', null, '流程模型', '297e39e45e11e100015e130b76cd0007', 1, 'bpm_model', 1, '#');

insert into bpm_menu (ID, CREATE_TIME, DIVISION, NAME, PARENT_ID, SORT, STAMP, STATUS, URL)
values ('297e39e45e36c981015e36dd7a680006', '31-8月 -17 03.12.19.178000 下午', null, '模型管理', '297e39e45e36c981015e36db32210002', 1, 'model_manage', 1, 'main_business?path=bpm/bpm/model/model_manage');

insert into bpm_menu (ID, CREATE_TIME, DIVISION, NAME, PARENT_ID, SORT, STAMP, STATUS, URL)
values ('297e39e45e36c981015e36de376c0008', '31-8月 -17 03.12.33.754000 下午', null, '部署管理', '297e39e45e36c981015e36db78a90003', 1, 'deploy_manage', 1, 'main_business?path=bpm/bpm/deploy/deploy_manage');

insert into bpm_menu (ID, CREATE_TIME, DIVISION, NAME, PARENT_ID, SORT, STAMP, STATUS, URL)
values ('297e39e45e36c981015e36defc2d000a', '31-8月 -17 01.58.56.813000 下午', null, '流程授权', '297e39e45e36c981015e36dbc1d00004', 1, 'process_authorization', 1, '#');

insert into bpm_menu (ID, CREATE_TIME, DIVISION, NAME, PARENT_ID, SORT, STAMP, STATUS, URL)
values ('297e39e45e36c981015e36dfaca9000c', '31-8月 -17 03.12.56.446000 下午', null, '实例管理', '297e39e45e36c981015e36dd0f280005', 1, '/instance_manage', 1, 'main_business?path=bpm/bpm/instance/instance_manage');

insert into bpm_menu (ID, CREATE_TIME, DIVISION, NAME, PARENT_ID, SORT, STAMP, STATUS, URL)
values ('297e39e45e11e100015e130825170001', '24-8月 -17 02.57.34.487000 下午', null, '流程中心', '0', 2, null, 1, '#');

insert into bpm_menu (ID, CREATE_TIME, DIVISION, NAME, PARENT_ID, SORT, STAMP, STATUS, URL)
values ('297e39e45e11e100015e130aa7430005', '31-8月 -17 03.11.21.216000 下午', null, '任务管理', '297e39e45e11e100015e1307bcf50000', 2, 'work_work', 1, '#');

insert into bpm_menu (ID, CREATE_TIME, DIVISION, NAME, PARENT_ID, SORT, STAMP, STATUS, URL)
values ('297e39e45e11e100015e130b76cd0007', '31-8月 -17 01.53.17.661000 下午', null, '流程设计', '297e39e45e11e100015e130825170001', 2, 'bpm', 1, '#');

insert into bpm_menu (ID, CREATE_TIME, DIVISION, NAME, PARENT_ID, SORT, STAMP, STATUS, URL)
values ('297e39e45e11e100015e130bfc660009', '31-8月 -17 03.08.25.071000 下午', null, '角色配置', '297e39e45e35f074015e35fb849f0000', 2, 'role_manage', 1, 'main_business?path=organization/role_manage');

insert into bpm_menu (ID, CREATE_TIME, DIVISION, NAME, PARENT_ID, SORT, STAMP, STATUS, URL)
values ('297e39e45e11e100015e130e904c000f', '24-8月 -17 03.04.35.148000 下午', null, '日历管理', '297e39e45e11e100015e130971570003', 2, null, 1, '#');

insert into bpm_menu (ID, CREATE_TIME, DIVISION, NAME, PARENT_ID, SORT, STAMP, STATUS, URL)
values ('297e39e45e35f074015e35fbc10c0001', '31-8月 -17 03.08.02.838000 下午', null, '机构配置', '297e39e45e11e100015e130949100002', 2, 'institution_manage', 1, '#');

insert into bpm_menu (ID, CREATE_TIME, DIVISION, NAME, PARENT_ID, SORT, STAMP, STATUS, URL)
values ('297e39e45e363aa6015e36bec8520001', '31-8月 -17 03.11.43.639000 下午', null, '未结流程', '297e39e45e11e100015e130a12630004', 2, 'bpm_execution', 1, 'main_business?path=work/bpm/bpm_execution');

insert into bpm_menu (ID, CREATE_TIME, DIVISION, NAME, PARENT_ID, SORT, STAMP, STATUS, URL)
values ('297e39e45e363aa6015e36c04e6a0004', '31-8月 -17 03.11.59.487000 下午', null, '已办任务', '297e39e45e11e100015e130aa7430005', 2, 'work_executed', 1, 'main_business?path=work/work/work_executed');

insert into bpm_menu (ID, CREATE_TIME, DIVISION, NAME, PARENT_ID, SORT, STAMP, STATUS, URL)
values ('297e39e45e36c981015e36dab4fc0001', '31-8月 -17 03.12.13.544000 下午', null, '自定义表单', '297e39e45e11e100015e130b20700006', 2, 'form_manage', 1, 'main_business?path=bpm/form/form_manage');

insert into bpm_menu (ID, CREATE_TIME, DIVISION, NAME, PARENT_ID, SORT, STAMP, STATUS, URL)
values ('297e39e45e36c981015e36db78a90003', '31-8月 -17 01.55.06.537000 下午', null, '流程部署', '297e39e45e11e100015e130b76cd0007', 2, 'bpm_deploy', 1, '#');

insert into bpm_menu (ID, CREATE_TIME, DIVISION, NAME, PARENT_ID, SORT, STAMP, STATUS, URL)
values ('297e39e45e36c981015e36ddd8670007', '31-8月 -17 03.12.23.585000 下午', null, '流程分类', '297e39e45e36c981015e36db32210002', 2, 'category_manage', 1, 'main_business?path=bpm/bpm/model/category_manage');

insert into bpm_menu (ID, CREATE_TIME, DIVISION, NAME, PARENT_ID, SORT, STAMP, STATUS, URL)
values ('297e39e45e36c981015e36de8e8a0009', '31-8月 -17 03.12.38.261000 下午', null, '流程定义', '297e39e45e36c981015e36db78a90003', 2, 'definition_manage', 1, 'main_business?path=bpm/bpm/deploy/definition_manage');

insert into bpm_menu (ID, CREATE_TIME, DIVISION, NAME, PARENT_ID, SORT, STAMP, STATUS, URL)
values ('297e39e45e36c981015e36df4e88000b', '31-8月 -17 03.12.50.183000 下午', null, '配置管理', '297e39e45e36c981015e36dbc1d00004', 2, 'process_manage', 1, 'main_business?path=bpm/bpm/manage/process_manage');

insert into bpm_menu (ID, CREATE_TIME, DIVISION, NAME, PARENT_ID, SORT, STAMP, STATUS, URL)
values ('297e39e45e11e100015e130949100002', '24-8月 -17 02.58.49.231000 下午', null, '组织机构', '0', 3, null, 1, '#');

insert into bpm_menu (ID, CREATE_TIME, DIVISION, NAME, PARENT_ID, SORT, STAMP, STATUS, URL)
values ('297e39e45e11e100015e130d0483000a', '31-8月 -17 03.08.29.976000 下午', null, '菜单配置', '297e39e45e35f074015e35fb849f0000', 3, 'menu_manage', 1, 'main_business?path=organization/menu_manage');

insert into bpm_menu (ID, CREATE_TIME, DIVISION, NAME, PARENT_ID, SORT, STAMP, STATUS, URL)
values ('297e39e45e11e100015e130ec30c0010', '24-8月 -17 03.04.48.139000 下午', null, '定时管理', '297e39e45e11e100015e130971570003', 3, null, 1, '#');

insert into bpm_menu (ID, CREATE_TIME, DIVISION, NAME, PARENT_ID, SORT, STAMP, STATUS, URL)
values ('297e39e45e363aa6015e36bf23a90002', '31-8月 -17 03.11.48.552000 下午', null, '办结流程', '297e39e45e11e100015e130a12630004', 3, 'bpm_finished', 1, 'main_business?path=work/bpm/bpm_finished');

insert into bpm_menu (ID, CREATE_TIME, DIVISION, NAME, PARENT_ID, SORT, STAMP, STATUS, URL)
values ('297e39e45e363aa6015e36c09b660005', '31-8月 -17 03.12.03.648000 下午', null, '办结任务', '297e39e45e11e100015e130aa7430005', 3, 'work_finished', 1, 'main_business?path=work/work/work_finished');

insert into bpm_menu (ID, CREATE_TIME, DIVISION, NAME, PARENT_ID, SORT, STAMP, STATUS, URL)
values ('297e39e45e36c981015e36dbc1d00004', '31-8月 -17 01.55.25.264000 下午', null, '流程配置', '297e39e45e11e100015e130b76cd0007', 3, 'bpm_process', 1, '#');

insert into bpm_menu (ID, CREATE_TIME, DIVISION, NAME, PARENT_ID, SORT, STAMP, STATUS, URL)
values ('297e39e45e11e100015e130971570003', '24-8月 -17 02.58.59.542000 下午', null, '系统设置', '0', 4, null, 1, '#');

insert into bpm_menu (ID, CREATE_TIME, DIVISION, NAME, PARENT_ID, SORT, STAMP, STATUS, URL)
values ('297e39e45e11e100015e130d74fb000b', '31-8月 -17 03.08.37.758000 下午', null, '部门配置', '297e39e45e35f074015e35fbc10c0001', 4, 'dept_manage', 1, 'main_business?path=organization/dept_manage');

insert into bpm_menu (ID, CREATE_TIME, DIVISION, NAME, PARENT_ID, SORT, STAMP, STATUS, URL)
values ('297e39e45e36c981015e36dd0f280005', '31-8月 -17 01.56.50.599000 下午', null, '流程实例', '297e39e45e11e100015e130b76cd0007', 4, 'bpm_instance', 1, '#');
