<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="DTO.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Task Dashboard</title>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Sortable/1.15.0/Sortable.min.js"></script>
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
<style>
* {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
    font-family: 'Segoe UI', sans-serif;
}

body {
    background-color: #121212; /* Dark background */
    color: #e0e0e0;
    min-height: 100vh;
}

.navbar {
    background: #1e1e1e;
	box-shadow: 0 2px 4px rgba(0, 0, 0, 0.5);
    padding: 1rem 2rem;
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
    color: #90caf9;
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
    background: #1e1e1e;
    padding: 2rem;
    height: calc(100vh - 64px);
    position: fixed;
    box-shadow: 2px 0 4px rgba(0, 0, 0, 0.5);
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
    background: #333;
    border-radius: 8px;
    cursor: pointer;
    transition: all 0.2s;
    color: #e0e0e0;
}

.workspace-item:hover {
	background: #444;
    transform: translateX(5px);
}

.workspace-item.active {
    background: #90caf9; 
    color: #121212;
}

.add-workspace-btn {
    padding: 1rem;
    background: #90caf9;
    color: #121212;
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
    background: rgba(0, 0, 0, 0.7);
    backdrop-filter: blur(5px);
    display: none;
    justify-content: center;
    align-items: center;
    z-index: 1000;
}

.modal {
    background: #1e1e1e;
    padding: 2rem;
    border-radius: 12px;
    width: 90%;
    max-width: 500px;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.5);
}

.modal h2 {
    margin-bottom: 1.5rem;
    color: #90caf9;
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
    border: 2px solid #555;
    border-radius: 6px;
    font-size: 1rem;
    background: #333;
    color: #e0e0e0;
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
    background: #90caf9;
    color: #121212;
}

.cancel-btn {
    background: #555;
    color: #e0e0e0;
}

.content {
    margin-left: 300px;
    padding: 2rem;
    flex: 1;
}

.task-input-container {
    background: #1e1e1e;
    padding: 1.5rem;
    border-radius: 12px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.5);
    margin-bottom: 2rem;
    display: flex;
    gap: 1rem;
}

.task-input {
    flex: 1;
    padding: 0.75rem;
    border: 2px solid #555;
    border-radius: 6px;
    font-size: 1rem;
    background: #333;
    color: #e0e0e0;
}

.add-task-btn {
    padding: 0.75rem 1.5rem;
    background: #90caf9;
    color: #121212;
    border: none;
    border-radius: 6px;
}

.task-list {
    background: #1e1e1e;
    padding: 1.5rem;
    border-radius: 12px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.5);
}

.task {
    padding: 1rem;
    background: #333;
    border-radius: 8px;
    margin-bottom: 1rem;
    display: flex;
    justify-content: space-between;
    align-items: center;
    transition: all 0.2s;
}

.task:first-child {
    border-left: 4px solid #90caf9;
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
    background: #2e7d32;
    border-left: 4px solid #4caf50;
}

::-webkit-scrollbar {
    width: 0px;
    height: 0px;
}

::-webkit-scrollbar-thumb {
    background: transparent;
}

::-webkit-scrollbar-track {
    background: transparent;
}

.pointy{
	cursor: pointer;
}

/* For Firefox */
body {
    scrollbar-width: thin;
    scrollbar-color: transparent transparent;
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
                Long currTaskspaceId = (Long) session.getAttribute("currTaskspaceId");
                if (workspaces != null) {
                    for (TaskSpace workspace : workspaces) {
                        String activeClass = (workspace.getId() == currTaskspaceId) ? "active" : "";
                %>
                <div class="workspace-item <%=activeClass%>" onclick="loadWorkspace(<%=workspace.getId()%>)">
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
            if (currTaskspaceId != null) {
            %>
            <div class="task-input-container">
                <input type="text" class="task-input" placeholder="Enter a new task...">
                <button class="add-task-btn pointy" onclick="addTask()"> 
                    <i class="fas fa-plus"></i> Add Task
                </button>
            </div>

            <div class="task-list">
                <%
                List<Task> tasks = (List<Task>) user.getTaskSpaceById(currTaskspaceId).getTasks();
                if (tasks != null) {
                    for (Task task : tasks) {
                %>
                <div class="task <%=task.isCompleted() ? "completed" : ""%>" data-id="<%=task.getId()%>">
                    <span><%=task.getContent()%></span>
                    <button class="complete-btn" onclick="completeTask(<%=task.getId()%>)">
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
                Select or create a Taskspace to start adding tasks
            </div>
            <%
            }
            %>
        </div>
    </div>

    <div class="modal-overlay" id="workspaceModal">
        <div class="modal">
            <h2>Create New Workspace</h2>
            <div class="form-group">
                <label>Workspace Title</label> 
                <input type="text" id="workspaceTitle" placeholder="Enter workspace title" required>
            </div>
            <div class="form-group">
                <label>Description</label> 
                <input type="text" id="workspaceDesc" placeholder="Enter description" required>
            </div>
            <div class="modal-buttons">
                <button class="save-btn" onclick="saveWorkspace()">Save</button>
                <button class="cancel-btn" onclick="closeModal()">Cancel</button>
            </div>
        </div>
    </div>

    <script>
        function loadWorkspace(id) {
            window.location.href = 'Taskspace?taskspaceId=' + id;
        }

        function showModal() {
            document.getElementById('workspaceModal').style.display = 'flex';
        }

        function closeModal() {
            document.getElementById('workspaceModal').style.display = 'none';
        }

        function saveWorkspace() {
            const title = document.getElementById('workspaceTitle').value;
            const description = document.getElementById('workspaceDesc').value;

            const xhr = new XMLHttpRequest();
            xhr.open('POST', 'Taskspace', true);
            xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

            xhr.onload = function() {
                if (xhr.status === 200) {
                    const response = JSON.parse(xhr.responseText);
                    if (response.success) {
                        location.reload();
                    } else {
                        alert('Error: ' + response.error);
                    }
                } else {
                    alert('An error occurred while creating the workspace.');
                }
            };

            xhr.send('title=' + encodeURIComponent(title) + '&description=' + encodeURIComponent(description));

            closeModal();
        }

        function addTask() {
            const taskContent = document.querySelector('.task-input').value;

            if (taskContent) {
                const taskData = new URLSearchParams();
                taskData.append("content", taskContent);

                fetch('Task', {
                    method: 'POST',
                    body: taskData,
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    }
                })
                .then(response => response.json())
                .then(data => {
                    if (data.success) {
                        window.location.reload();
                    } else {
                        alert('Failed to add task: ' + data.error);
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert('An error occurred while adding the task.');
                });
            } else {
                alert('Please enter a task.');
            }
        }

        function completeTask(taskId) {
            fetch('Task', {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ taskId: taskId }) // Send the taskId in the body as JSON
            })
            .then(response => {
                if (response.ok) {
                    alert("Task completed successfully");
                    document.querySelector(`button[onclick="completeTask(${taskId})"]`).disabled = true;
                    location.reload();
                } else {
                    alert("Failed to complete task");
                }
            })
            .catch(error => {
                console.error('Error completing task:', error);
                location.reload();
            });
        }




    </script>
</body>
</html>
