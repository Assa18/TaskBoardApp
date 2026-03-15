import { Box } from '@mui/material';
import { Outlet } from 'react-router-dom';

export default function AppLayout() {
  return (
    <Box
      sx={{
        minHeight: '100vh',
        bgcolor: 'background.default',
        color: 'text.primary',
        display: 'flex',
        flexDirection: 'column',
      }}
    >
      <Outlet />
    </Box>
  );
}
