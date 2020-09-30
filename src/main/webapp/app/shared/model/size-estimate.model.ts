import { IUser } from 'app/core/user/user.model';
import { IBoard } from 'app/shared/model/board.model';

export interface ISizeEstimate {
  id?: number;
  size?: string;
  estimate?: number;
  user?: IUser;
  board?: IBoard;
}

export class SizeEstimate implements ISizeEstimate {
  constructor(public id?: number, public size?: string, public estimate?: number, public user?: IUser, public board?: IBoard) {}
}
