import { createContext, useContext } from 'react';
import { AuthResponse } from '../model/AuthResponse';

type AuthContextType = {
  token: string | null;
  userId: number | null;
  isAuthenticated: boolean;
  login: (authData: AuthResponse) => void;
  logout: () => void;
};

export const AuthContext = createContext<AuthContextType | undefined>(undefined);

export function useAuth() {
  const ctx = useContext(AuthContext);
  if (!ctx) throw new Error('useAuth must be used within AuthProvider');
  return ctx;
}
