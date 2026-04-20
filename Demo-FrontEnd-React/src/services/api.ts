import axios from "axios";

const getUsersAPI = () => {
  const url = `${import.meta.env.VITE_BACKEND_URL}/users`;
  return axios.get(url);
};

const createAUserAPI = (name: string, email: string) => {
  const url = `${import.meta.env.VITE_BACKEND_URL}/users`;
  return axios.post(url, { name, email });
};

const updateUserAPI = (id: number, name: string, email: string) => {
  const url = `${import.meta.env.VITE_BACKEND_URL}/users/${id}`;
  return axios.put(url, { id, name, email });
};

const deleteUserAPI = (id: number) => {
  const url = `${import.meta.env.VITE_BACKEND_URL}/users/${id}`;
  return axios.delete(url);
};
export { getUsersAPI, createAUserAPI, updateUserAPI, deleteUserAPI };
