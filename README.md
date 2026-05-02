# Team Task Manager - Full-Stack Assignment

A complete web application for team task management with role-based access control, built with Spring Boot (backend) and HTML/CSS/JS (frontend).

## 📋 Features Implemented

✅ **Authentication** - Signup/Login with JWT tokens  
✅ **Project Management** - Create projects and add team members  
✅ **Task Management** - Create, assign, and track tasks with status and priority  
✅ **Dashboard** - View task counts by status (TODO, IN_PROGRESS, DONE) and overdue tasks  
✅ **Role-Based Access** - Admin and Member roles with appropriate permissions  
✅ **REST APIs** - Full REST backend with proper validations and relationships  
✅ **MySQL Database** - Persistent data storage with proper schema  
✅ **Frontend UI** - Responsive HTML/CSS/JS single-page application  

---

## 🚀 Quick Start (Local Development)

### Prerequisites
- Java 17 or higher
- Maven 3.8+
- Docker & Docker Compose (recommended) OR MySQL running locally

### Option 1: Run Everything with Docker Compose (Recommended)

```bash
cd /Users/aryanpanwar/Downloads/ttm/backend
docker-compose up --build
```

Then open:
- **Frontend**: http://localhost:3000
- **Backend API**: http://localhost:8080/api
- **Health Check**: http://localhost:8080/health

Stop with:
```bash
docker-compose down
```

### Option 2: Run Backend JAR Locally + Static Files

```bash
# Build the project (creates jar with embedded frontend)
mvn -DskipTests package

# Start MySQL (if not running)
docker run --name ttm-mysql \
  -e MYSQL_ROOT_PASSWORD=jaat12345 \
  -e MYSQL_DATABASE=ttm \
  -p 3306:3306 \
  -d mysql:8.0

# Generate a strong JWT secret and run the jar
export JWT_SECRET="$(openssl rand -base64 48)"
java -jar target/team-task-manager-1.0.0.jar
```

Then open:
- **Frontend + API**: http://localhost:8080
- **Health Check**: http://localhost:8080/health

### Option 3: Run with Maven

```bash
export JWT_SECRET="$(openssl rand -base64 48)"
SPRING_DATASOURCE_PASSWORD=jaat12345 mvn spring-boot:run
```

---

## 📝 API Usage & Demo Commands

### 1. Signup (create an admin user)
```bash
curl -X POST http://localhost:8080/api/auth/signup \
  -H "Content-Type: application/json" \
  -d '{"name":"Admin","email":"admin@example.com","password":"adminpass","role":"ADMIN"}'
```

### 2. Login (get JWT token)
```bash
TOKEN=$(curl -s -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@example.com","password":"adminpass"}' | jq -r '.token')

echo "Your token: $TOKEN"
```

### 3. Create a Project
```bash
curl -X POST http://localhost:8080/api/projects \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{"name":"Demo Project","description":"Project for demo"}'
```

### 4. Create a Task
```bash
curl -X POST http://localhost:8080/api/projects/1/tasks \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{"title":"First Task","description":"Demo task","priority":"HIGH","dueDate":"2026-12-31"}'
```

### 5. Update Task Status
```bash
curl -X PATCH http://localhost:8080/api/tasks/1/status \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{"status":"IN_PROGRESS"}'
```

### 6. View Dashboard
```bash
curl http://localhost:8080/api/dashboard -H "Authorization: Bearer $TOKEN" | jq
```

---

## 📦 Database Schema

The application uses MySQL with the following main tables:
- **users** - user accounts with roles (ADMIN/MEMBER)
- **projects** - team projects
- **tasks** - tasks within projects (with status: TODO/IN_PROGRESS/DONE)
- **project_members** - many-to-many relationship for project members

Initial schema is created automatically via Hibernate DDL (`spring.jpa.hibernate.ddl-auto=update`).

**Automatic Admin Seeder**: If the database is empty on startup, a default admin user is created:
- Email: `admin@ttm.local`
- Password: `adminpass`

---

## 🌐 Deployment to Railway

### Step 1: Push to GitHub
```bash
git add .
git commit -m "Team Task Manager - full stack submission"
git push -u origin main
```

### Step 2: Create Railway Project
1. Go to [railway.app](https://railway.app)
2. Click "New Project" → "Deploy from GitHub"
3. Select your repository
4. Railway will auto-detect `railway.json` and build using the Dockerfile

### Step 3: Set Up MySQL Database on Railway
1. In Railway dashboard, click "Add Service" → "MySQL"
2. Railway will create a MySQL instance and provide connection details

### Step 4: Configure Environment Variables
In Railway project settings, set these variables:
```
SPRING_DATASOURCE_URL = jdbc:mysql://[host]:[port]/[db]?useSSL=false&serverTimezone=UTC
SPRING_DATASOURCE_USERNAME = root
SPRING_DATASOURCE_PASSWORD = [your-password]
JWT_SECRET = [generate with: openssl rand -base64 48]
CORS_ORIGINS = *
```

Railway will provide the exact JDBC URL when you connect the MySQL service.

### Step 5: Deploy Frontend (Choose One Option)

**Option A: Use Railway Static Site Service (Easiest)**
1. Add a new Railway service → "Static Site"
2. Point to the `frontend/` directory
3. Update `frontend/index.html` to set API_BASE before app.js loads:
```html
<script>
  // Replace with your Railway backend URL
  window.API_BASE = 'https://your-backend.railway.app/api';
</script>
<script src="/app.js"></script>
```

**Option B: Deploy Frontend to GitHub Pages (Free)**
1. Enable GitHub Pages in your repo settings
2. Update `frontend/index.html` to set `window.API_BASE` to Railway backend URL
3. GitHub Pages will serve the frontend

### Step 6: Get Your Live URLs
After successful deployment:
- **Backend**: `https://your-project.railway.app`
- **Frontend**: `https://github.com/yourusername/yourrepo` (GitHub Pages) or Railway static URL
- **Health**: `https://your-project.railway.app/health`

---

## 📱 Frontend Usage

### Login/Signup
1. Open frontend (http://localhost:8080 or deployed URL)
2. Create an account or use seeded admin (`admin@ttm.local` / `adminpass`)
3. Login and receive JWT token (stored in browser)

### Create Project
1. Fill in project name and description
2. Click "Create Project"
3. Project appears in the projects list

### Create Task
1. Select a project from the dropdown
2. Fill in task details (title, description, priority, due date)
3. Click "Create Task"
4. Task appears in task list with TODO status

### Edit Task
1. Click "Edit" on any task
2. Modify title, status, assignee, or due date
3. Click "Save"
4. Changes persist immediately

### Add Team Members
1. Select a project
2. Enter member ID in "Manage Members" section
3. Click "Add Member"
4. Member is added to project

### View Dashboard
1. Dashboard shows:
   - Total task count
   - Tasks by status (TODO, IN_PROGRESS, DONE)
   - Overdue task count
   - My assigned tasks

---

## 🎥 Demo Video Checklist

Record a 2–5 minute demo showing:
1. ✅ Open frontend and login/signup
2. ✅ Create a new project
3. ✅ Create tasks in the project
4. ✅ Edit a task and change status
5. ✅ View dashboard with task counts
6. ✅ Explain deployment URLs

**Tip**: Use QuickTime (macOS), OBS, or ScreenFlow to record. Upload to YouTube (unlisted) or provide as MP4.

---

## 📤 Submission Checklist

**Before 5 PM, deliver:**

- [ ] **Live Backend URL** - Railway deployment (e.g., `https://project.railway.app`)
- [ ] **Live Frontend URL** - Railway static or GitHub Pages
- [ ] **GitHub Repository** - Push all code (backend, frontend, README, .env.example)
- [ ] **README.md** - This file (in repo root)
- [ ] **Demo Video** - 2–5 minutes showing full workflow (YouTube link or MP4)

---

## 🔧 Environment Variables

See `.env.example` for all variables. Key ones:
- `JWT_SECRET` - MUST be strong (256+ bits). Generate: `openssl rand -base64 48`
- `SPRING_DATASOURCE_URL` - MySQL JDBC connection string
- `SPRING_DATASOURCE_USERNAME` / `_PASSWORD` - DB credentials
- `CORS_ORIGINS` - Allowed frontend origins (default: `*`)

---

## 🐛 Troubleshooting

### Port already in use
```bash
# Use a different port
PORT=9090 java -jar target/team-task-manager-1.0.0.jar
```

### JWT secret too weak
```bash
# Generate a strong secret (256+ bits)
export JWT_SECRET="$(openssl rand -base64 48)"
java -jar target/team-task-manager-1.0.0.jar
```

### Database connection error
```bash
# Check MySQL is running
docker ps | grep mysql
# Or start MySQL:
docker run --name ttm-mysql -e MYSQL_ROOT_PASSWORD=jaat12345 -e MYSQL_DATABASE=ttm -p 3306:3306 -d mysql:8.0
```

### Frontend not loading
- Ensure backend is running: `curl http://localhost:8080/health`
- For docker-compose: verify all services started: `docker-compose ps`
- Check browser console (F12) for errors

---

## 📚 Architecture

**Backend** (Java Spring Boot):
- REST APIs for auth, projects, tasks, dashboard
- Spring Data JPA with MySQL
- JWT authentication with role-based access control
- DataSeeder for initial admin user

**Frontend** (HTML/CSS/JS):
- Single-page application (SPA)
- Fetches data from `/api` endpoints
- localStorage for JWT persistence
- Responsive UI with task/project management

**Deployment**:
- Backend: Docker container on Railway
- Frontend: Static files served by backend or GitHub Pages
- Database: Railway MySQL or local Docker MySQL

---

## 📝 Notes

- The app automatically creates schema and seeds an initial admin if DB is empty
- JWT tokens expire after 24 hours (configurable)
- CORS is enabled for all origins by default (set `CORS_ORIGINS` for production)
- Frontend uses relative `/api` paths, so it works in docker-compose out of the box