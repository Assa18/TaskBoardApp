import ErrorOutlineIcon from '@mui/icons-material/ErrorOutline';
import { Box, Typography, Button, Paper } from '@mui/material';
import { useNavigate } from 'react-router-dom';

type ErrorProps = {
  message: string;
};

export default function Error({ message }: ErrorProps) {
  const navigate = useNavigate();

  return (
    <Box display="flex" justifyContent="center" alignItems="center" height="100vh" bgcolor="#f5f5f5" p={2}>
      <Paper elevation={3} sx={{ p: 4, maxWidth: 400, textAlign: 'center' }}>
        <ErrorOutlineIcon color="error" sx={{ fontSize: 60, mb: 2 }} />

        <Typography variant="h4" gutterBottom>
          Something Went Wrong
        </Typography>

        <Typography variant="body1" color="text.secondary" sx={{ mb: 3 }}>
          {message}
        </Typography>

        <Button variant="contained" onClick={() => navigate(-1)} sx={{ mt: 1 }}>
          Go Back
        </Button>
      </Paper>
    </Box>
  );
}
