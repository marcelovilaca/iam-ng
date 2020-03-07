-- These migrations will be migrated to flyway once the persistence layer schema
-- has stabilized
insert into REALMS(ID,NAME,DESCRIPTION,CONFIG,CREATION_TIME,LAST_UPDATE_TIME) 
    values (1,'iam', 'The iam realm', '{}', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
 
insert into REALMS(ID,NAME,DESCRIPTION,CONFIG,CREATION_TIME,LAST_UPDATE_TIME) 
    values (2,'alice', 'The alice realm', '{}', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
    
insert into REALMS(ID,NAME,DESCRIPTION,CONFIG,CREATION_TIME,LAST_UPDATE_TIME) 
    values (3,'atlas', 'The atlas realm', '{}', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

insert into GROUPS(ID,REALM_ID,NAME,UUID,PARENT_GROUP_ID,CREATION_TIME,LAST_UPDATE_TIME)
    values (100,2,'alice', UUID(), null,CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

insert into GROUPS(ID,REALM_ID,NAME,UUID,PARENT_GROUP_ID,CREATION_TIME,LAST_UPDATE_TIME)
    values (101,2,'alice/production', UUID(), 100,CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());