import { useMutation, useQueryClient } from '@tanstack/react-query';
import { Box, Button } from '@mui/material';
import { useNavigate, useParams } from 'react-router-dom';
import { addTodo } from '../api/boardApi';
import { SaveTodoDto } from '../model/saveTodo.schema';
import TodoForm from '../components/TodoForm';

export default function AddTodo() {
  const navigate = useNavigate();
  const queryClient = useQueryClient();
  const { id } = useParams();
  const boardId = Number(id);

  const mutation = useMutation({
    mutationFn: (dto: SaveTodoDto) => addTodo(boardId, dto),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['todos', boardId] });
      navigate('/');
    },
    onError: () => {
      navigate('error', { state: { message: 'Failed to add new todo!' } });
    },
  });

  return (
    <Box maxWidth="500px" mx="auto" mt={4}>
      <TodoForm submitLabel="Save Todo" onSubmit={(data) => mutation.mutate(data)} />

      <Button variant="outlined" onClick={() => navigate(-1)} sx={{ mt: 2 }}>
        Back
      </Button>
    </Box>
  );
}
