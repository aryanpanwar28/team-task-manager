# 📋 Copy-Paste Commands for Submission

**Use these exact commands - just copy and paste!**

---

## 1️⃣ VERIFY LOCALLY (Copy-Paste These Commands)

```bash
# Change to project directory
cd /Users/aryanpanwar/Downloads/ttm/backend

# Build the project
mvn -DskipTests clean package

# Start everything with Docker Compose
docker-compose up --build
```

**Then open in browser:**
- Frontend: http://localhost:3000
- Backend health: http://localhost:8080/health

---

## 2️⃣ GIT COMMANDS (Copy-Paste)

**Replace `YOUR_USERNAME` with your actual GitHub username!**

```bash
cd /Users/aryanpanwar/Downloads/ttm/backend

# Initialize git (if not already done)
git init

# Add GitHub as remote
git remote add origin git@github.com:YOUR_USERNAME/team-task-manager.git

# Stage all files
git add .

# Create commit
git commit -m "Team Task Manager - Full Stack Submission

- Spring Boot backend with JWT auth and role-based access
- HTML/CSS/JS frontend single-page application  
- MySQL database with auto-schema creation
- Complete project and task management system
- Deployed to Railway with Docker support"

# Push to GitHub
git push -u origin main
```

**Expected output:**
```
Enumerating objects: ...
Counting objects: ...
...
To github.com:YOUR_USERNAME/team-task-manager.git
 * [new branch]      main -> main
Branch 'main' set up to track remote branch 'main' from 'origin'.
```

---

## 3️⃣ RAILWAY DEPLOYMENT (Manual Steps - No Commands)

### 3a. Backend Deploy
1. Go to **https://railway.app**
2. Sign up or login with GitHub
3. Click **"New Project"**
4. Select **"Deploy from GitHub"**
5. Find and click **team-task-manager** repo
6. Wait for build to complete (~3-5 min)
7. When done, Railway shows your URL: `https://....railway.app`
   - **COPY THIS URL** (you'll need it later)

### 3b. Add MySQL
1. In Railway project dashboard, click **"Add Service"**
2. Select **"MySQL"**
3. Railway creates database automatically
4. Click on the MySQL service to see connection details
5. Note down these values from Railway:
   - Host
   - Port
   - Username
   - Password
   - Database name

### 3c. Set Environment Variables
1. Back in Railway project, look for **"Variables"** or **"Settings"**
2. Click **"New Variable"** and add these (copy from Railway MySQL details or generate):

```
SPRING_DATASOURCE_URL
Value: jdbc:mysql://gateway.railway.internal:3306/railway?useSSL=false&serverTimezone=UTC

SPRING_DATASOURCE_USERNAME
Value: root

SPRING_DATASOURCE_PASSWORD
Value: [copy from Railway MySQL service details]

JWT_SECRET
Value: [generate new one - see below]

CORS_ORIGINS
Value: *
```

**To generate JWT_SECRET, run this in terminal:**
```bash
openssl rand -base64 48
```
Copy the output and paste as JWT_SECRET value.

### 3d. Verify Backend Works
```bash
# Replace XXXX with your actual Railway URL
curl https://ttm-XXXX.railway.app/health

# Should return:
# {"status":"ok"}
```

---

## 4️⃣ FRONTEND DEPLOYMENT (GitHub Pages)

### Step 1: Enable GitHub Pages
1. Go to **github.com/YOUR_USERNAME/team-task-manager**
2. Click **Settings**
3. Click **Pages** (left sidebar)
4. Under "Build and deployment":
   - Source: **Deploy from a branch**
   - Branch: **main**
   - Folder: **/ (root)**
5. Click **Save**
6. Wait 1-2 minutes
7. GitHub shows your Pages URL at the top (like `https://YOUR_USERNAME.github.io/team-task-manager`)
   - **COPY THIS URL**

### Step 2: Update Frontend to Use Railway URL
```bash
cd /Users/aryanpanwar/Downloads/ttm/backend

# Open frontend/index.html in text editor
# Find this line (should be around line 15-20):
# <script src="/app.js"></script>

# Add these lines BEFORE that line:
# <script>
#   window.API_BASE = 'https://YOUR_RAILWAY_URL/api';
# </script>

# Example (replace ttm-abc123 with your actual Railway URL):
# <script>
#   window.API_BASE = 'https://ttm-abc123.railway.app/api';
# </script>
```

**Then commit and push:**
```bash
cd /Users/aryanpanwar/Downloads/ttm/backend

git add frontend/index.html

git commit -m "Add Railway backend URL to frontend"

git push
```

**Wait 2-3 minutes for GitHub Pages to rebuild, then test:**
```bash
curl https://YOUR_USERNAME.github.io/team-task-manager
# Should return HTML page
```

---

## 5️⃣ RECORD DEMO VIDEO

### Option: Use QuickTime (macOS)

```bash
# Step 1: Open QuickTime
open -a "QuickTime Player"
# OR use Command+Space and type "QuickTime"

# Step 2: File → New Screen Recording
# Step 3: Click red Record button

# Follow this script (memorize or have handy):
# - Say: "Team Task Manager - Full Stack Demo"
# - Show frontend login page
# - Login with admin account (admin@ttm.local / adminpass)
# - Show dashboard
# - Create a project (fill form, submit)
# - Create a task (fill form, submit)
# - Click Edit on task, change status, Save
# - Show dashboard with updated counts
# - Show both URLs:
#   - Backend: [your Railway URL]/health
#   - Frontend: [your GitHub Pages URL]
# - Say: "GitHub repo: [your GitHub URL]"
# - Say: "Code is complete and deployed!"

# Step 4: Click Stop
# Step 5: File → Export As... → MP4
# Save as: ~/Desktop/demo.mp4
```

### Upload to YouTube
```bash
# Go to https://youtube.com in browser
# Click your profile icon (top right) → Create a video
# Select ~/Desktop/demo.mp4
# Title: "Team Task Manager - Full Stack Demo"
# Description: "GitHub: https://github.com/YOUR_USERNAME/team-task-manager"
# Visibility: UNLISTED (very important!)
# Click Publish
# Copy the video URL (like: https://www.youtube.com/watch?v=ABCxyz...)
```

---

## 6️⃣ FINAL SUBMISSION

**Gather these 4 links:**

```
1. GitHub Repo:
   https://github.com/YOUR_USERNAME/team-task-manager

2. Backend (Live URL):
   https://ttm-XXXX.railway.app
   (test with: https://ttm-XXXX.railway.app/health)

3. Frontend (Live URL):
   https://YOUR_USERNAME.github.io/team-task-manager

4. Demo Video:
   https://www.youtube.com/watch?v=...
```

**Submit these 4 links to your instructor before 5 PM!**

---

## 🔍 QUICK VERIFICATION COMMANDS

```bash
# Test backend is live (replace URL)
curl https://ttm-XXXX.railway.app/health

# Test frontend loads (replace URL)
curl -s https://YOUR_USERNAME.github.io/team-task-manager | head -20

# Check git status
cd /Users/aryanpanwar/Downloads/ttm/backend
git log --oneline -5
git remote -v

# Verify local build
mvn -DskipTests clean package -q && echo "✅ Build successful"
```

---

## ❌ Common Issues & Fixes

### GitHub push failed
```bash
# If you get "permission denied", use HTTPS instead:
cd /Users/aryanpanwar/Downloads/ttm/backend
git remote set-url origin https://github.com/YOUR_USERNAME/team-task-manager.git
git push -u origin main
```

### Railway backend not responding
```bash
# Wait 2-3 minutes after first push (build takes time)
# Then test:
curl https://ttm-XXXX.railway.app/health
```

### Frontend shows blank or can't login
```bash
# Check that window.API_BASE is set in frontend/index.html
# Should look like:
# <script>
#   window.API_BASE = 'https://ttm-XXXX.railway.app/api';
# </script>
# <script src="/app.js"></script>

# Then test API is reachable:
curl https://ttm-XXXX.railway.app/api/health
```

### MySQL connection error on Railway
```bash
# Check MySQL service is created in Railway
# In Railway dashboard, verify MySQL service exists
# Then verify env vars are set correctly in Variables section
```

---

## 📝 NOTES

- Replace `YOUR_USERNAME` with your actual GitHub username
- Replace `ttm-XXXX` with your actual Railway URL (you get it after deployment)
- Ensure Docker Desktop is running before running docker-compose
- Make sure JWT_SECRET is very long (openssl rand -base64 48 generates a good one)
- GitHub Pages rebuild takes 1-3 minutes after push
- Railway deployment takes 3-5 minutes after first push

---

## ✅ BEFORE YOU SUBMIT

Check all these boxes:

- [ ] `docker-compose up --build` works locally
- [ ] Can login and create tasks on local frontend
- [ ] Git push succeeded (check github.com)
- [ ] Backend deployed on Railway (curl health endpoint works)
- [ ] MySQL connected in Railway (no DB connection errors)
- [ ] Frontend deployed on GitHub Pages (URL loads)
- [ ] Demo video recorded and uploaded to YouTube (unlisted)
- [ ] All 4 links are correct and working
- [ ] Time is before 5 PM

**You're ready to submit! 🎉**
