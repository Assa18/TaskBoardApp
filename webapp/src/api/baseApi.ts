import axios, { AxiosInstance } from 'axios';

export const API_BASE_URL = 'http://localhost:8080/api';
const TOKEN_KEY = 'jwt_token';

export function createBaseApi(): AxiosInstance {
  const instance = axios.create({
    baseURL: API_BASE_URL,
    withCredentials: true,
    headers: {
      Accept: 'application/json',
    },
  });

  instance.interceptors.request.use((config) => {
    const token = localStorage.getItem(TOKEN_KEY);

    if (token && config.headers) {
      config.headers.Authorization = `Bearer ${token}`;
    }

    return config;
  });

  instance.interceptors.response.use(
    (response) => response,
    (error) => {
      if (error.response?.status === 401 || error.response?.status === 403) {
        localStorage.removeItem(TOKEN_KEY);
      }
      return Promise.reject(error);
    },
  );

  return instance;
}
