import { createTheme } from '@mui/material';

export type AppTheme = 'light' | 'dark' | 'orange';

export const themes = {
  light: createTheme({
    palette: {
      mode: 'light',
      primary: {
        main: '#1976d2',
      },
      background: {
        default: '#f4f6f8',
        paper: '#ffffff',
      },
    },
  }),

  dark: createTheme({
    palette: {
      mode: 'dark',
      primary: {
        main: '#90caf9',
      },
      background: {
        default: '#121212',
        paper: '#1e1e1e',
      },
    },
  }),

  orange: createTheme({
    palette: {
      mode: 'light',
      primary: {
        main: '#f57c00',
      },
      secondary: {
        main: '#ffb74d',
      },
      background: {
        default: '#fff3e0',
        paper: '#ffffff',
      },
    },
  }),
};
