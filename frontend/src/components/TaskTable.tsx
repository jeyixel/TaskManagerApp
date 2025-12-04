import { useState } from 'react';
import type { Task } from '../types/Task';
import './TaskTable.css';

interface TaskTableProps {
  tasks: Task[];
  onUpdateTask: (task: Task) => void;
  onDeleteTask: (id: number) => void;
}

export function TaskTable({ tasks, onUpdateTask, onDeleteTask }: TaskTableProps) {
  const [editingId, setEditingId] = useState<number | null>(null);
  const [editTitle, setEditTitle] = useState('');
  const [editDescription, setEditDescription] = useState('');

  const startEditing = (task: Task) => {
    setEditingId(task.id!);
    setEditTitle(task.title);
    setEditDescription(task.description);
  };

  const cancelEditing = () => {
    setEditingId(null);
    setEditTitle('');
    setEditDescription('');
  };

  const saveEdit = (task: Task) => {
    onUpdateTask({
      ...task,
      title: editTitle,
      description: editDescription,
    });
    cancelEditing();
  };

  const toggleCompleted = (task: Task) => {
    onUpdateTask({
      ...task,
      completed: !task.completed,
    });
  };

  if (tasks.length === 0) {
    return (
      <div className="no-tasks">
        <p>No tasks yet. Add one above!</p>
      </div>
    );
  }

  return (
    <table className="task-table">
      <thead>
        <tr>
          <th>Done</th>
          <th>Title</th>
          <th>Description</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        {tasks.map((task) => (
          <tr key={task.id} className={task.completed ? 'completed' : ''}>
            <td>
              <input
                type="checkbox"
                checked={task.completed}
                onChange={() => toggleCompleted(task)}
                className="checkbox"
              />
            </td>
            <td>
              {editingId === task.id ? (
                <input
                  type="text"
                  value={editTitle}
                  onChange={(e) => setEditTitle(e.target.value)}
                  className="edit-input"
                />
              ) : (
                <span className={task.completed ? 'strikethrough' : ''}>
                  {task.title}
                </span>
              )}
            </td>
            <td>
              {editingId === task.id ? (
                <input
                  type="text"
                  value={editDescription}
                  onChange={(e) => setEditDescription(e.target.value)}
                  className="edit-input"
                />
              ) : (
                <span className={task.completed ? 'strikethrough' : ''}>
                  {task.description}
                </span>
              )}
            </td>
            <td>
              {editingId === task.id ? (
                <div className="action-buttons">
                  <button className="save-btn" onClick={() => saveEdit(task)}>
                    Save
                  </button>
                  <button className="cancel-btn" onClick={cancelEditing}>
                    Cancel
                  </button>
                </div>
              ) : (
                <div className="action-buttons">
                  <button className="edit-btn" onClick={() => startEditing(task)}>
                    Edit
                  </button>
                  <button
                    className="delete-btn"
                    onClick={() => onDeleteTask(task.id!)}
                  >
                    Delete
                  </button>
                </div>
              )}
            </td>
          </tr>
        ))}
      </tbody>
    </table>
  );
}
