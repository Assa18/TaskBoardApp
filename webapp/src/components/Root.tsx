import { BrowserRouter, Route, Routes } from 'react-router-dom';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import AddTodo from '../pages/AddTodo';
import TodoDetails from '../pages/TodoDetails';
import Error from '../pages/Error';
import BoardContainer from '../pages/BoardContainer';
import EditTodo from '../pages/EditTodo';
import { AuthProvider } from '../context/AuthContext';
import PublicRoute from './PublicRoute';
import ProtectedRoute from './ProtoctedRoute';
import Login from '../pages/Login';
import Register from '../pages/Register';
import { ThemeProvider } from '../context/ThemeContext';
import AppLayout from '../theme/AppLayout';

const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      staleTime: 10000,
      refetchInterval: 30000,
    },
  },
});

export default function Root() {
  return (
    <QueryClientProvider client={queryClient}>
      <BrowserRouter>
        <AuthProvider>
          <ThemeProvider>
            <Routes>
              <Route element={<PublicRoute />}>
                <Route path="/login" element={<Login />} />
                <Route path="/register" element={<Register />} />
              </Route>

              <Route element={<ProtectedRoute />}>
                <Route element={<AppLayout />}>
                  <Route path="/" element={<BoardContainer />} />
                  <Route path="/:id/add" element={<AddTodo />} />
                  <Route path="/:id/edit" element={<EditTodo />} />
                  <Route path="/todos/:id" element={<TodoDetails />} />
                </Route>
              </Route>

              <Route path="*" element={<Error message="Could not find page!" />} />
            </Routes>
          </ThemeProvider>
        </AuthProvider>
      </BrowserRouter>
    </QueryClientProvider>
  );
}
