import { useForm } from 'react-hook-form';
import { useEffect } from 'react';
import { Button, MenuItem, Stack, TextField } from '@mui/material';
import { zodResolver } from '@hookform/resolvers/zod';
import { saveTodoSchema, SaveTodoDto } from '../model/saveTodo.schema';

type TodoFormProps = {
  defaultValues?: Partial<SaveTodoDto>;
  onSubmit: (data: SaveTodoDto) => void;
  submitLabel: string;
};

export default function TodoForm({ defaultValues = undefined, onSubmit, submitLabel }: TodoFormProps) {
  const {
    register,
    handleSubmit,
    reset,
    formState: { errors },
  } = useForm<SaveTodoDto>({
    defaultValues,
    resolver: zodResolver(saveTodoSchema),
  });

  useEffect(() => {
    if (defaultValues) {
      reset(defaultValues);
    }
  }, [defaultValues, reset]);

  return (
    <form onSubmit={handleSubmit(onSubmit)}>
      <Stack spacing={3}>
        <TextField label="Title" {...register('title')} error={!!errors.title} helperText={errors.title?.message} />

        <TextField
          label="Description"
          multiline
          rows={3}
          {...register('description')}
          error={!!errors.description}
          helperText={errors.description?.message}
        />

        <TextField
          label="Deadline"
          type="datetime-local"
          {...register('deadline')}
          error={!!errors.deadline}
          helperText={errors.deadline?.message}
        />

        <TextField
          label="Severity"
          select
          {...register('severity')}
          error={!!errors.severity}
          helperText={errors.severity?.message}
        >
          <MenuItem value="LAZY">Lazy</MenuItem>
          <MenuItem value="NORMAL">Normal</MenuItem>
          <MenuItem value="URGENT">Urgent</MenuItem>
        </TextField>

        <Button type="submit" variant="contained">
          {submitLabel}
        </Button>
      </Stack>
    </form>
  );
}
