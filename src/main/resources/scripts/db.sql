create database bug_tracker encoding 'utf8';

create table users(
	id int,
    email varchar(100) not null unique,
    password varchar(60) not null unique,
    name varchar(50) not null,
    role varchar(10) not null,
    enable_flag boolean not null default true,
    delete_flag boolean not null default false,
    created_at timestamp not null default current_timestamp,
    updated_at timestamp not null default current_timestamp,
    primary key (id)
);

create table projects(
	id int,
	name varchar(100) not null,
	kind varchar(50) not null,
    architecture varchar(50) not null,
    technology varchar(50) not null,
    lang varchar(100) not null,
    db varchar(50) not null,
	note varchar(256),
	close_flag boolean not null default false,
	delete_flag boolean not null default false,
	created_at timestamp not null default current_timestamp,
    updated_at timestamp not null default current_timestamp,
    primary key (id)
);

create table project_roles(
    id int,
    name varchar(50) not null,
    note varchar(256),
    delete_flag boolean not null default false,
    created_at timestamp not null default current_timestamp,
    updated_at timestamp not null default current_timestamp,
    primary key (id)
);

create table project_members(
    id int,
    project_id int not null,
    user_id int not null,
    project_role_id int not null,
    delete_flag boolean not null default false,
    created_at timestamp not null default current_timestamp,
    updated_at timestamp not null default current_timestamp,
    primary key (id),
    foreign key (project_id) references projects(id),
    foreign key (user_id) references users(id),
    foreign key (project_role_id) references project_roles(id)
);

create table posts(
    id int,
    reporter_id int not null,
    assigner_id int not null,
    project_id int not null,
    phase varchar(10),
    status varchar(10) not null,
    title varchar(100) not null,
    bug_desc varchar(256) not null,
    bug_reason varchar(256) not null,
    bug_fix_method varchar(256) not null,
    delete_flag boolean not null default false,
    created_at timestamp not null default current_timestamp,
    updated_at timestamp not null default current_timestamp,
    primary key (id),
    foreign key (reporter_id) references project_members(id),
    foreign key (assigner_id) references project_members(id),
    foreign key (project_id) references projects(id)
);

create table post_files(
    id int,
    post_id int not null,
    url varchar(256) not null,
    created_at timestamp not null default current_timestamp,
    updated_at timestamp not null default current_timestamp,
    primary key (id),
    foreign key (post_id) references posts(id)
);

create sequence projects_seq increment 1 start 1;
create sequence project_roles_seq increment 1 start 1;
create sequence project_members_seq increment 1 start 1;
create sequence users_seq increment 1 start 1;
create sequence posts_seq increment 1 start 1;
create sequence post_files_seq increment 1 start 1;