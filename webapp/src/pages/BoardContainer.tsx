import { useQuery } from '@tanstack/react-query';
import { Box, Button } from '@mui/material';
import Loading from './Loading';
import Error from './Error';
import Board from '../components/Board';
import AddBoardForm from '../components/AddBoardForm';
import { useAuth } from '../hooks/useAuth';
import { fetchBoardsOfUser } from '../api/userApi';
import ThemeSwitcher from '../components/ThemeSwitcher';

export default function BoardContainer() {
  const { userId } = useAuth();

  const { data, isLoading, isError } = useQuery({
    queryKey: ['boards'],
    queryFn: () => fetchBoardsOfUser(userId),
  });
  const { logout } = useAuth();

  if (isLoading) {
    return <Loading />;
  }

  if (isError || !data) {
    return <Error message="Failed to fetch boards!" />;
  }

  return (
    <Box
      sx={{
        display: 'flex',
        flexDirection: 'column',
        height: '100%',
      }}
    >
      <Box
        sx={{
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'space-between',
          borderBottom: '1px solid',
          borderColor: 'divider',
          px: 2,
          py: 1.5,
        }}
      >
        <AddBoardForm />

        <ThemeSwitcher />

        <Button variant="outlined" color="error" onClick={logout}>
          Logout
        </Button>
      </Box>

      <Box
        sx={{
          display: 'flex',
          gap: 2,
          alignItems: 'flex-start',
          overflowX: 'auto',
          overflowY: 'hidden',
          padding: 2,
          flexGrow: 1,
          whiteSpace: 'nowrap',
        }}
      >
        {data.map((board) => (
          <Board key={board.id} boardId={board.id} title={board.title} />
        ))}
      </Box>
    </Box>
  );
}
