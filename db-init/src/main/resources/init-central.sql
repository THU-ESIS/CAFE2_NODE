DROP TABLE IF EXISTS model_file;
CREATE TABLE
  model_file
(
  id                    INT(10)       NOT NULL AUTO_INCREMENT,
  node_id               INT(10),
  full_path             VARCHAR(1024) NOT NULL,
  mip_era               VARCHAR(1024) COLLATE utf8_bin NOT NULL,
  institute             VARCHAR(1024) COLLATE utf8_bin NOT NULL,
  model                 VARCHAR(1024) COLLATE utf8_bin NOT NULL,
  experiment            VARCHAR(1024) COLLATE utf8_bin NOT NULL,
  frequency             VARCHAR(1024) COLLATE utf8_bin NOT NULL,
  modeling_realm        VARCHAR(1024) COLLATE utf8_bin NOT NULL,
  ensemble_member       VARCHAR(1024) COLLATE utf8_bin NOT NULL,
  variable_name         VARCHAR(1024) COLLATE utf8_bin NOT NULL,
  mip_table             VARCHAR(1024) COLLATE utf8_bin NOT NULL DEFAULT 'NA',
  dataset_version       VARCHAR(1024) COLLATE utf8_bin NOT NULL DEFAULT 'NA',
  temporal_start_year   VARCHAR(16),
  temporal_start_month  VARCHAR(16),
  temporal_start_day    VARCHAR(16),
  temporal_start_hour   VARCHAR(16),
  temporal_start_minute VARCHAR(16),
  temporal_end_year     VARCHAR(16),
  temporal_end_month    VARCHAR(16),
  temporal_end_day      VARCHAR(16),
  temporal_end_hour     VARCHAR(16),
  temporal_end_minute   VARCHAR(16),
  grid_label            VARCHAR(1024) COLLATE utf8_bin NOT NULL DEFAULT 'NA',
  create_time           DATETIME,

  INDEX ix_node_id (node_id),
  INDEX ix_institute (institute),
  INDEX ix_model (model),
  INDEX ix_experiment (experiment),
  INDEX ix_frequency (frequency),
  INDEX ix_modeling_realm (modeling_realm),
  INDEX ix_ensemble_member (ensemble_member),
  INDEX ix_variable_name (variable_name),
  INDEX ix_mip_table (mip_table),
  INDEX ix_temporal_start_year (temporal_start_year),
  INDEX ix_temporal_end_year (temporal_end_year),

  PRIMARY KEY (id)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =utf8;


DROP TABLE IF EXISTS worker_node;
CREATE TABLE worker_node (
  id        INT(10)      NOT NULL AUTO_INCREMENT,
  name      VARCHAR(256) NOT NULL,
  ip        VARCHAR(16)  NOT NULL,
  port      INT(10)      NOT NULL,
  root_path VARCHAR(1024),
  create_time           DATETIME,

  PRIMARY KEY (id)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =utf8;

DROP TABLE IF EXISTS deployment;
CREATE TABLE deployment (
  mode                   VARCHAR(32) NOT NULL,
  node_id                INT(10),
  node_name              VARCHAR(64) NOT NULL,
  node_ip                VARCHAR(64) NOT NULL,
  node_port              INT(2)      NOT NULL,
  root_path              VARCHAR(64) NOT NULL,
  central_node_ip        VARCHAR(64),
  central_node_port      INT(2),
  central_node_root_path VARCHAR(64)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =utf8;
