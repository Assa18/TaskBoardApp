import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { Box } from '@mui/material';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { fetchBoardTodos } from '../api/boardApi';
import Error from '../pages/Error';
import Loading from '../pages/Loading';
import TodoItem from './TodoItem';
import { MoveTodoDto } from '../model/MoveTodoDto';
import { moveTodo } from '../api/todoApi';

type TodoListProps = {
  boardId: number;
};

type MoveTodoMutationInput = {
  todoId: number;
  dto: MoveTodoDto;
};

export default function TodoList({ boardId }: TodoListProps) {
  const queryClient = useQueryClient();
  const navigate = useNavigate();

  const { data, isLoading, isError } = useQuery({
    queryKey: ['todos', boardId],
    queryFn: () => fetchBoardTodos(boardId),
  });

  const moveTodoMutation = useMutation({
    mutationFn: ({ todoId, dto }: MoveTodoMutationInput) => moveTodo(todoId, dto),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['todos'] });
    },
    onError: () => {
      navigate('error', { state: { message: 'Failed to move todo!' } });
    },
  });

  const [isOver, setIsOver] = useState(false);

  if (isLoading) {
    return <Loading />;
  }

  if (isError || !data) {
    return <Error message="Failed to fetch todos!" />;
  }

  const handleDrop = (e: React.DragEvent) => {
    e.preventDefault();
    setIsOver(false);

    const payload = JSON.parse(e.dataTransfer.getData('application/json'));
    const { todoId, sourceBoardId } = payload;

    if (sourceBoardId === boardId) return;

    moveTodoMutation.mutate({
      todoId,
      dto: { newBoardId: boardId },
    });
  };

  return (
    <Box
      onDragOver={(e) => {
        e.preventDefault();
        setIsOver(true);
      }}
      onDragLeave={() => setIsOver(false)}
      onDrop={handleDrop}
      sx={{
        display: 'flex',
        flexDirection: 'column',
        gap: 1,
        overflowY: 'auto',
        flexGrow: 1,
        pr: 0.5,
        padding: 2,
        borderRadius: 1,
        transition: 'background-color 0.15s ease',
        backgroundColor: isOver ? 'action.hover' : 'transparent',
      }}
    >
      {data.map((todo) => (
        <TodoItem key={todo.id} todoId={todo.id} title={todo.title} boardId={boardId} />
      ))}
    </Box>
  );
}
