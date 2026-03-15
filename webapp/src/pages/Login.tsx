import { useForm } from 'react-hook-form';
import { useMutation } from '@tanstack/react-query';
import { Box, Button, Link, Stack, TextField, Typography } from '@mui/material';
import { Link as RouterLink, useNavigate } from 'react-router-dom';
import { zodResolver } from '@hookform/resolvers/zod';
import { LoginRequestDto, loginRequestSchema } from '../model/loginRequest.schema';
import { useAuth } from '../hooks/useAuth';
import { requestLogin } from '../api/authApi';
import { useThemeContext } from '../hooks/useThemeContext';
import { AppTheme } from '../theme/themes';

export default function Login() {
  const { login } = useAuth();
  const { setTheme } = useThemeContext();
  const navigate = useNavigate();

  const {
    register,
    handleSubmit,
    formState: { errors, isSubmitting },
  } = useForm<LoginRequestDto>({
    resolver: zodResolver(loginRequestSchema),
  });

  const loginMutation = useMutation({
    mutationFn: (dto: LoginRequestDto) => requestLogin(dto),
    onSuccess: (data) => {
      login(data);
      setTheme(data.theme as AppTheme);
    },
    onError: () => {
      navigate('error', { state: { message: 'Wrong credentials! Try again' } });
    },
  });

  return (
    <Box maxWidth={400} mx="auto" mt={8}>
      <Typography variant="h4" mb={3}>
        Login
      </Typography>

      <form onSubmit={handleSubmit((data) => loginMutation.mutate(data))} noValidate>
        <Stack spacing={3}>
          <TextField
            label="Email"
            type="email"
            {...register('email')}
            error={!!errors.email}
            helperText={errors.email?.message}
            fullWidth
          />

          <TextField
            label="Password"
            type="password"
            {...register('password')}
            error={!!errors.password}
            helperText={errors.password?.message}
            fullWidth
          />

          <Button type="submit" variant="contained" disabled={isSubmitting || loginMutation.isPending}>
            Login
          </Button>

          <Typography variant="body2" align="center">
            Do not have an account?{' '}
            <Link component={RouterLink} to="/register">
              Register here
            </Link>
          </Typography>
        </Stack>
      </form>
    </Box>
  );
}
