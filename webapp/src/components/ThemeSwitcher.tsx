import { MenuItem, Select } from '@mui/material';
import { useThemeContext } from '../hooks/useThemeContext';
import { AppTheme } from '../theme/themes';
import { setUserTheme } from '../api/userApi';
import { useAuth } from '../hooks/useAuth';
import { SetThemeDto } from '../model/SetThemeDto';

export default function ThemeSwitcher() {
  const { themeName, setTheme } = useThemeContext();
  const { userId } = useAuth();

  function handleChange(newTheme: AppTheme) {
    setTheme(newTheme);
    setUserTheme(userId, { theme: newTheme as string } as SetThemeDto);
  }

  return (
    <Select size="small" value={themeName} onChange={(e) => handleChange(e.target.value as AppTheme)}>
      <MenuItem value="light">Light</MenuItem>
      <MenuItem value="dark">Dark</MenuItem>
      <MenuItem value="orange">Orange</MenuItem>
    </Select>
  );
}
