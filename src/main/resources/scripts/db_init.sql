insert into projects values(nextval('projects_seq'), 'Test project', 'Web Application', 'Micro-services', 'RESTful, Jwt', 'Angular v16 (Typescript), Spring Boot 3 (Java)', 'PostgreSQL', 'Test project...', false, false);
insert into projects values(nextval('projects_seq'), 'Test project 2', 'Web Application', 'Monolith', 'RESTful, Jwt', 'Angular v16 (Typescript), Spring Boot 3 (Java)', 'MySQL', 'Test project 2...', false, false);
insert into projects values(nextval('projects_seq'), 'Test project 3', 'Mobile', 'Micro-services', 'RESTful, OAuth2', 'React Native (Typescript), NestJS (Typescript)', 'MongoDB', 'Test project 3...', false, false);

insert into project_roles values(nextval('project_roles_seq'), 'Manager', '', true);
insert into project_roles values(nextval('project_roles_seq'), 'Leader', '');
insert into project_roles values(nextval('project_roles_seq'), 'Sub-leader', '');
insert into project_roles values(nextval('project_roles_seq'), 'Key-member', '');
insert into project_roles values(nextval('project_roles_seq'), 'Member', '');