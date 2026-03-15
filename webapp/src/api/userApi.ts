import { FetchBoardDto } from '../model/FetchBoardDto';
import { SaveBoardDto } from '../model/saveBoard.schema';
import { SetThemeDto } from '../model/SetThemeDto';
import { createBaseApi } from './baseApi';

const userApi = createBaseApi();
userApi.defaults.baseURL += '/users';

export async function fetchBoardsOfUser(id: number | null): Promise<FetchBoardDto[]> {
  const result = await userApi.get<FetchBoardDto[]>(`/${id}/boards`);
  return result.data;
}

export async function addBoard(userId: number | null, dto: SaveBoardDto) {
  await userApi.post<FetchBoardDto>(`/${userId}/boards`, dto);
}

export async function setUserTheme(userId: number | null, dto: SetThemeDto) {
  await userApi.put(`/${userId}`, dto);
}
