import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ITask, Task } from 'app/shared/model/task.model';
import { TaskService } from './task.service';
import { ITaskBucket } from 'app/shared/model/task-bucket.model';
import { TaskBucketService } from 'app/entities/task-bucket/task-bucket.service';

@Component({
  selector: 'jhi-task-update',
  templateUrl: './task-update.component.html',
})
export class TaskUpdateComponent implements OnInit {
  isSaving = false;
  taskbuckets: ITaskBucket[] = [];

  editForm = this.fb.group({
    id: [],
    title: [],
    type: [],
    status: [],
    startDate: [],
    endDate: [],
    priority: [],
    size: [],
    bucket: [],
  });

  constructor(
    protected taskService: TaskService,
    protected taskBucketService: TaskBucketService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ task }) => {
      if (!task.id) {
        const today = moment().startOf('day');
        task.startDate = today;
        task.endDate = today;
      }

      this.updateForm(task);

      this.taskBucketService.query().subscribe((res: HttpResponse<ITaskBucket[]>) => (this.taskbuckets = res.body || []));
    });
  }

  updateForm(task: ITask): void {
    this.editForm.patchValue({
      id: task.id,
      title: task.title,
      type: task.type,
      status: task.status,
      startDate: task.startDate ? task.startDate.format(DATE_TIME_FORMAT) : null,
      endDate: task.endDate ? task.endDate.format(DATE_TIME_FORMAT) : null,
      priority: task.priority,
      size: task.size,
      bucket: task.bucket,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const task = this.createFromForm();
    if (task.id !== undefined) {
      this.subscribeToSaveResponse(this.taskService.update(task));
    } else {
      this.subscribeToSaveResponse(this.taskService.create(task));
    }
  }

  private createFromForm(): ITask {
    return {
      ...new Task(),
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      type: this.editForm.get(['type'])!.value,
      status: this.editForm.get(['status'])!.value,
      startDate: this.editForm.get(['startDate'])!.value ? moment(this.editForm.get(['startDate'])!.value, DATE_TIME_FORMAT) : undefined,
      endDate: this.editForm.get(['endDate'])!.value ? moment(this.editForm.get(['endDate'])!.value, DATE_TIME_FORMAT) : undefined,
      priority: this.editForm.get(['priority'])!.value,
      size: this.editForm.get(['size'])!.value,
      bucket: this.editForm.get(['bucket'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITask>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: ITaskBucket): any {
    return item.id;
  }
}
