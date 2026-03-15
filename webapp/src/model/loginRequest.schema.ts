import { z } from 'zod';

export const loginRequestSchema = z.object({
  email: z.string().min(1, 'Email is required').max(100, 'Email must be at most 100 characters'),

  password: z
    .string()
    .min(1, 'Password is required')
    .min(6, 'Password must be at least 6 characters')
    .max(100, 'Password must be at most 100 characters'),
});

export type LoginRequestDto = z.infer<typeof loginRequestSchema>;
