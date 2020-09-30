import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITaskBucket } from 'app/shared/model/task-bucket.model';

@Component({
  selector: 'jhi-task-bucket-detail',
  templateUrl: './task-bucket-detail.component.html',
})
export class TaskBucketDetailComponent implements OnInit {
  taskBucket: ITaskBucket | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ taskBucket }) => (this.taskBucket = taskBucket));
  }

  previousState(): void {
    window.history.back();
  }
}
