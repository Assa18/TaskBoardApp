import { ShortTodoData } from '../model/FetchTodoShortDto';
import { DetailedTodoData } from '../model/FetchTodoDetailedDto';
import { MoveTodoDto } from '../model/MoveTodoDto';
import { SaveTodoDto } from '../model/saveTodo.schema';
import { createBaseApi } from './baseApi';

const todoApi = createBaseApi();
todoApi.defaults.baseURL += '/todos';

export async function fetchAllTodos(): Promise<ShortTodoData[]> {
  const result = await todoApi.get<ShortTodoData[]>('');
  return result.data;
}

export async function fetchTodoById(id: number): Promise<DetailedTodoData> {
  const result = await todoApi.get<DetailedTodoData>(`/${id}`);
  return result.data;
}

export async function deleteTodo(id: number) {
  await todoApi.delete(`/${id}`);
}

export async function moveTodo(id: number, dto: MoveTodoDto) {
  await todoApi.put<DetailedTodoData>(`/${id}/move`, dto);
}

export async function updateTodo(todoId: number, dto: SaveTodoDto) {
  await todoApi.put<DetailedTodoData>(`/${todoId}`, dto);
}
