(function(){
  // Allow overriding API base when frontend is deployed separately (set window.API_BASE in index.html or nginx)
  const apiBase = window.API_BASE || '/api';
  const auth = { token: null };

  // DOM refs
  const loginForm = document.getElementById('login-form');
  const signupForm = document.getElementById('signup-form');
  const dashboard = document.getElementById('dashboard-section');
  const authSection = document.getElementById('auth-section');
  const btnLogin = document.getElementById('btn-login');
  const btnSignup = document.getElementById('btn-signup');
  const btnLogout = document.getElementById('btn-logout');

  // project/task form refs
  const createProjectName = document.getElementById('create-project-name');
  const createProjectDesc = document.getElementById('create-project-description');
  const createProjectSubmit = document.getElementById('create-project-submit');
  const createProjectError = document.getElementById('create-project-error');

  const projectList = document.getElementById('project-list');
  const projectSelect = document.getElementById('project-select');

  const createTaskTitle = document.getElementById('create-task-title');
  const createTaskDesc = document.getElementById('create-task-desc');
  const createTaskDue = document.getElementById('create-task-dueDate');
  const createTaskAssignee = document.getElementById('create-task-assigneeId');
  const createTaskPriority = document.getElementById('create-task-priority');
  const createTaskSubmit = document.getElementById('create-task-submit');
  const createTaskError = document.getElementById('create-task-error');

  const manageProjectId = document.getElementById('manage-project-id');
  const addMemberId = document.getElementById('add-member-id');
  const addMemberSubmit = document.getElementById('add-member-submit');
  const addMemberError = document.getElementById('add-member-error');

  // Persist token in localStorage
  function saveToken(t) { if (t) { localStorage.setItem('ttm_token', t); } else { localStorage.removeItem('ttm_token'); } }
  function loadToken() { const t = localStorage.getItem('ttm_token'); if (t) { setAuth(t); } }

  // call loadToken early
  loadToken();

  // set project selection change to update manageProjectId
  document.getElementById('project-select').addEventListener('change', (ev) => { manageProjectId.value = ev.target.value; loadTasks(ev.target.value); });

  // Add member to project
  addMemberSubmit.addEventListener('click', async () => {
    addMemberError.textContent = '';
    try {
      const pid = manageProjectId.value;
      const memberId = parseInt(addMemberId.value);
      if (!pid) { addMemberError.textContent = 'Select a project first'; return; }
      await post(`/projects/${pid}`, { name: 'noop', description: '', memberIds: [memberId] });
      addMemberId.value = '';
      await loadDashboard();
    } catch (e) { addMemberError.textContent = e.message || JSON.stringify(e); }
  });

  // Update setAuth to persist token
  function setAuth(token) {
    auth.token = token;
    saveToken(token);
    if (token) {
      btnLogout.style.display = 'inline-block';
      btnLogin.style.display = 'none';
      btnSignup.style.display = 'none';
    } else {
      btnLogout.style.display = 'none';
      btnLogin.style.display = 'inline-block';
      btnSignup.style.display = 'inline-block';
    }
  }

  // Update logout handler to clear localStorage
  btnLogout.addEventListener('click', () => { setAuth(null); showAuth(); });

  function headers() {
    const h = { 'Content-Type': 'application/json' };
    if (auth.token) h['Authorization'] = 'Bearer ' + auth.token;
    return h;
  }

  async function post(path, body) {
    const res = await fetch(apiBase + path, { method: 'POST', headers: headers(), body: JSON.stringify(body) });
    if (!res.ok) throw await res.json().catch(()=>new Error(res.statusText));
    return res.json();
  }

  async function get(path) {
    const res = await fetch(apiBase + path, { headers: headers() });
    if (!res.ok) throw await res.json().catch(()=>new Error(res.statusText));
    return res.json();
  }

  // Login
  document.getElementById('login-submit').addEventListener('click', async () => {
    const email = document.getElementById('login-email').value;
    const password = document.getElementById('login-password').value;
    try {
      const data = await post('/auth/login', { email, password });
      setAuth(data.token);
      showDashboard();
      await loadDashboard();
    } catch (e) {
      document.getElementById('login-error').textContent = e.message || JSON.stringify(e);
    }
  });

  // Signup
  document.getElementById('signup-submit').addEventListener('click', async () => {
    const name = document.getElementById('signup-name').value;
    const email = document.getElementById('signup-email').value;
    const password = document.getElementById('signup-password').value;
    try {
      const data = await post('/auth/signup', { name, email, password });
      // auto-login
      setAuth(data.token);
      showDashboard();
      await loadDashboard();
    } catch (e) {
      document.getElementById('signup-error').textContent = e.message || JSON.stringify(e);
    }
  });

  btnLogin.addEventListener('click', () => { loginForm.style.display = 'block'; signupForm.style.display = 'none'; dashboard.style.display='none'; });
  btnSignup.addEventListener('click', () => { signupForm.style.display = 'block'; loginForm.style.display = 'none'; dashboard.style.display='none'; });
  btnLogout.addEventListener('click', () => { setAuth(null); showAuth(); });

  // Create project
  createProjectSubmit.addEventListener('click', async () => {
    createProjectError.textContent = '';
    try {
      const body = { name: createProjectName.value, description: createProjectDesc.value };
      await post('/projects', body);
      createProjectName.value = '';
      createProjectDesc.value = '';
      await loadDashboard();
    } catch (e) { createProjectError.textContent = e.message || JSON.stringify(e); }
  });

  // Create task
  createTaskSubmit.addEventListener('click', async () => {
    createTaskError.textContent = '';
    try {
      const projectId = projectSelect.value;
      const body = {
        title: createTaskTitle.value,
        description: createTaskDesc.value,
        dueDate: createTaskDue.value || null,
        assigneeId: createTaskAssignee.value ? parseInt(createTaskAssignee.value) : null,
        priority: createTaskPriority.value
      };
      await post(`/projects/${projectId}/tasks`, body);
      createTaskTitle.value = '';
      createTaskDesc.value = '';
      createTaskDue.value = '';
      createTaskAssignee.value = '';
      await loadDashboard();
    } catch (e) { createTaskError.textContent = e.message || JSON.stringify(e); }
  });

  function showDashboard() {
    authSection.style.display = 'none';
    dashboard.style.display = 'block';
  }
  function showAuth() {
    authSection.style.display = 'block';
    dashboard.style.display = 'none';
    loginForm.style.display = 'block';
    signupForm.style.display = 'none';
  }

  async function loadDashboard() {
    try {
      const data = await get('/dashboard');
      document.getElementById('stat-total').textContent = data.total;
      document.getElementById('stat-todo').textContent = data.todo;
      document.getElementById('stat-inprogress').textContent = data.inProgress;
      document.getElementById('stat-done').textContent = data.done;
      document.getElementById('stat-overdue').textContent = data.overdue;
      // backend uses `myTasks` in DTO; fall back to `mine` if present for compatibility
      const myTasks = (data.myTasks ?? data.mine ?? 0);
      document.getElementById('stat-mine').textContent = myTasks;
      // load projects
      const projects = await get('/projects');
      projectList.innerHTML = '';
      projectSelect.innerHTML = '';
      projects.forEach(p => {
        const li = document.createElement('li'); li.textContent = p.name + ' — ' + p.description; projectList.appendChild(li);
        const opt = document.createElement('option'); opt.value = p.id; opt.textContent = p.name; projectSelect.appendChild(opt);
      });
      if (projects.length) loadTasks(projects[0].id);
    } catch (e) {
      console.error('Failed to load dashboard', e);
      if (e.message && e.message.toLowerCase().includes('unauthorized')) showAuth();
    }
  }

  async function loadTasks(projectId) {
    try {
      const tasks = await get(`/projects/${projectId}/tasks`);
      const tl = document.getElementById('task-list'); tl.innerHTML = '';
      tasks.forEach(t => {
        const li = document.createElement('li');
        li.innerHTML = `<strong>${t.title}</strong> — ${t.description || ''} <em>[${t.status}]</em> `;
        const editBtn = document.createElement('button'); editBtn.textContent = 'Edit'; editBtn.style.marginLeft='8px';
        editBtn.addEventListener('click', () => openEditForm(t));
        li.appendChild(editBtn);
        tl.appendChild(li);
      });
    } catch (e) { console.error('Failed to load tasks', e); }
  }

  function openEditForm(task) {
    document.getElementById('edit-task-id').value = task.id;
    document.getElementById('edit-task-title').value = task.title || '';
    document.getElementById('edit-task-desc').value = task.description || '';
    document.getElementById('edit-task-dueDate').value = task.dueDate || '';
    document.getElementById('edit-task-assigneeId').value = task.assigneeId || '';
    document.getElementById('edit-task-status').value = task.status || 'TODO';
    document.getElementById('edit-task-priority').value = task.priority || 'MEDIUM';
    document.getElementById('edit-task-form').style.display = 'block';
    window.scrollTo(0, document.getElementById('edit-task-form').offsetTop - 20);
  }

  document.getElementById('edit-task-cancel').addEventListener('click', () => {
    document.getElementById('edit-task-form').style.display = 'none';
  });

  document.getElementById('edit-task-save').addEventListener('click', async () => {
    const id = document.getElementById('edit-task-id').value;
    const body = {
      title: document.getElementById('edit-task-title').value,
      description: document.getElementById('edit-task-desc').value,
      dueDate: document.getElementById('edit-task-dueDate').value || null,
      assigneeId: document.getElementById('edit-task-assigneeId').value ? parseInt(document.getElementById('edit-task-assigneeId').value) : null,
      status: document.getElementById('edit-task-status').value,
      priority: document.getElementById('edit-task-priority').value
    };
    try {
      // Use PUT to update the task
      const res = await fetch(apiBase + `/tasks/${id}`, { method: 'PUT', headers: headers(), body: JSON.stringify(body) });
      if (!res.ok) throw await res.json().catch(()=>new Error(res.statusText));
      await loadDashboard();
      document.getElementById('edit-task-form').style.display = 'none';
    } catch (e) {
      document.getElementById('edit-task-error').textContent = e.message || JSON.stringify(e);
    }
  });

  document.getElementById('project-select').addEventListener('change', (ev) => loadTasks(ev.target.value));

  // initial state
  showAuth();
})();