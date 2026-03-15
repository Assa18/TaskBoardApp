import { Card, Box, Typography } from '@mui/material';
import { Link } from 'react-router-dom';

type TodoItemProps = {
  todoId: number;
  title: string;
  boardId: number;
};

export default function TodoItem({ todoId, title, boardId }: TodoItemProps) {
  const handleDragStart = (e: React.DragEvent) => {
    e.dataTransfer.setData('application/json', JSON.stringify({ todoId, sourceBoardId: boardId }));
    e.dataTransfer.effectAllowed = 'move';
  };

  return (
    <Card
      component={Link}
      to={`/todos/${todoId}`}
      draggable
      onDragStart={handleDragStart}
      elevation={1}
      sx={{
        textDecoration: 'none',
        color: 'inherit',
        borderRadius: 1.5,
        cursor: 'pointer',
        '&:hover': {
          boxShadow: 3,
        },
        '&:active': {
          cursor: 'grabbing',
        },
      }}
    >
      <Box
        sx={{
          display: 'flex',
          alignItems: 'center',
          px: 1.5,
          py: 1,
          gap: 1,
        }}
      >
        <Typography variant="body2">{title}</Typography>
      </Box>
    </Card>
  );
}
