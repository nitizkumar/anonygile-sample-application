import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITaskBucket } from 'app/shared/model/task-bucket.model';
import { TaskBucketService } from './task-bucket.service';

@Component({
  templateUrl: './task-bucket-delete-dialog.component.html',
})
export class TaskBucketDeleteDialogComponent {
  taskBucket?: ITaskBucket;

  constructor(
    protected taskBucketService: TaskBucketService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.taskBucketService.delete(id).subscribe(() => {
      this.eventManager.broadcast('taskBucketListModification');
      this.activeModal.close();
    });
  }
}
