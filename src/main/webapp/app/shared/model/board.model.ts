import { IUser } from 'app/core/user/user.model';

export interface IBoard {
  id?: number;
  name?: string;
  type?: string;
  owner?: IUser;
}

export class Board implements IBoard {
  constructor(public id?: number, public name?: string, public type?: string, public owner?: IUser) {}
}
