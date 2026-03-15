import {
  Box,
  Button,
  Card,
  CardHeader,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  TextField,
} from '@mui/material';
import { Link, useNavigate } from 'react-router-dom';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import { useState } from 'react';
import TodoList from './TodoList';
import { deleteBoard, renameBoard } from '../api/boardApi';

type BoardProps = {
  boardId: number;
  title: string;
};

export default function Board({ boardId, title }: BoardProps) {
  const queryClient = useQueryClient();
  const navigate = useNavigate();

  const [editOpen, setEditOpen] = useState(false);
  const [editedTitle, setEditedTitle] = useState(title);

  const deleteMutation = useMutation({
    mutationFn: () => deleteBoard(boardId),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['boards'] });
    },
    onError: () => {
      navigate('error', { state: { message: 'Failed to delete todo!' } });
    },
  });

  const updateMutation = useMutation({
    mutationFn: (newTitle: string) => renameBoard(boardId, { title: newTitle }),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['boards'] });
    },
  });

  function handleEditOk() {
    updateMutation.mutate(editedTitle);
    setEditOpen(false);
  }

  return (
    <>
      <Card
        elevation={2}
        sx={{
          width: 380,
          minWidth: 380,
          flexShrink: 0,
          borderRadius: 2,
          display: 'flex',
          flexDirection: 'column',
          maxHeight: '100%',
        }}
      >
        <CardHeader
          title={title}
          sx={{
            pb: 1,
            '& .MuiCardHeader-title': {
              fontSize: '1rem',
              fontWeight: 600,
            },
          }}
        />

        <Box
          sx={{
            display: 'flex',
            gap: 1,
            px: 2,
            pb: 1,
          }}
        >
          <Button component={Link} to={`/${boardId}/add`} size="small" variant="contained" fullWidth>
            Add new todo
          </Button>

          <Button
            size="small"
            variant="outlined"
            fullWidth
            onClick={() => {
              setEditedTitle(title);
              setEditOpen(true);
            }}
          >
            Edit
          </Button>

          <Button size="small" variant="outlined" color="error" fullWidth onClick={() => deleteMutation.mutate()}>
            Delete board
          </Button>
        </Box>

        <Box
          sx={{
            px: 2,
            pb: 2,
            overflowY: 'auto',
            flexGrow: 1,
          }}
        >
          <TodoList boardId={boardId} />
        </Box>
      </Card>

      <Dialog open={editOpen} onClose={() => setEditOpen(false)} maxWidth="sm" fullWidth>
        <DialogTitle>Edit board title</DialogTitle>

        <DialogContent>
          <TextField
            autoFocus
            margin="dense"
            label="Title"
            fullWidth
            value={editedTitle}
            onChange={(e) => setEditedTitle(e.target.value)}
          />
        </DialogContent>

        <DialogActions>
          <Button onClick={() => setEditOpen(false)}>Cancel</Button>
          <Button variant="contained" onClick={() => handleEditOk()}>
            OK
          </Button>
        </DialogActions>
      </Dialog>
    </>
  );
}
