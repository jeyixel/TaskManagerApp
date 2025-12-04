import { useState, useEffect } from 'react';
import type { Task } from './types/Task';
import { taskApi } from './api/taskApi';
import { TaskForm } from './components/TaskForm';
import { TaskTable } from './components/TaskTable';
import './App.css';

function App() {
  const [tasks, setTasks] = useState<Task[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  // Fetch all tasks on component mount
  useEffect(() => {
    fetchTasks();
  }, []);

  const fetchTasks = async () => {
    try {
      setLoading(true);
      const data = await taskApi.getAllTasks();
      setTasks(data);
      setError(null);
    } catch (err) {
      setError('Failed to fetch tasks. Make sure the backend is running.');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const handleAddTask = async (task: Omit<Task, 'id'>) => {
    try {
      const newTask = await taskApi.createTask(task);
      setTasks([...tasks, newTask]);
      setError(null);
    } catch (err) {
      setError('Failed to create task.');
      console.error(err);
    }
  };

  const handleUpdateTask = async (task: Task) => {
    try {
      const updatedTask = await taskApi.updateTask(task.id!, task);
      setTasks(tasks.map((t) => (t.id === task.id ? updatedTask : t)));
      setError(null);
    } catch (err) {
      setError('Failed to update task.');
      console.error(err);
    }
  };

  const handleDeleteTask = async (id: number) => {
    try {
      await taskApi.deleteTask(id);
      setTasks(tasks.filter((t) => t.id !== id));
      setError(null);
    } catch (err) {
      setError('Failed to delete task.');
      console.error(err);
    }
  };

  return (
    <div className="app-container">
      <h1>Task Manager</h1>
      
      {error && <div className="error-message">{error}</div>}
      
      <TaskForm onAddTask={handleAddTask} />
      
      {loading ? (
        <div className="loading">Loading tasks...</div>
      ) : (
        <TaskTable
          tasks={tasks}
          onUpdateTask={handleUpdateTask}
          onDeleteTask={handleDeleteTask}
        />
      )}
    </div>
  );
}

export default App;
