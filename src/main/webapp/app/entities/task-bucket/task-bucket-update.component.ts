import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITaskBucket, TaskBucket } from 'app/shared/model/task-bucket.model';
import { TaskBucketService } from './task-bucket.service';
import { IBoard } from 'app/shared/model/board.model';
import { BoardService } from 'app/entities/board/board.service';

@Component({
  selector: 'jhi-task-bucket-update',
  templateUrl: './task-bucket-update.component.html',
})
export class TaskBucketUpdateComponent implements OnInit {
  isSaving = false;
  boards: IBoard[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    status: [],
    board: [],
  });

  constructor(
    protected taskBucketService: TaskBucketService,
    protected boardService: BoardService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ taskBucket }) => {
      this.updateForm(taskBucket);

      this.boardService.query().subscribe((res: HttpResponse<IBoard[]>) => (this.boards = res.body || []));
    });
  }

  updateForm(taskBucket: ITaskBucket): void {
    this.editForm.patchValue({
      id: taskBucket.id,
      name: taskBucket.name,
      status: taskBucket.status,
      board: taskBucket.board,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const taskBucket = this.createFromForm();
    if (taskBucket.id !== undefined) {
      this.subscribeToSaveResponse(this.taskBucketService.update(taskBucket));
    } else {
      this.subscribeToSaveResponse(this.taskBucketService.create(taskBucket));
    }
  }

  private createFromForm(): ITaskBucket {
    return {
      ...new TaskBucket(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      status: this.editForm.get(['status'])!.value,
      board: this.editForm.get(['board'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITaskBucket>>): void {
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

  trackById(index: number, item: IBoard): any {
    return item.id;
  }
}
