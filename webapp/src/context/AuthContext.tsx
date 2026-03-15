import { useEffect, useMemo, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { AuthContext } from '../hooks/useAuth';
import { AuthResponse } from '../model/AuthResponse';

const TOKEN_KEY = 'jwt_token';
const USERID_KEY = 'user_id';

export function AuthProvider({ children }: { children: React.ReactNode }) {
  const navigate = useNavigate();
  const [token, setToken] = useState<string | null>(null);
  const [userId, setUserId] = useState<number | null>(null);

  useEffect(() => {
    const storedToken = localStorage.getItem(TOKEN_KEY);
    if (storedToken) setToken(storedToken);

    const storedUserId = localStorage.getItem(USERID_KEY);
    const id = Number(storedUserId);
    if (storedUserId) setUserId(id);
  }, []);

  const login = (authData: AuthResponse) => {
    localStorage.setItem(TOKEN_KEY, authData.token);
    setToken(authData.token);

    localStorage.setItem(USERID_KEY, String(authData.userId));
    setUserId(authData.userId);
  };

  const logout = () => {
    localStorage.removeItem(TOKEN_KEY);
    localStorage.removeItem(USERID_KEY);
    setToken(null);
    setUserId(null);
    navigate('/login');
  };

  const value = useMemo(
    () => ({
      token,
      userId,
      isAuthenticated: !!token,
      login,
      logout,
    }),
    [token],
  );

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}
