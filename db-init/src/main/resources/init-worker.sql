DROP TABLE IF EXISTS model_file;
CREATE TABLE
  model_file
(
  id                    INT(10)       NOT NULL AUTO_INCREMENT,
  node_id               INT(10),
  full_path             VARCHAR(1024) NOT NULL,
  datasource_type       VARCHAR(1024) COLLATE utf8_bin NOT NULL DEFAULT 'N/A',
  institute             VARCHAR(1024) COLLATE utf8_bin NOT NULL DEFAULT 'N/A',
  model                 VARCHAR(1024) COLLATE utf8_bin NOT NULL DEFAULT 'N/A',
  experiment            VARCHAR(1024) COLLATE utf8_bin NOT NULL DEFAULT 'N/A',
  frequency             VARCHAR(1024) COLLATE utf8_bin NOT NULL DEFAULT 'N/A',
  modeling_realm        VARCHAR(1024) COLLATE utf8_bin NOT NULL DEFAULT 'N/A',
  ensemble_member       VARCHAR(1024) COLLATE utf8_bin NOT NULL DEFAULT 'N/A',
  variable_name         VARCHAR(1024) COLLATE utf8_bin NOT NULL DEFAULT 'N/A',
  mip_table             VARCHAR(1024) COLLATE utf8_bin NOT NULL DEFAULT 'N/A',
  dataset_version       VARCHAR(1024) COLLATE utf8_bin NOT NULL DEFAULT 'N/A',
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
  geographical_info     VARCHAR(1024) COLLATE utf8_bin NOT NULL DEFAULT 'N/A',
  grid_label            VARCHAR(1024) COLLATE utf8_bin NOT NULL DEFAULT 'N/A',

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


DROP TABLE IF EXISTS task;
CREATE TABLE task (
  id        INT(10) NOT NULL AUTO_INCREMENT,
  uuid      VARCHAR(64) NOT NULL COMMENT 'task的唯一id',
  submition TEXT    NOT NULL,
  create_time DATETIME NOT NULL COMMENT '创建时间',

  PRIMARY KEY (id)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =utf8;


DROP TABLE IF EXISTS task_subtask_list;
CREATE TABLE task_subtask_list (
  task_id     INT(10) NOT NULL,
  sub_task_id INT(10) NOT NULL,
  node_id     INT(10),

  PRIMARY KEY (task_id, sub_task_id, node_id)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =utf8;

DROP TABLE IF EXISTS sub_task;
CREATE TABLE sub_task (
  id                   INT(10)     NOT NULL AUTO_INCREMENT,
  model                TEXT        NOT NULL,
  script               TEXT        NOT NULL,
  status               VARCHAR(32) NOT NULL,
  progress             INT(2),
  progress_description TEXT,
  failure_cause        TEXT,

  PRIMARY KEY (id)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =utf8;

DROP TABLE IF EXISTS sub_task_result;
CREATE TABLE sub_task_result (
  id          INT(10)       NOT NULL AUTO_INCREMENT,
  sub_task_id INT(10)       NOT NULL,
  type        VARCHAR(16)   NOT NULL,
  path        VARCHAR(1024) NOT NULL,

  PRIMARY KEY (id),
  INDEX idx_sub_task_result_id (sub_task_id)
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
