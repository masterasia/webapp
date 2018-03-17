-- 初始数据
INSERT INTO sys_user (user_id, username, password, salt, email, mobile, status, dept_id, create_time) VALUES ('1', 'admin', 'e1153123d7d180ceeb820d577ff119876678732a68eef4e6ffc0b1f06a01f91b', 'YzcmCZNvbXocrsz9dm8e', '', '', '1', '1', sysdate);

INSERT INTO sys_menu (menu_id, parent_id, menu_name, url, perms, menu_type, menu_icon, order_num) VALUES ('1', '0', '系统管理', NULL, NULL, '0', 'fa fa-cog', '0');
INSERT INTO sys_menu (menu_id, parent_id, menu_name, url, perms, menu_type, menu_icon, order_num) VALUES ('2', '1', '管理员管理', 'webapp/sys/user.html', NULL, '1', 'fa fa-user', '1');
INSERT INTO sys_menu (menu_id, parent_id, menu_name, url, perms, menu_type, menu_icon, order_num) VALUES ('3', '1', '角色管理', 'webapp/sys/role.html', NULL, '1', 'fa fa-user-secret', '2');
INSERT INTO sys_menu (menu_id, parent_id, menu_name, url, perms, menu_type, menu_icon, order_num) VALUES ('4', '1', '菜单管理', 'webapp/sys/menu.html', NULL, '1', 'fa fa-th-list', '3');
INSERT INTO sys_menu (menu_id, parent_id, menu_name, url, perms, menu_type, menu_icon, order_num) VALUES ('5', '1', 'SQL监控', 'druid/sql.html', NULL, '1', 'fa fa-bug', '4');
INSERT INTO sys_menu (menu_id, parent_id, menu_name, url, perms, menu_type, menu_icon, order_num) VALUES ('15', '2', '查看', NULL, 'sys:user:list,sys:user:info', '2', NULL, '0');
INSERT INTO sys_menu (menu_id, parent_id, menu_name, url, perms, menu_type, menu_icon, order_num) VALUES ('16', '2', '新增', NULL, 'sys:user:save,sys:role:select', '2', NULL, '0');
INSERT INTO sys_menu (menu_id, parent_id, menu_name, url, perms, menu_type, menu_icon, order_num) VALUES ('17', '2', '修改', NULL, 'sys:user:update,sys:role:select', '2', NULL, '0');
INSERT INTO sys_menu (menu_id, parent_id, menu_name, url, perms, menu_type, menu_icon, order_num) VALUES ('18', '2', '删除', NULL, 'sys:user:delete', '2', NULL, '0');
INSERT INTO sys_menu (menu_id, parent_id, menu_name, url, perms, menu_type, menu_icon, order_num) VALUES ('19', '3', '查看', NULL, 'sys:role:list,sys:role:info', '2', NULL, '0');
INSERT INTO sys_menu (menu_id, parent_id, menu_name, url, perms, menu_type, menu_icon, order_num) VALUES ('20', '3', '新增', NULL, 'sys:role:save,sys:menu:perms', '2', NULL, '0');
INSERT INTO sys_menu (menu_id, parent_id, menu_name, url, perms, menu_type, menu_icon, order_num) VALUES ('21', '3', '修改', NULL, 'sys:role:update,sys:menu:perms', '2', NULL, '0');
INSERT INTO sys_menu (menu_id, parent_id, menu_name, url, perms, menu_type, menu_icon, order_num) VALUES ('22', '3', '删除', NULL, 'sys:role:delete', '2', NULL, '0');
INSERT INTO sys_menu (menu_id, parent_id, menu_name, url, perms, menu_type, menu_icon, order_num) VALUES ('23', '4', '查看', NULL, 'sys:menu:list,sys:menu:info', '2', NULL, '0');
INSERT INTO sys_menu (menu_id, parent_id, menu_name, url, perms, menu_type, menu_icon, order_num) VALUES ('24', '4', '新增', NULL, 'sys:menu:save,sys:menu:select', '2', NULL, '0');
INSERT INTO sys_menu (menu_id, parent_id, menu_name, url, perms, menu_type, menu_icon, order_num) VALUES ('25', '4', '修改', NULL, 'sys:menu:update,sys:menu:select', '2', NULL, '0');
INSERT INTO sys_menu (menu_id, parent_id, menu_name, url, perms, menu_type, menu_icon, order_num) VALUES ('26', '4', '删除', NULL, 'sys:menu:delete', '2', NULL, '0');
INSERT INTO sys_menu (menu_id, parent_id, menu_name, url, perms, menu_type, menu_icon, order_num) VALUES ('27', '1', '参数管理', 'webapp/sys/config.html', 'sys:config:list,sys:config:info,sys:config:save,sys:config:update,sys:config:delete', '1', 'fa fa-sun-o', '6');
INSERT INTO sys_menu (menu_id, parent_id, menu_name, url, perms, menu_type, menu_icon, order_num) VALUES ('29', '1', '系统日志', 'webapp/sys/log.html', 'sys:log:list', '1', 'fa fa-file-text-o', '7');
INSERT INTO sys_menu (menu_id, parent_id, menu_name, url, perms, menu_type, menu_icon, order_num) VALUES ('31', '1', '部门管理', 'webapp/sys/dept.html', NULL, '1', 'fa fa-file-code-o', '1');
INSERT INTO sys_menu (menu_id, parent_id, menu_name, url, perms, menu_type, menu_icon, order_num) VALUES ('32', '31', '查看', NULL, 'sys:dept:list,sys:dept:info', '2', NULL, '0');
INSERT INTO sys_menu (menu_id, parent_id, menu_name, url, perms, menu_type, menu_icon, order_num) VALUES ('33', '31', '新增', NULL, 'sys:dept:save,sys:dept:select', '2', NULL, '0');
INSERT INTO sys_menu (menu_id, parent_id, menu_name, url, perms, menu_type, menu_icon, order_num) VALUES ('34', '31', '修改', NULL, 'sys:dept:update,sys:dept:select', '2', NULL, '0');
INSERT INTO sys_menu (menu_id, parent_id, menu_name, url, perms, menu_type, menu_icon, order_num) VALUES ('35', '31', '删除', NULL, 'sys:dept:delete', '2', NULL, '0');

-- 菜单SQL
INSERT INTO sys_menu (menu_id, parent_id, menu_name, url, perms, menu_type, menu_icon, order_num) VALUES (260, '1', '字典类别管理', 'webapp/generator/maincategory.html', NULL, '1', 'fa fa-file-code-o', '260');

-- 菜单对应按钮SQL
INSERT INTO sys_menu (menu_id, parent_id, menu_name, url, perms, menu_type, menu_icon, order_num) VALUES ( 271, '260', '查看字典类别列表', null, 'maincategory:list', '2', null, '6');
INSERT INTO sys_menu (menu_id, parent_id, menu_name, url, perms, menu_type, menu_icon, order_num) VALUES ( 272, '260', '查看字典类别元素', null, 'maincategory:info', '2', null, '6');
INSERT INTO sys_menu (menu_id, parent_id, menu_name, url, perms, menu_type, menu_icon, order_num) VALUES ( 273, '260', '新增字典类别', null, 'maincategory:save', '2', null, '6');
INSERT INTO sys_menu (menu_id, parent_id, menu_name, url, perms, menu_type, menu_icon, order_num) VALUES ( 274, '260', '修改字典类别', null, 'maincategory:update', '2', null, '6');
INSERT INTO sys_menu (menu_id, parent_id, menu_name, url, perms, menu_type, menu_icon, order_num) VALUES ( 275, '260', '删除字典类别', null, 'maincategory:delete', '2', null, '6');
INSERT INTO sys_menu (menu_id, parent_id, menu_name, url, perms, menu_type, menu_icon, order_num) VALUES ( 276, '260', '批量删除字典类别', null, 'maincategory:deleteBatch', '2', null, '6');

-- 菜单SQL
INSERT INTO sys_menu (menu_id, parent_id, menu_name, url, perms, menu_type, menu_icon, order_num) VALUES (290, '1', '字典内容', 'webapp/generator/subcategory.html', NULL, '1', 'fa fa-file-code-o', '290');

-- 菜单对应按钮SQL
INSERT INTO sys_menu (menu_id, parent_id, menu_name, url, perms, menu_type, menu_icon, order_num) VALUES ( 301, '290', '查看字典内容列表', null, 'subcategory:list', '2', null, '6');
INSERT INTO sys_menu (menu_id, parent_id, menu_name, url, perms, menu_type, menu_icon, order_num) VALUES ( 302, '290', '查看字典内容元素', null, 'subcategory:info', '2', null, '6');
INSERT INTO sys_menu (menu_id, parent_id, menu_name, url, perms, menu_type, menu_icon, order_num) VALUES ( 303, '290', '新增字典内容', null, 'subcategory:save', '2', null, '6');
INSERT INTO sys_menu (menu_id, parent_id, menu_name, url, perms, menu_type, menu_icon, order_num) VALUES ( 304, '290', '修改字典内容', null, 'subcategory:update', '2', null, '6');
INSERT INTO sys_menu (menu_id, parent_id, menu_name, url, perms, menu_type, menu_icon, order_num) VALUES ( 305, '290', '删除字典内容', null, 'subcategory:delete', '2', null, '6');
INSERT INTO sys_menu (menu_id, parent_id, menu_name, url, perms, menu_type, menu_icon, order_num) VALUES ( 306, '290', '批量删除字典内容', null, 'subcategory:deleteBatch', '2', null, '6');

INSERT INTO sys_menu (menu_id, parent_id, menu_name, url, perms, menu_type, menu_icon, order_num) VALUES (320, '1', '区域管理', 'webapp/sys/area.html', NULL, '1', 'fa fa-file-code-o', '320');
INSERT INTO sys_menu (menu_id, parent_id, menu_name, url, perms, menu_type, menu_icon, order_num) VALUES (331, '320', '查看区域', NULL, 'sys:area:list', '2', NULL, '0');
INSERT INTO sys_menu (menu_id, parent_id, menu_name, url, perms, menu_type, menu_icon, order_num) VALUES (332, '320', '查看区域元素', NULL, 'sys:area:info', '2', NULL, '0');
INSERT INTO sys_menu (menu_id, parent_id, menu_name, url, perms, menu_type, menu_icon, order_num) VALUES (333, '320', '新增区域', NULL, 'sys:area:save', '2', NULL, '0');
INSERT INTO sys_menu (menu_id, parent_id, menu_name, url, perms, menu_type, menu_icon, order_num) VALUES (334, '320', '选择区域', NULL, 'sys:area:select', '2', NULL, '0');
INSERT INTO sys_menu (menu_id, parent_id, menu_name, url, perms, menu_type, menu_icon, order_num) VALUES (335, '320', '修改区域', NULL, 'sys:area:update', '2', NULL, '0');
INSERT INTO sys_menu (menu_id, parent_id, menu_name, url, perms, menu_type, menu_icon, order_num) VALUES (336, '320', '删除区域', NULL, 'sys:area:delete', '2', NULL, '0');

INSERT INTO sys_dept (dept_id, parent_id,dept_name, order_num, del_flag) VALUES ('1', '0', 'root', '0', '0');
INSERT INTO sys_dept (dept_id, parent_id,dept_name, order_num, del_flag) VALUES ('2', '1', 'first', '1', '0');