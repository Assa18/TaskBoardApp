import { useParams, useNavigate } from 'react-router-dom';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { Box, Typography, Button } from '@mui/material';
import TodoForm from '../components/TodoForm';
import { fetchTodoById, updateTodo } from '../api/todoApi';
import { SaveTodoDto, saveTodoSchema } from '../model/saveTodo.schema';
import Loading from './Loading';
import Error from './Error';

export default function EditTodo() {
  const { id } = useParams();
  const todoId = Number(id);
  const navigate = useNavigate();
  const queryClient = useQueryClient();

  const { data, isLoading, isError } = useQuery({
    queryKey: ['todo', todoId],
    queryFn: () => fetchTodoById(todoId),
  });

  const mutation = useMutation({
    mutationFn: (dto: SaveTodoDto) => updateTodo(todoId, dto),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['todo', todoId] });
      queryClient.invalidateQueries({ queryKey: ['todos'] });
      navigate(-1);
    },
  });

  if (isLoading) {
    return <Loading />;
  }

  if (isError || !data) {
    return <Error message="Failed to get todo details!" />;
  }

  const parsedTodo = saveTodoSchema.parse(data);

  return (
    <Box maxWidth="500px" mx="auto" mt={4}>
      <Typography variant="h4" mb={3}>
        Edit Todo
      </Typography>

      <TodoForm
        submitLabel="Update Todo"
        defaultValues={parsedTodo}
        onSubmit={(formData) => mutation.mutate(formData)}
      />

      <Button variant="outlined" onClick={() => navigate(-1)} sx={{ mt: 2 }}>
        Back
      </Button>
    </Box>
  );
}
