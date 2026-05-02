# ✅ FINAL SUBMISSION CHECKLIST

**Print this out or keep it open while you work!**

---

## 📍 SECTION 1: LOCAL VERIFICATION (5 minutes)

### Pre-Check
- [ ] Docker Desktop is open/running
- [ ] Terminal open and can navigate to project

### Build & Run
```bash
cd /Users/aryanpanwar/Downloads/ttm/backend
mvn -DskipTests clean package
docker-compose up --build
```
- [ ] Build completes successfully (no errors)
- [ ] All services start (MySQL, Backend, Frontend)

### Test Application
- [ ] Open http://localhost:3000 in browser
- [ ] Frontend loads with login form
- [ ] Click "Signup" and create account OR use admin@ttm.local / adminpass
- [ ] Login successful
- [ ] Dashboard appears with 0 tasks
- [ ] Create Project form works
- [ ] Create a project successfully
- [ ] Create Task form works
- [ ] Create a task successfully
- [ ] Task appears in task list with TODO status
- [ ] Edit button appears on task
- [ ] Click Edit, change status to IN_PROGRESS, Save
- [ ] Task status updates in list
- [ ] Dashboard shows 1 total task, 0 TODO, 1 IN_PROGRESS

**Status: ✅ LOCAL WORKS** → Proceed to Step 2

---

## 📍 SECTION 2: GITHUB SETUP & PUSH (2 minutes)

### Create GitHub Repo
- [ ] Go to github.com
- [ ] Create new repository: `team-task-manager`
- [ ] Make it PUBLIC (important for GitHub Pages later)
- [ ] Do NOT add README, .gitignore, license (we have them already)
- [ ] Click "Create repository"
- [ ] Copy the SSH or HTTPS URL

### Git Commands
```bash
cd /Users/aryanpanwar/Downloads/ttm/backend

# Initialize git
git init

# Add GitHub as remote (use SSH or HTTPS from above)
git remote add origin git@github.com:YOUR_USERNAME/team-task-manager.git
# OR
git remote add origin https://github.com/YOUR_USERNAME/team-task-manager.git

# Check all files
git status
```
- [ ] Shows many files to commit (should see src/, frontend/, pom.xml, etc.)

```bash
# Commit all files
git add .
git commit -m "Team Task Manager - Full Stack Submission

- Spring Boot backend with REST APIs
- MySQL database with auto schema
- HTML/CSS/JS frontend SPA
- JWT authentication & RBAC
- Complete project & task management
- Docker & Railway ready"

# Push to GitHub
git push -u origin main
```
- [ ] Push completes (may take 10-20 seconds)
- [ ] No errors in output
- [ ] Verify on github.com: your repo shows all files

**Status: ✅ CODE ON GITHUB** → Proceed to Step 3

---

## 📍 SECTION 3: RAILWAY DEPLOYMENT (15 minutes)

### 3A: Create Railway Account & Project
- [ ] Go to https://railway.app
- [ ] Sign up or login (use GitHub for quick signup)
- [ ] Click "New Project"
- [ ] Select "Deploy from GitHub"
- [ ] Authorize Railway to access GitHub
- [ ] Select your `team-task-manager` repository
- [ ] Railway shows "Connecting"

### 3B: Wait for Build
- [ ] See "Building..." status
- [ ] Wait 3-5 minutes for build to complete
- [ ] Check "Deployments" tab for build progress
- [ ] Build completes with green checkmark
- [ ] Backend URL appears (like `https://ttm-abc123.railway.app`)
- [ ] **SAVE THIS URL** - you'll need it multiple times

### 3C: Add MySQL Service
- [ ] In Railway dashboard, click "Add Service"
- [ ] Select "MySQL"
- [ ] Railway creates database instance
- [ ] Click MySQL service to see connection details
- [ ] Note the connection string/details

### 3D: Set Environment Variables
- [ ] In Railway dashboard, go to "Variables" section
- [ ] Click "New Variable" and add:

**Variable 1: SPRING_DATASOURCE_URL**
```
Value: jdbc:mysql://gateway.railway.internal:3306/railway?useSSL=false&serverTimezone=UTC
```
- [ ] Added

**Variable 2: SPRING_DATASOURCE_USERNAME**
```
Value: root
```
- [ ] Added

**Variable 3: SPRING_DATASOURCE_PASSWORD**
```
Value: [copy from Railway MySQL service details]
```
- [ ] Added

**Variable 4: JWT_SECRET**
```
Generate: openssl rand -base64 48
Example output: abc123xyz456...
Value: [paste the generated secret]
```
- [ ] Added

**Variable 5: CORS_ORIGINS**
```
Value: *
```
- [ ] Added

### 3E: Verify Backend is Live
```bash
# Use your actual Railway URL
curl https://ttm-XXXX.railway.app/health
```
- [ ] Returns: `{"status":"ok"}`
- [ ] Backend is working! ✅

### 3F: Save Your Railway URL
```
BACKEND_URL = https://ttm-XXXX.railway.app
```
- [ ] **WRITE THIS DOWN** - needed for frontend

**Status: ✅ BACKEND DEPLOYED ON RAILWAY** → Proceed to Step 4

---

## 📍 SECTION 4: FRONTEND DEPLOYMENT (5 minutes)

### 4A: Enable GitHub Pages
- [ ] Go to github.com/YOUR_USERNAME/team-task-manager
- [ ] Click "Settings" tab
- [ ] Click "Pages" in left sidebar
- [ ] Under "Build and deployment":
  - [ ] Source: Select "Deploy from a branch"
  - [ ] Branch: Select "main"
  - [ ] Folder: Select "/ (root)"
- [ ] Click "Save"
- [ ] Wait 1-2 minutes
- [ ] GitHub shows your Pages URL (write it down):
```
FRONTEND_URL = https://YOUR_USERNAME.github.io/team-task-manager
```

### 4B: Update Frontend to Use Railway URL
```bash
cd /Users/aryanpanwar/Downloads/ttm/backend

# Open frontend/index.html in text editor
# Find this line (around line 15-20):
# <script src="/app.js"></script>

# Add these 4 lines BEFORE it:
# <script>
#   window.API_BASE = 'https://YOUR_RAILWAY_URL/api';
# </script>

# Example (replace with your actual Railway URL):
# <script>
#   window.API_BASE = 'https://ttm-abc123.railway.app/api';
# </script>
```
- [ ] Edited index.html with Railway URL

### 4C: Commit & Push Change
```bash
cd /Users/aryanpanwar/Downloads/ttm/backend

git add frontend/index.html

git commit -m "Add Railway backend URL to frontend"

git push
```
- [ ] Push successful
- [ ] No errors

### 4D: Wait for GitHub Pages Rebuild
- [ ] Wait 2-3 minutes (check: https://github.com/YOUR_USERNAME/team-task-manager/deployments)
- [ ] Should see green checkmark on latest deployment

### 4E: Verify Frontend Works
```bash
curl https://YOUR_USERNAME.github.io/team-task-manager
```
- [ ] Returns HTML page
- [ ] Can open in browser: https://YOUR_USERNAME.github.io/team-task-manager
- [ ] Frontend loads
- [ ] Can login (test with a quick signup and login)

**Status: ✅ FRONTEND DEPLOYED ON GITHUB PAGES** → Proceed to Step 5

---

## 📍 SECTION 5: RECORD DEMO VIDEO (10 minutes)

### 5A: Prepare Demo Script
- [ ] Have paper/notes with login credentials nearby
- [ ] Know what project/task names you'll use
- [ ] Have QuickTime or screen recorder open
- [ ] Test audio (mic working)

### 5B: Record (Follow This Script)
**0:00-0:20** - Title Slide
- [ ] Say: "Team Task Manager - Full Stack Demo"

**0:20-0:50** - Show Frontend
- [ ] Open your frontend URL in browser
- [ ] Show login page
- [ ] Say: "This is the web application deployed at [your frontend URL]"

**0:50-1:30** - Login
- [ ] Click Signup or use auto-seeded admin
- [ ] Fill in credentials
- [ ] Click Login
- [ ] Say: "Logging in with authenticated user"
- [ ] Show dashboard with 0 tasks

**1:30-2:10** - Create Project
- [ ] Fill in project form (name, description)
- [ ] Click "Create Project"
- [ ] Show project appears in list
- [ ] Say: "Successfully created a new project"

**2:10-2:50** - Create Task
- [ ] Select the project from dropdown
- [ ] Fill in task form (title, description, priority, date)
- [ ] Click "Create Task"
- [ ] Show task appears in task list
- [ ] Say: "Task created with TODO status"

**2:50-3:30** - Edit Task
- [ ] Click "Edit" on the task
- [ ] Show edit form opens
- [ ] Change status to "IN_PROGRESS"
- [ ] Click "Save"
- [ ] Show task status updates in list
- [ ] Say: "Task status updated successfully"

**3:30-4:00** - Dashboard & URLs
- [ ] Scroll to top, show dashboard
- [ ] Say: "Dashboard now shows 1 total, 0 TODO, 1 IN_PROGRESS"
- [ ] Show two URLs:
  - [ ] Backend: https://ttm-XXXX.railway.app/health
  - [ ] Frontend: https://yourusername.github.io/team-task-manager
- [ ] Say: "Application is fully deployed and working"

**4:00-4:30** - Closing
- [ ] Say: "GitHub repo: [your GitHub URL]"
- [ ] Say: "Full code and documentation in the repository"
- [ ] Say: "Thank you!"

- [ ] End recording

### 5C: Save Video
- [ ] Save as: `demo.mp4` on Desktop
- [ ] Check file size is reasonable (should be 50-200 MB for 4-5 min video)

**Status: ✅ DEMO VIDEO RECORDED** → Proceed to Step 6

---

## 📍 SECTION 6: UPLOAD DEMO VIDEO (3 minutes)

### 6A: Upload to YouTube
- [ ] Go to https://youtube.com
- [ ] Click profile icon (top right)
- [ ] Select "Create a video"
- [ ] Click "Upload video"
- [ ] Select `demo.mp4` from Desktop
- [ ] Wait for upload to complete

### 6B: Fill Video Details
- [ ] Title: `Team Task Manager - Full Stack Demo`
- [ ] Description: `GitHub: https://github.com/YOUR_USERNAME/team-task-manager`
- [ ] Visibility: **UNLISTED** (very important!)
- [ ] Click "Publish"

### 6C: Get Video URL
- [ ] Wait for processing (usually 1-2 minutes)
- [ ] Copy video URL (should be: `https://www.youtube.com/watch?v=...`)
- [ ] **SAVE THIS URL**

**Status: ✅ DEMO VIDEO LIVE ON YOUTUBE** → Proceed to Step 7

---

## 📍 SECTION 7: FINAL VERIFICATION (2 minutes)

### Collect Your 4 Submission Links
- [ ] **GitHub Repo:** `https://github.com/YOUR_USERNAME/team-task-manager`
- [ ] **Backend URL:** `https://ttm-XXXX.railway.app`
- [ ] **Frontend URL:** `https://YOUR_USERNAME.github.io/team-task-manager`
- [ ] **Demo Video:** `https://www.youtube.com/watch?v=...`

### Final Tests
```bash
# Test 1: Backend health
curl https://ttm-XXXX.railway.app/health
# Should return: {"status":"ok"}

# Test 2: Frontend loads
curl https://YOUR_USERNAME.github.io/team-task-manager
# Should return HTML

# Test 3: GitHub repo accessible
curl https://github.com/YOUR_USERNAME/team-task-manager
# Should return HTML showing repo

# Test 4: YouTube video accessible
# Open in browser: https://www.youtube.com/watch?v=...
# Should show video
```

- [ ] All 4 tests pass

### Check Deadline
- [ ] Current time is still BEFORE 5 PM

**Status: ✅ READY TO SUBMIT** → Proceed to Step 8

---

## 📍 SECTION 8: SUBMIT TO INSTRUCTOR (2 minutes)

### Prepare Submission Message
```
TEAM TASK MANAGER - SUBMISSION

📌 GitHub Repository:
https://github.com/YOUR_USERNAME/team-task-manager

🚀 Live Backend URL:
https://ttm-XXXX.railway.app
(Health check: https://ttm-XXXX.railway.app/health)

🌐 Live Frontend URL:
https://YOUR_USERNAME.github.io/team-task-manager

🎥 Demo Video:
https://www.youtube.com/watch?v=...

Features Implemented:
✅ Authentication (Signup/Login with JWT)
✅ Role-based Access (Admin/Member)
✅ Project Management
✅ Task Creation & Assignment
✅ Task Status Tracking
✅ Dashboard with Statistics
✅ REST APIs with MySQL Database
✅ Deployed to Railway

All code, documentation, and deployment complete.
```

### Submit
- [ ] Copy text above
- [ ] Paste to instructor email/submission form
- [ ] Include your 4 links
- [ ] Submit BEFORE 5 PM

**Status: ✅ SUBMITTED** → DONE! 🎉

---

## 🎉 FINAL STATUS

- [ ] Local testing: ✅
- [ ] GitHub push: ✅
- [ ] Backend deployed: ✅
- [ ] Frontend deployed: ✅
- [ ] Demo video: ✅
- [ ] 4 links submitted: ✅

**SUBMISSION COMPLETE!**

---

## 📊 TIME BREAKDOWN

| Step | Time | Cumulative |
|---|---|---|
| 1. Local Verification | 5 min | 5 min |
| 2. GitHub Push | 2 min | 7 min |
| 3. Railway Deploy | 15 min | 22 min |
| 4. Frontend Deploy | 5 min | 27 min |
| 5. Record Demo | 10 min | 37 min |
| 6. Upload Video | 3 min | 40 min |
| 7. Final Verification | 2 min | 42 min |
| 8. Submit | 2 min | 44 min |

**Total: ~45 minutes** (includes waiting times)

---

## 🆘 NEED HELP?

- **Local issues?** → See: `README.md` (Troubleshooting)
- **Deployment issues?** → See: `COPY_PASTE_COMMANDS.md` (Troubleshooting)
- **Can't remember what to do?** → See: `QUICK_SUBMIT.md`
- **Need exact commands?** → See: `COPY_PASTE_COMMANDS.md`
- **What did I build?** → See: `SUBMISSION_SUMMARY.md`

---

**Print this checklist or open it during submission process.**

**Good luck! 🚀 You've got this!**

**⏰ DEADLINE: TODAY before 5 PM**
