import { createContext, useContext } from 'react';
import { AppTheme } from '../theme/themes';

type ThemeContextType = {
  themeName: AppTheme;
  setTheme: (theme: AppTheme) => void;
};

export const ThemeContext = createContext<ThemeContextType | undefined>(undefined);

export const useThemeContext = () => {
  const ctx = useContext(ThemeContext);
  if (!ctx) throw new Error('useThemeContext must be used inside ThemeProvider');
  return ctx;
};
