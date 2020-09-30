import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITaskBucket } from 'app/shared/model/task-bucket.model';
import { TaskBucketService } from './task-bucket.service';
import { TaskBucketDeleteDialogComponent } from './task-bucket-delete-dialog.component';

@Component({
  selector: 'jhi-task-bucket',
  templateUrl: './task-bucket.component.html',
})
export class TaskBucketComponent implements OnInit, OnDestroy {
  taskBuckets?: ITaskBucket[];
  eventSubscriber?: Subscription;

  constructor(protected taskBucketService: TaskBucketService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.taskBucketService.query().subscribe((res: HttpResponse<ITaskBucket[]>) => (this.taskBuckets = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTaskBuckets();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITaskBucket): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTaskBuckets(): void {
    this.eventSubscriber = this.eventManager.subscribe('taskBucketListModification', () => this.loadAll());
  }

  delete(taskBucket: ITaskBucket): void {
    const modalRef = this.modalService.open(TaskBucketDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.taskBucket = taskBucket;
  }
}
