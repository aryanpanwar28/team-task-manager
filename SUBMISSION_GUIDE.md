# 📋 SUBMISSION GUIDE - Team Task Manager

**Deadline: Today before 5 PM**

---

## ✅ Pre-Submission Checklist

### 1. Local Verification
Before submitting, verify everything works locally:

```bash
# Step 1: Build the project
cd /Users/aryanpanwar/Downloads/ttm/backend
mvn -DskipTests clean package

# Step 2: Start with Docker Compose (easiest)
docker-compose up --build

# OR start manually:
docker run --name ttm-mysql -e MYSQL_ROOT_PASSWORD=jaat12345 -e MYSQL_DATABASE=ttm -p 3306:3306 -d mysql:8.0
export JWT_SECRET="$(openssl rand -base64 48)"
java -jar target/team-task-manager-1.0.0.jar

# Step 3: Verify
curl http://localhost:8080/health        # Should return {"status":"ok"}
curl http://localhost:8080/               # Should return HTML (frontend page)
curl http://localhost:3000/               # Frontend (if using docker-compose)
```

### 2. Test the Application
- Open http://localhost:8080 (or http://localhost:3000 with docker-compose)
- Sign up as an admin user (or use seeded: admin@ttm.local / adminpass)
- Create a project
- Create a task in that project
- Edit the task and change its status
- View the dashboard (verify task counts update)

---

## 📤 Submission Steps

### Step 1: Push Code to GitHub

```bash
cd /Users/aryanpanwar/Downloads/ttm/backend

# Initialize git (if not already done)
git init

# Add remote (replace with your GitHub repo URL)
git remote add origin git@github.com:YOUR_USERNAME/team-task-manager.git

# Add all files
git add .

# Commit
git commit -m "Team Task Manager - Full Stack Submission

- Backend: Spring Boot REST APIs with JWT authentication
- Frontend: HTML/CSS/JS single-page application
- Database: MySQL with role-based access control
- Features: Projects, Tasks, Dashboard, User Management
- Deployment: Railway ready with docker-compose support"

# Push to GitHub
git push -u origin main
```

**⚠️ Important**: Make sure these files are committed:
- ✅ `pom.xml` - Maven build file
- ✅ `Dockerfile` - Backend container build
- ✅ `docker-compose.yml` - Full stack orchestration
- ✅ `railway.json` - Railway deployment config
- ✅ `README.md` - Comprehensive guide (updated)
- ✅ `.env.example` - Environment variables template
- ✅ `src/main/java/` - All backend code
- ✅ `src/main/resources/` - Frontend static files + config
- ✅ `frontend/` - Frontend source files

### Step 2: Deploy Backend to Railway

1. **Go to railway.app** → Sign up or login
2. **Create New Project** → "Deploy from GitHub"
3. **Select your repository** from the list
4. **Wait for Railway to build** (it will detect `railway.json` and Dockerfile)
5. **Add MySQL Service**:
   - Click "Add Service" → "MySQL"
   - Railway will create a database instance
6. **Configure Environment Variables** in Railway dashboard:
   - `SPRING_DATASOURCE_URL` - Copy the JDBC URL from Railway MySQL service details
   - `SPRING_DATASOURCE_USERNAME` - Usually `root` or the Railway-provided username
   - `SPRING_DATASOURCE_PASSWORD` - The password Railway generated
   - `JWT_SECRET` - Generate one: `openssl rand -base64 48`
   - `CORS_ORIGINS` - Set to `*` or your frontend URL

7. **Get Your Live Backend URL**:
   - Railway will show something like: `https://project.railway.app`
   - Health check: `https://project.railway.app/health` (should return `{"status":"ok"}`)

### Step 3: Deploy Frontend (Choose One)

#### Option A: GitHub Pages (Free & Easy)
1. Enable GitHub Pages in your repo settings
2. Set source to `main` branch, `/docs` or `/` folder
3. Update `frontend/index.html` to set API base (before the script tag that loads app.js):
```html
<script>
  window.API_BASE = 'https://YOUR_RAILWAY_BACKEND_URL/api';
</script>
```
4. Frontend will be at: `https://yourusername.github.io/team-task-manager`

#### Option B: Railway Static Service
1. In your Railway project, add new service → "Static Site"
2. Configure to serve the `frontend/` directory
3. Update `frontend/index.html` to set `window.API_BASE` to your Railway backend URL
4. Frontend URL will be provided by Railway

#### Option C: Serve from Backend
If backend and frontend are on the same domain (both at https://project.railway.app):
- Frontend is automatically served from `/` (no additional deployment needed)
- API calls to `/api` will work via reverse proxy

### Step 4: Create Demo Video (2–5 minutes)

**Script:**
1. (0:00–0:20) Show the screen, say: "Team Task Manager - Full Stack Demo"
2. (0:20–0:50) Open frontend at your live URL, show login/signup page
3. (0:50–1:20) Login or signup as admin, show the dashboard
4. (1:20–1:50) Create a new project (show form filled and submitted)
5. (1:50–2:20) Create a task in that project (show form and task appearing in list)
6. (2:20–2:50) Edit the task: click Edit, change status to IN_PROGRESS, click Save
7. (2:50–3:20) Show dashboard again - verify task counts have updated
8. (3:20–3:50) Quickly show:
   - Backend health endpoint: `https://project.railway.app/health`
   - Frontend URL: `https://yourfrontend.github.io`
9. (3:50–4:00) End screen with GitHub repo link and any closing remarks

**Recording Tips:**
- Use QuickTime (⌘ + Shift + 5 on macOS) or Loom for easy recording
- Keep it steady, narrate clearly
- Show happy path only (no errors)
- Upload to YouTube (unlisted) or save as MP4

**Upload to YouTube:**
- Go to YouTube, click upload
- Set visibility to "Unlisted" (so only people with link can see)
- Copy the YouTube link (e.g., `https://www.youtube.com/watch?v=...`)

---

## 📝 Final Submission Deliverables

Prepare these items to submit (gather URLs and files):

1. **📌 GitHub Repository URL**
   - Example: `https://github.com/yourusername/team-task-manager`

2. **🚀 Live Backend URL** 
   - Example: `https://teamtaskmanager.railway.app`
   - Verify with: `curl https://teamtaskmanager.railway.app/health`

3. **🌐 Live Frontend URL**
   - Example: `https://yourusername.github.io/team-task-manager` (GitHub Pages)
   - Or Railway static URL if you chose that

4. **📄 README.md** 
   - Already in your repo (reviewed and complete)

5. **🎥 Demo Video Link**
   - Example: `https://www.youtube.com/watch?v=...` (YouTube unlisted)

6. **📋 This Submission Form** (filled out):
```
Project Name: Team Task Manager
GitHub Repo: [paste your repo URL]
Backend URL: [paste Railway URL]
Frontend URL: [paste frontend URL]
Demo Video: [paste YouTube/video link]

Features Implemented:
✅ Authentication (signup/login with JWT)
✅ Role-based access (Admin/Member)
✅ Project management (create, view, add members)
✅ Task management (create, edit, assign, status tracking)
✅ Dashboard (task counts, overdue, status breakdown)
✅ REST APIs with MySQL
✅ Deployed to Railway
✅ Full-stack working end-to-end
```

---

## 🚨 Verification Before Submission

```bash
# Test backend is live
curl https://your-backend-url/health

# Test frontend loads
curl -s https://your-frontend-url/ | grep -o "Team Task Manager"

# Test API endpoints work (if you have a token)
curl -X POST https://your-backend-url/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@ttm.local","password":"adminpass"}'

# Verify repo has all files
cd /Users/aryanpanwar/Downloads/ttm/backend
git log --oneline  # Should show your commits
git remote -v      # Should show GitHub URL
```

---

## 💡 If Something Goes Wrong

### Backend not starting on Railway
- Check Environment Variables are set correctly
- Check logs in Railway dashboard (Deployments tab)
- Ensure MySQL service is added and connected

### Frontend not connecting to backend
- Verify `window.API_BASE` is set in `frontend/index.html`
- Check browser console (F12) for CORS errors
- Ensure backend URL is correct and API endpoints work

### Local testing fails
- Rebuild: `mvn clean package`
- Start fresh MySQL: `docker stop ttm-mysql && docker rm ttm-mysql` then restart
- Use strong JWT secret: `export JWT_SECRET="$(openssl rand -base64 48)"`

---

## ✨ You're Ready!

Your submission is complete when you have:
1. ✅ Code pushed to GitHub with all files
2. ✅ Backend deployed and running on Railway
3. ✅ Frontend deployed and accessible
4. ✅ Demo video recorded and linked
5. ✅ All 3 requirements met (Live URL, GitHub repo, README, demo video)

**Good luck! 🚀 Submit before 5 PM!**
