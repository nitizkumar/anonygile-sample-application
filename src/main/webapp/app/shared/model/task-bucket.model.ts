import { IBoard } from 'app/shared/model/board.model';
import { TaskStatus } from 'app/shared/model/enumerations/task-status.model';

export interface ITaskBucket {
  id?: number;
  name?: string;
  status?: TaskStatus;
  board?: IBoard;
}

export class TaskBucket implements ITaskBucket {
  constructor(public id?: number, public name?: string, public status?: TaskStatus, public board?: IBoard) {}
}
