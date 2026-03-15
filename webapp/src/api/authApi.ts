import axios from 'axios';
import { LoginRequestDto } from '../model/loginRequest.schema';
import { AuthResponse } from '../model/AuthResponse';
import { RegisterRequestDto } from '../model/registerRequest.schema';

const authApi = axios.create({
  baseURL: 'http://localhost:8080/api/auth',
  headers: {
    Accept: 'application/json',
  },
});

authApi.interceptors.request.use((config) => {
  const token = localStorage.getItem('jwt_token');
  if (token && config.headers) {
    config.headers.Authorization = `Bearer ${token}`;
  }

  return config;
});

export async function requestLogin(dto: LoginRequestDto): Promise<AuthResponse> {
  const result = await authApi.post<AuthResponse>('/login', dto);
  return result.data;
}

export async function requestRegister(dto: RegisterRequestDto) {
  await authApi.post('/register', dto);
}
