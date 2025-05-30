import { useEffect, useState } from "react";
import "./App.css";
import type {Task, TaskStatus } from "./types";
import { api } from "./api";

function App() {
  const [tasks, setTasks] = useState<Task[]>([]);
  const [newTitle, setNewTitle] = useState("");
  const [error, setError] = useState<string | null>(null);

  const loadTasks = async () => {
    try {
      const response = await api.getTasks();
      setTasks(response);
    } catch (err) {
      setError("Failed to load tasks");
    }
  };

  const handleAddTask = async () => {
    if (!newTitle.trim()) return;
    try {
      const newTask = await api.createTask(newTitle.trim());
      setNewTitle("");
      await loadTasks();
    } catch (err) {
      setError("Failed to add task");
    }
  };

  const markAsDone = async (task: Task) => {
     console.log("Marking as done:", task);
    const updatedTask = {
    ...task,
    taskStatus: 'DONE' as TaskStatus,
  };
    try {
      await api.updateTask(task.id, updatedTask);
      await loadTasks();
    } catch (err) {
      console.error("Update failed", err);
      setError("Failed to update task status");
    }
  };

  const deleteTask = async (id: string) => {
    if (!window.confirm('Are you sure you want to delete this task?')) return;
    try {
      await api.deleteTask(id);
      await loadTasks();
    } catch (e) {
      setError("Failed to delete task.");
    }
  };
  useEffect(() => {
    loadTasks();
  }, []);

  return (
    <div style={{ maxWidth: 600, margin: "2rem auto" }}>
      <h1>Task Manager</h1>

      <div style={{ marginBottom: "1rem" }}>
        <input
          type="text"
          placeholder="New task title"
          value={newTitle}
          onChange={(e) => setNewTitle(e.target.value)}
        />
        <button onClick={handleAddTask}>Add</button>
      </div>

      {error && <p style={{ color: "red" }}>{error}</p>}

      <ul>
        {tasks.map((task) => (
          <li key={task.id}>
            <strong>{task.title}</strong> — {task.taskStatus}
            {task.taskStatus !== "DONE" && (
              <button
                onClick={() => markAsDone(task)}
                style={{ marginLeft: 8 }}
              >
                Mark as Done
              </button>
            )}
            <button
              onClick={() => deleteTask(task.id)}
              style={{ marginLeft: 8, color: "red" }}
            >
              Delete
            </button>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default App;
