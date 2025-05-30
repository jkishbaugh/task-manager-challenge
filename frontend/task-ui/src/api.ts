import axios, { type AxiosResponse } from "axios";
import type { Task } from "./types";

const API_BASE_URL = 'http://localhost:8080';

axios.defaults.baseURL = API_BASE_URL;

export const api = {
  getTasks: async (): Promise<Task[]> => {
    const response: AxiosResponse<Task[]> = await axios.get('/tasks');
    return response.data;
  },

  createTask: async (title: string): Promise<Task> => {
    const response: AxiosResponse<Task> = await axios.post('/tasks', {
      title,
      status: 'TODO',
    });
    return response.data;
  },

  updateTask: async (id: string, task: Task): Promise<Task> => {
    console.log("Updating task:", id, task);
    const response: AxiosResponse<Task> = await axios.put(`/tasks/${id}`, task);
    console.log("Updated task:", response.data);
    return response.data;
  },
  deleteTask: async (id: string): Promise<void> => {
    await axios.delete(`/tasks/${id}`);
  },
};