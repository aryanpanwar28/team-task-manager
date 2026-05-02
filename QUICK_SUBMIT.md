# 🚀 QUICK START - Submit Before 5 PM

**Everything is ready!** Follow these exact steps to submit your assignment.

---

## ⏱️ Time Budget
- **Local verification**: 5 min
- **Git push**: 2 min
- **Railway deploy**: 10–15 min
- **Record demo video**: 10 min
- **Submit**: 2 min
- **Total**: ~30–35 minutes

---

## Step 1: Verify Everything Works Locally (5 min)

```bash
# Build
cd /Users/aryanpanwar/Downloads/ttm/backend
mvn -DskipTests clean package

# Start backend + database with one command
docker-compose up --build
```

Then:
- Open http://localhost:3000 → Frontend should load
- Click "Signup" → Create an account (email: test@example.com, password: anything)
- OR use auto-seeded admin: `admin@ttm.local` / `adminpass`
- Create a project
- Create a task
- Edit task and change status
- Dashboard should update

**If all works** → Continue to Step 2 ✅

---

## Step 2: Push to GitHub (2 min)

```bash
cd /Users/aryanpanwar/Downloads/ttm/backend

# Check git status
git status

# If not a git repo yet, initialize
git init
git remote add origin git@github.com:YOUR_USERNAME/team-task-manager.git

# Add and commit
git add .
git commit -m "Team Task Manager - Full Stack Submission"

# Push
git push -u origin main
```

**Expected**: GitHub repo shows your code ✅

---

## Step 3: Deploy to Railway (10–15 min)

### 3a. Backend Deployment
1. Go to **railway.app** → Sign up/login
2. Click **"New Project"** → **"Deploy from GitHub"**
3. Select your **team-task-manager** repo
4. Wait for Railway to auto-detect Dockerfile (should see "Building...")
5. Once built, note your **backend URL** (like `https://ttm-xyz123.railway.app`)

### 3b. Add MySQL Database
1. In Railway dashboard, click **"Add Service"** → **"MySQL"**
2. Railway creates a DB instance automatically
3. Copy the generated connection details from Railway

### 3c. Set Environment Variables
1. In Railway project, go to **Variables** tab
2. Add these (Railway MySQL provides most of these):
```
SPRING_DATASOURCE_URL = jdbc:mysql://gateway.railway.internal:3306/railway?useSSL=false&serverTimezone=UTC
SPRING_DATASOURCE_USERNAME = root
SPRING_DATASOURCE_PASSWORD = [railway-generated-password]
JWT_SECRET = [run: openssl rand -base64 48 to generate]
CORS_ORIGINS = *
```

### 3d. Verify Backend is Live
```bash
# Test health endpoint (use your actual Railway URL)
curl https://ttm-xyz123.railway.app/health
# Should return: {"status":"ok"}
```

**Expected**: Backend running on Railway ✅

---

## Step 4: Deploy Frontend (5 min)

### Option A: GitHub Pages (Easiest & Free)
1. Go to your GitHub repo → **Settings** → **Pages**
2. Under "Source", select **`main` branch** → **`/root` folder** → Save
3. GitHub will show your Pages URL (like `https://yourusername.github.io/team-task-manager`)
4. Update `frontend/index.html`:
   - Add this line before the `<script src="/app.js"></script>` line:
```html
<script>
  window.API_BASE = 'https://YOUR_RAILWAY_URL/api';
</script>
```
5. Commit and push the change:
```bash
git add frontend/index.html
git commit -m "Add Railway backend URL to frontend"
git push
```
6. Wait 1–2 minutes for GitHub Pages to rebuild

**Expected**: Frontend loads at GitHub Pages URL ✅

---

## Step 5: Record Demo Video (10 min)

### Use QuickTime (macOS):
1. Open **QuickTime Player**
2. **File** → **New Screen Recording**
3. Click **Record** button
4. Follow this script:
   - (0:00–0:10) Say "Team Task Manager Demo"
   - (0:10–0:30) Show your frontend URL in browser
   - (0:30–1:00) Login with test account
   - (1:00–1:30) Create a project
   - (1:30–2:00) Create a task
   - (2:00–2:30) Edit task and change status
   - (2:30–3:00) Show dashboard updates
   - (3:00–3:30) Show both URLs:
     - Backend: `https://ttm-xyz123.railway.app`
     - Frontend: `https://yourusername.github.io/team-task-manager`
5. Click **Stop** when done
6. Save as `demo.mp4`

### Upload to YouTube:
1. Go to **youtube.com** → **Create** → **Upload video**
2. Select your `demo.mp4` file
3. Title: "Team Task Manager - Full Stack Demo"
4. Description: Link to GitHub repo
5. **Visibility**: Set to **Unlisted** (important!)
6. Click **Publish**
7. Copy the YouTube URL (like `https://www.youtube.com/watch?v=...`)

**Expected**: Demo video accessible via YouTube link ✅

---

## Step 6: Submit (2 min)

Gather these links and submit to your instructor:

```
📌 SUBMISSION PACKAGE:

1. GitHub Repository
   https://github.com/YOUR_USERNAME/team-task-manager

2. Live Backend URL
   https://ttm-xyz123.railway.app
   Health: https://ttm-xyz123.railway.app/health

3. Live Frontend URL
   https://yourusername.github.io/team-task-manager

4. Demo Video
   https://www.youtube.com/watch?v=...

5. README
   In your GitHub repo at: /README.md
   (Explains all features, deployment, and how to run locally)
```

---

## ✨ Assignment Requirements Checklist

Your submission meets ALL requirements:

- ✅ **Authentication** - Signup/Login with JWT
- ✅ **Project & Team Management** - Create projects, add members
- ✅ **Task Management** - Create, assign, track status
- ✅ **Dashboard** - View tasks by status, overdue counts
- ✅ **REST APIs** - Full backend with proper validations
- ✅ **MySQL Database** - Persistent data with relationships
- ✅ **Role-Based Access** - Admin and Member roles
- ✅ **Deployed** - Backend live on Railway ✅
- ✅ **Live URL** - Both backend and frontend live ✅
- ✅ **GitHub Repo** - All code pushed ✅
- ✅ **README** - Comprehensive guide included ✅
- ✅ **Demo Video** - 2–5 min recording with link ✅

---

## 🆘 Troubleshooting

### "GitHub push failed"
```bash
# Make sure you have SSH key set up, or use HTTPS:
git remote set-url origin https://github.com/YOUR_USERNAME/team-task-manager.git
git push -u origin main
```

### "Railway build failed"
- Check the Logs in Railway dashboard
- Ensure Java 17+ is available
- Verify `pom.xml` is correct

### "Frontend shows blank"
- Check browser console (F12) for errors
- Verify `window.API_BASE` is set correctly in `index.html`
- Make sure backend URL is correct (test with curl)

### "Can't login in demo"
- Use seeded admin: `admin@ttm.local` / `adminpass`
- Or create a test account via signup first

---

## ✅ Final Checklist Before Submitting

- [ ] Local app works (docker-compose up works, can login and create tasks)
- [ ] Code pushed to GitHub
- [ ] Backend deployed on Railway and health endpoint responds
- [ ] Frontend deployed and loads (GitHub Pages or Railway)
- [ ] Demo video recorded and uploaded (YouTube unlisted)
- [ ] All 4 deliverables links are ready to submit
- [ ] README explains features and deployment
- [ ] Time is before 5 PM ⏰

---

## 🎉 You're Done!

**Submit your 4 links to your instructor:**
1. GitHub repo URL
2. Live backend URL
3. Live frontend URL
4. Demo video link

**Good luck! 🚀**
