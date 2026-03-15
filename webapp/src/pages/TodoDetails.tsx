import { useParams, useNavigate, Link } from 'react-router-dom';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { Box, Card, CardContent, Typography, Button, Stack } from '@mui/material';
import { fetchTodoById, deleteTodo } from '../api/todoApi';
import { DetailedTodoData } from '../model/FetchTodoDetailedDto';
import Error from './Error';
import Loading from './Loading';

export default function TodoDetails() {
  const { id } = useParams();
  const navigate = useNavigate();
  const queryClient = useQueryClient();

  const todoId = Number(id);

  const { data, isLoading, isError } = useQuery<DetailedTodoData>({
    queryKey: ['todo', todoId],
    queryFn: () => fetchTodoById(todoId),
  });

  const deleteMutation = useMutation({
    mutationFn: () => deleteTodo(todoId),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['todos'] });
      queryClient.invalidateQueries({ queryKey: ['todo', todoId] });
      navigate('/');
    },
    onError: () => {
      navigate('error', { state: { message: 'Failed to delete todo!' } });
    },
  });

  if (isLoading) {
    return <Loading />;
  }

  if (isError || !data) {
    return <Error message="Failed to get todo details!" />;
  }

  return (
    <Box p={2}>
      <Button variant="outlined" onClick={() => navigate(-1)} sx={{ mb: 2 }}>
        Back
      </Button>
      <Card>
        <CardContent>
          <Typography variant="h5" gutterBottom>
            {data.title}
          </Typography>

          <Typography variant="body1" sx={{ mb: 2 }}>
            {data.description}
          </Typography>

          <Stack spacing={1}>
            <Typography>Deadline: {data.deadline}</Typography>
            <Typography>Severity: {data.severity}</Typography>
          </Stack>

          <Stack direction="row" spacing={2} mt={3}>
            <Button variant="outlined" component={Link} to={`/${todoId}/edit`}>
              Edit
            </Button>
            <Button variant="outlined" color="error" onClick={() => deleteMutation.mutate()}>
              Delete
            </Button>
          </Stack>
        </CardContent>
      </Card>
    </Box>
  );
}
