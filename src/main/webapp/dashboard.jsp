<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="DTO.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Task Dashboard</title>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/Sortable/1.15.0/Sortable.min.js"></script>
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"
	rel="stylesheet">
<style>
* {
	margin: 0;
	padding: 0;
	box-sizing: border-box;
	font-family: 'Segoe UI', sans-serif;
}

body {
	background: #f0f2f5;
	min-height: 100vh;
}

.navbar {
	background: white;
	padding: 1rem 2rem;
	box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
	display: flex;
	justify-content: space-between;
	align-items: center;
	position: fixed;
	width: 100%;
	top: 0;
	z-index: 100;
}

.username {
	font-weight: 500;
	color: #1a73e8;
}

.logout-btn {
	padding: 0.5rem 1rem;
	background: #dc3545;
	color: white;
	border: none;
	border-radius: 4px;
	cursor: pointer;
	display: flex;
	align-items: center;
	gap: 8px;
}

.main-container {
	display: flex;
	padding-top: 64px;
	min-height: 100vh;
}

.sidebar {
	width: 300px;
	background: white;
	padding: 2rem;
	height: calc(100vh - 64px);
	position: fixed;
	box-shadow: 2px 0 4px rgba(0, 0, 0, 0.1);
	display: flex;
	flex-direction: column;
}

.workspace-list {
	flex: 1;
	overflow-y: auto;
}

.workspace-item {
	padding: 1rem;
	margin: 0.5rem 0;
	background: #f8f9fa;
	border-radius: 8px;
	cursor: pointer;
	transition: all 0.2s;
}

.workspace-item:hover {
	background: #e9ecef;
	transform: translateX(5px);
}

.workspace-item.active {
	background: #1a73e8;
	color: white;
}

.add-workspace-btn {
	padding: 1rem;
	background: #1a73e8;
	color: white;
	border: none;
	border-radius: 8px;
	cursor: pointer;
	display: flex;
	align-items: center;
	justify-content: center;
	gap: 8px;
	margin-top: 1rem;
}

.modal-overlay {
	position: fixed;
	top: 0;
	left: 0;
	right: 0;
	bottom: 0;
	background: rgba(0, 0, 0, 0.5);
	backdrop-filter: blur(5px);
	display: none;
	justify-content: center;
	align-items: center;
	z-index: 1000;
}

.modal {
	background: white;
	padding: 2rem;
	border-radius: 12px;
	width: 90%;
	max-width: 500px;
	box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.modal h2 {
	margin-bottom: 1.5rem;
	color: #1a73e8;
}

.form-group {
	margin-bottom: 1rem;
}

.form-group label {
	display: block;
	margin-bottom: 0.5rem;
	color: #495057;
}

.form-group input {
	width: 100%;
	padding: 0.75rem;
	border: 2px solid #dee2e6;
	border-radius: 6px;
	font-size: 1rem;
}

.modal-buttons {
	display: flex;
	gap: 1rem;
	margin-top: 1.5rem;
}

.modal-buttons button {
	flex: 1;
	padding: 0.75rem;
	border: none;
	border-radius: 6px;
	cursor: pointer;
	font-size: 1rem;
}

.save-btn {
	background: #1a73e8;
	color: white;
}

.cancel-btn {
	background: #dee2e6;
	color: #495057;
}

.content {
	margin-left: 300px;
	padding: 2rem;
	flex: 1;
}

.task-input-container {
	background: white;
	padding: 1.5rem;
	border-radius: 12px;
	box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
	margin-bottom: 2rem;
	display: flex;
	gap: 1rem;
}

.task-input {
	flex: 1;
	padding: 0.75rem;
	border: 2px solid #dee2e6;
	border-radius: 6px;
	font-size: 1rem;
}

.add-task-btn {
	padding: 0.75rem 1.5rem;
	background: #1a73e8;
	color: white;
	border: none;
	border-radius: 6px;
	cursor: pointer;
}

.task-list {
	background: white;
	padding: 1.5rem;
	border-radius: 12px;
	box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.task {
	padding: 1rem;
	background: #f8f9fa;
	border-radius: 8px;
	margin-bottom: 1rem;
	display: flex;
	justify-content: space-between;
	align-items: center;
	transition: all 0.2s;
}

.task:first-child {
	border-left: 4px solid #1a73e8;
}

.complete-btn {
	padding: 0.5rem 1rem;
	background: #28a745;
	color: white;
	border: none;
	border-radius: 4px;
	cursor: pointer;
	display: none;
}

.task:first-child .complete-btn {
	display: block;
}

.completed {
	background: #e8f6ef;
	border-left: 4px solid #28a745;
}
</style>
</head>
<body>
	<nav class="navbar">
		<div class="username">
			<i class="fas fa-user"></i>
			<%=((User) session.getAttribute("user")).getUsername()%>
		</div>
		<form action="Logout" method="GET">
			<button class="logout-btn" type="submit">
				<i class="fas fa-sign-out-alt"></i> Disconnect
			</button>
		</form>
	</nav>

	<div class="main-container">
		<div class="sidebar">
			<div class="workspace-list">
				<%
				User user = (User) session.getAttribute("user");
				List<TaskSpace> workspaces = (List<TaskSpace>) user.getTaskSpaces();
				Long activeWorkspaceId = (Long) session.getAttribute("activeWorkspaceId");
				if (workspaces != null) {
					for (TaskSpace workspace : workspaces) {
						String activeClass = (workspace.getId() == activeWorkspaceId) ? "active" : "";
				%>
				<div class="workspace-item <%=activeClass%>"
					onclick="loadWorkspace(<%=workspace.getId()%>)">
					<i class="fas fa-folder"></i>
					<%=workspace.getTitle()%>
				</div>
				<%
				}
				}
				%>
			</div>
			<button class="add-workspace-btn" onclick="showModal()">
				<i class="fas fa-plus"></i> New Workspace
			</button>
		</div>

		<div class="content">
			<%
			if (activeWorkspaceId != null) {
			%>
			<div class="task-input-container">
				<input type="text" class="task-input"
					placeholder="Enter a new task...">
				<button class="add-task-btn" onclick="addTask()">
					<i class="fas fa-plus"></i> Add Task
				</button>
			</div>

			<div class="task-list">
				<%
				List<Task> tasks = null;
				if (tasks != null) {
					for (Task task : tasks) {
				%>
				<div class="task <%=task.isCompleted() ? "completed" : ""%>"
					data-id="<%=task.getId()%>">
					<span><%=task.getContent()%></span>
					<button class="complete-btn"
						onclick="completeTask(<%=task.getId()%>)">
						<i class="fas fa-check"></i> Complete
					</button>
				</div>
				<%
				}
				}
				%>
			</div>
			<%
			} else {
			%>
			<div style="text-align: center; color: #6c757d; margin-top: 2rem;">
				Select or create a workspace to start adding tasks</div>
			<%
			}
			%>
		</div>
	</div>

	<div class="modal-overlay" id="workspaceModal">
		<div class="modal">
			<h2>Create New Workspace</h2>
			<div class="form-group">
				<label>Workspace Title</label> <input type="text"
					id="workspaceTitle" placeholder="Enter workspace title" required>
			</div>
			<div class="form-group">
				<label>Description</label> <input type="text" id="workspaceDesc"
					placeholder="Enter workspace description" required>
			</div>
			<div class="modal-buttons">
				<button class="cancel-btn" onclick="hideModal()">Cancel</button>
				<button class="save-btn" onclick="createWorkspace()">Create
					Workspace</button>
			</div>
		</div>
	</div>
	<script>
	function showModal() {
        document.getElementById("workspaceModal").style.display = "flex";
    }

    function hideModal() {
        document.getElementById("workspaceModal").style.display = "none";
    }

    function createWorkspace() {
        const title = document.getElementById("workspaceTitle").value;
        const description = document.getElementById("workspaceDesc").value;

        if (title.trim() === "") {
            alert("Workspace title is required.");
            return;
        }

        const data = {
            title: title,
            description: description
        };

        fetch('TaskSpace', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: new URLSearchParams(data).toString()
        })
        .then(response => response.json())
        .then(data => {
            if (data.error) {
                alert(data.error);
                return;
            }
            
            if (data.success && data.workspace) {
                // Add the new workspace to the list
                const workspaceList = document.querySelector('.workspace-list');
                const workspaceItem = document.createElement('div');
                workspaceItem.className = 'workspace-item';
                workspaceItem.onclick = () => loadWorkspace(data.workspace.id);
                workspaceItem.innerHTML = `
                    <i class="fas fa-folder"></i>
                    ${data.workspace.title}
                `;
                
                workspaceList.appendChild(workspaceItem);

                // Clear the form and hide the modal
                hideModal();
                document.getElementById("workspaceTitle").value = "";
                document.getElementById("workspaceDesc").value = "";
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Failed to create workspace. Please try again.');
        });
    }

</script>
</body>
</html>