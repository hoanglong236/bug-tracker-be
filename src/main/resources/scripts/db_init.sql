insert into projects values(nextval('projects_seq'), 'Test project', '', false, false);
insert into projects values(nextval('projects_seq'), 'Test project 2', '', true, false);
insert into projects values(nextval('projects_seq'), 'Test project 3', '', false, true);

insert into project_roles values(nextval('project_roles_seq'), 'Manager', '', true);
insert into project_roles values(nextval('project_roles_seq'), 'Leader', '');
insert into project_roles values(nextval('project_roles_seq'), 'Sub-leader', '');
insert into project_roles values(nextval('project_roles_seq'), 'Key-member', '');
insert into project_roles values(nextval('project_roles_seq'), 'Member', '');