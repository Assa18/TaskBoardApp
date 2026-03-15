import { useMemo, useState } from 'react';
import { ThemeProvider as MuiThemeProvider, CssBaseline } from '@mui/material';
import { AppTheme, themes } from '../theme/themes';
import { ThemeContext } from '../hooks/useThemeContext';

const THEME_KEY = 'app-theme';

export function ThemeProvider({ children }: { children: React.ReactNode }) {
  const [themeName, setThemeName] = useState<AppTheme>(() => {
    return (localStorage.getItem(THEME_KEY) as AppTheme) || 'light';
  });

  const setTheme = (theme: AppTheme) => {
    setThemeName(theme);
    localStorage.setItem(THEME_KEY, theme);
  };

  const contextValue = useMemo(
    () => ({
      themeName,
      setTheme,
    }),
    [themeName],
  );

  return (
    <ThemeContext.Provider value={contextValue}>
      <MuiThemeProvider theme={themes[themeName]}>
        <CssBaseline />
        {children}
      </MuiThemeProvider>
    </ThemeContext.Provider>
  );
}
