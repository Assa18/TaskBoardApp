import { z } from 'zod';

export const saveTodoSchema = z.object({
  title: z.string().min(1, 'Title is required').max(100, 'Title must be at most 100 characters'),

  description: z.string().min(1, 'Description is required').max(1000, 'Description must be at most 1000 characters'),

  deadline: z
    .string()
    .min(1, 'Deadline is required')
    .refine((value) => !Number.isNaN(Date.parse(value)), 'Invalid date'),

  severity: z.enum(['LAZY', 'NORMAL', 'URGENT']).refine((v) => v !== undefined, {
    message: 'Severity is required',
  }),
});

export type SaveTodoDto = z.infer<typeof saveTodoSchema>;
