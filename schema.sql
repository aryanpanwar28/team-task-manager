-- Reference schema (Hibernate auto-generates this; provided for reference)
CREATE TABLE IF NOT EXISTS users (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  role VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS projects (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  description VARCHAR(1000),
  owner_id BIGINT NOT NULL,
  created_at DATETIME NOT NULL,
  CONSTRAINT fk_proj_owner FOREIGN KEY (owner_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS project_members (
  project_id BIGINT NOT NULL,
  user_id BIGINT NOT NULL,
  PRIMARY KEY (project_id, user_id),
  CONSTRAINT fk_pm_proj FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE,
  CONSTRAINT fk_pm_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS tasks (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(255) NOT NULL,
  description VARCHAR(2000),
  status VARCHAR(20) NOT NULL,
  priority VARCHAR(20) NOT NULL,
  due_date DATE,
  project_id BIGINT NOT NULL,
  assignee_id BIGINT,
  created_at DATETIME NOT NULL,
  CONSTRAINT fk_task_proj FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE,
  CONSTRAINT fk_task_assignee FOREIGN KEY (assignee_id) REFERENCES users(id) ON DELETE SET NULL
);
