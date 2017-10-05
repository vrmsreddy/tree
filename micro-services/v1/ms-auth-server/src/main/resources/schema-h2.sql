
drop table if exists ClientDetails;
create table ClientDetails (
        appId VARCHAR(255) PRIMARY KEY,
        resourceIds VARCHAR(255),
        appSecret VARCHAR(255),
        scope VARCHAR(255),
        grantTypes VARCHAR(255),
        redirectUrl VARCHAR(255),
        authorities VARCHAR(255),
        access_token_validity INTEGER,
        refresh_token_validity INTEGER,
        additionalInformation VARCHAR(2000)
);




DROP TABLE IF EXISTS authorities;
CREATE TABLE authorities (
  username varchar(50) ,
  authority varchar(50)
);



DROP TABLE IF EXISTS oauth_access_token;
CREATE TABLE oauth_access_token (
  token_id varchar(256),
  token blob,
  authentication_id varchar(256),
  user_name varchar(256),
  client_id varchar(256),
  authentication blob,
  refresh_token varchar(256)
) ;



DROP TABLE IF EXISTS oauth_client_details;
CREATE TABLE oauth_client_details (
  client_id varchar(256) ,
  resource_ids varchar(256) ,
  client_secret varchar(256) ,
  scope varchar(256) ,
  authorized_grant_types varchar(256) ,
  web_server_redirect_uri varchar(256) ,
  authorities varchar(256),
  access_token_validity int(11) ,
  refresh_token_validity int(11) ,
  additional_information varchar(4096) ,
  autoapprove varchar(256) 
);



DROP TABLE IF EXISTS oauth_code;
CREATE TABLE oauth_code (
  code varchar(256) DEFAULT NULL,
  authentication blob
);


drop table if exists oauth_refresh_token;
create table oauth_refresh_token (
        token_id VARCHAR(255),
        token blob,
        authentication blob
);

DROP TABLE IF EXISTS users;
CREATE TABLE users (
  username varchar(50),
  password varchar(50),
  enabled tinyint(1),
) ;

