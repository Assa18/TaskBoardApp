import { useForm } from 'react-hook-form';
import { useMutation } from '@tanstack/react-query';
import { Box, Button, Stack, TextField, Typography } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import { zodResolver } from '@hookform/resolvers/zod';
import { RegisterRequestDto, registerRequestSchema } from '../model/registerRequest.schema';
import { requestRegister } from '../api/authApi';

export default function Register() {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<RegisterRequestDto>({
    resolver: zodResolver(registerRequestSchema),
  });
  const navigate = useNavigate();

  const registerMutation = useMutation({
    mutationFn: (dto: RegisterRequestDto) => requestRegister(dto),
    onSuccess: () => navigate('/login'),
  });

  function onSubmit(data: RegisterRequestDto) {
    registerMutation.mutate(data);
  }

  return (
    <Box maxWidth={400} mx="auto" mt={8}>
      <Typography variant="h4" mb={3}>
        Register
      </Typography>

      <form onSubmit={handleSubmit(onSubmit)}>
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
          <Button type="submit" variant="contained">
            Register
          </Button>
        </Stack>
      </form>
    </Box>
  );
}
