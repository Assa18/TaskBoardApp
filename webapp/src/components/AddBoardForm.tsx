import { useMutation, useQueryClient } from '@tanstack/react-query';
import { useForm } from 'react-hook-form';
import { useNavigate } from 'react-router-dom';
import { Box, Button, TextField } from '@mui/material';
import { zodResolver } from '@hookform/resolvers/zod';
import { SaveBoardDto, saveBoardSchema } from '../model/saveBoard.schema';
import { addBoard } from '../api/userApi';
import { useAuth } from '../hooks/useAuth';

export default function AddBoardForm() {
  const navigate = useNavigate();
  const queryClient = useQueryClient();
  const { userId } = useAuth();

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<SaveBoardDto>({
    resolver: zodResolver(saveBoardSchema),
  });

  const mutation = useMutation({
    mutationFn: (dto: SaveBoardDto) => addBoard(userId, dto),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['boards'] });
    },
    onError: () => {
      navigate('error', { state: { message: 'Failed to add new todo!' } });
    },
  });

  function onSubmit(data: SaveBoardDto) {
    mutation.mutate(data);
  }

  return (
    <Box
      component="form"
      onSubmit={handleSubmit(onSubmit)}
      sx={{
        display: 'flex',
        gap: 2,
        alignItems: 'center',
        padding: 2,
        borderBottom: '1px solid',
        borderColor: 'divider',
      }}
    >
      <TextField label="Title" {...register('title')} error={!!errors.title} helperText={errors.title?.message} />

      <Button type="submit" variant="contained" size="small">
        Add board
      </Button>
    </Box>
  );
}
