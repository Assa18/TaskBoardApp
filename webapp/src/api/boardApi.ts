import { FetchBoardDto } from '../model/FetchBoardDto';
import { ShortTodoData } from '../model/FetchTodoShortDto';
import { SaveBoardDto } from '../model/saveBoard.schema';
import { SaveTodoDto } from '../model/saveTodo.schema';
import { DetailedTodoData } from '../model/FetchTodoDetailedDto';
import { createBaseApi } from './baseApi';

const boardApi = createBaseApi();
boardApi.defaults.baseURL += '/boards';

export async function fetchAllBoards(): Promise<FetchBoardDto[]> {
  const result = await boardApi.get<FetchBoardDto[]>('');
  return result.data;
}

export async function fetchBoardTodos(boardId: number): Promise<ShortTodoData[]> {
  const result = await boardApi.get<ShortTodoData[]>(`/${boardId}/todos`);
  return result.data;
}

export async function deleteBoard(id: number) {
  await boardApi.delete(`/${id}`);
}

export async function addTodo(boardId: number, dto: SaveTodoDto) {
  await boardApi.post<DetailedTodoData>(`/${boardId}/todos`, dto);
}

export async function renameBoard(boardId: number, dto: SaveBoardDto) {
  await boardApi.put<FetchBoardDto>(`/${boardId}`, dto);
}
