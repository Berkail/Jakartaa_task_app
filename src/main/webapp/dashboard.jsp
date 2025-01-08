<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="DTO.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Workspace Tasks</title>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Sortable/1.15.0/Sortable.min.js"></script>
    <style>
        body {
            margin: 0;
            padding: 20px;
            font-family: Arial, sans-serif;
            display: flex;
            min-height: 100vh;
        }

        .workspace-sidebar {
            width: 250px;
            background: #f5f5f5;
            padding: 20px;
            border-radius: 8px;
            margin-right: 20px;
        }

        .workspace-item {
            padding: 10px;
            margin: 5px 0;
            background: white;
            border-radius: 4px;
            cursor: pointer;
            transition: background-color 0.2s;
        }

        .workspace-item:hover {
            background: #e9ecef;
        }

        .workspace-item.active {
            background: #007bff;
            color: white;
        }

        .task-container {
            flex: 1;
            background: #f8f9fa;
            padding: 20px;
            border-radius: 8px;
        }

        .input-container {
            display: flex;
            gap: 10px;
            margin-bottom: 20px;
        }

        .task-input {
            flex: 1;
            padding: 12px;
            border: 2px solid #ddd;
            border-radius: 6px;
            font-size: 16px;
        }

        .add-button {
            background: #28a745;
            color: white;
            border: none;
            padding: 0 20px;
            border-radius: 6px;
            cursor: pointer;
            font-size: 16px;
        }

        .task-list {
            background: white;
            border-radius: 8px;
            padding: 20px;
            min-height: 100px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.05);
        }

        .task {
            background: #f8f9fa;
            padding: 15px;
            margin-bottom: 10px;
            border-radius: 6px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            display: flex;
            justify-content: space-between;
            align-items: center;
            cursor: move;
        }

        .complete-btn {
            background: #007bff;
            color: white;
            border: none;
            padding: 8px 15px;
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

        .workspace-title {
            font-size: 24px;
            margin-bottom: 20px;
        }

        .no-workspace-selected {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100%;
            color: #6c757d;
            font-size: 18px;
        }
    </style>
</head>
<body>
    <div class="workspace-sidebar">
        <h2>Workspaces</h2>
        <%
        User user = (User)request.getAttribute("user");
        List<TaskSpace> workspaces = user.getTaskSpaces();
        Integer activeWorkspaceId = (Integer) request.getAttribute("activeWorkspaceId");
        
        if (workspaces != null) {
            for (TaskSpace workspace : workspaces) {
                String activeClass = (workspace.getId() == activeWorkspaceId) ? "active" : "";
        %>
            <div class="workspace-item <%= activeClass %>" 
                 onclick="loadWorkspace(<%= workspace.getId() %>)">
                <%= workspace.getTitle() %>
            </div>
        <%
            }
        }
        %>
    </div>

    <div class="task-container">
        <%
        if (activeWorkspaceId != null) {
            TaskSpace activeWorkspace = (TaskSpace) request.getAttribute("activeWorkspace");
        %>
            <h1 class="workspace-title"><%= activeWorkspace.getTitle() %></h1>
            <div class="input-container">
                <input type="text" class="task-input" placeholder="Enter a new task...">
                <button class="add-button" onclick="addTask()">Add Task</button>
            </div>

            <div class="task-list">
                <%
                List<Task> tasks = (List<Task>) request.getAttribute("tasks");
                if (tasks != null) {
                    for (Task task : tasks) {
                %>
                    <div class="task <%= task.isCompleted() ? "completed" : "" %>" 
                         data-id="<%= task.getId() %>">
                        <span><%= task.getContent() %></span>
                        <button class="complete-btn" 
                                onclick="completeTask(<%= task.getId() %>)">Complete</button>
                    </div>
                <%
                    }
                }
                %>
            </div>
        <%
        } else {
        %>
            <div class="no-workspace-selected">
                Select a workspace to view tasks
            </div>
        <%
        }
        %>
    </div>

    <script>
        const taskList = document.querySelector('.task-list');
        if (taskList) {
            new Sortable(taskList, {
                animation: 150,
                onSort: function() {
                    updateCompleteButton();
                    updateTaskOrder();
                }
            });
        }

        function updateCompleteButton() {
            document.querySelectorAll('.complete-btn').forEach(btn => btn.style.display = 'none');
            const firstTask = document.querySelector('.task:first-child .complete-btn');
            if (firstTask) firstTask.style.display = 'block';
        }

        function loadWorkspace(workspaceId) {
            window.location.href = 'workspace?id=' + workspaceId;
        }

        function addTask() {
            const input = document.querySelector('.task-input');
            const task = input.value.trim();
            if (task) {
                fetch('addTask', {
                    method: 'POST',
                    headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                    body: `task=${encodeURIComponent(task)}&workspaceId=<%= activeWorkspaceId %>`
                }).then(() => window.location.reload());
                input.value = '';
            }
        }

        function completeTask(taskId) {
            fetch(`completeTask?taskId=${taskId}`, {
                method: 'POST'
            }).then(() => window.location.reload());
        }

        function updateTaskOrder() {
            const taskOrder = Array.from(document.querySelectorAll('.task'))
                .map(task => task.dataset.id);
            
            fetch('updateTaskOrder', {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({
                    workspaceId: <%= activeWorkspaceId %>,
                    taskOrder: taskOrder
                })
            });
        }
    </script>
</body>
</html>