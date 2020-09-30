import { Moment } from 'moment';
import { ITaskBucket } from 'app/shared/model/task-bucket.model';
import { TaskType } from 'app/shared/model/enumerations/task-type.model';
import { TaskStatus } from 'app/shared/model/enumerations/task-status.model';

export interface ITask {
  id?: number;
  title?: string;
  type?: TaskType;
  status?: TaskStatus;
  startDate?: Moment;
  endDate?: Moment;
  priority?: number;
  size?: string;
  bucket?: ITaskBucket;
}

export class Task implements ITask {
  constructor(
    public id?: number,
    public title?: string,
    public type?: TaskType,
    public status?: TaskStatus,
    public startDate?: Moment,
    public endDate?: Moment,
    public priority?: number,
    public size?: string,
    public bucket?: ITaskBucket
  ) {}
}
