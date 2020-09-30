import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AnonygileSharedModule } from 'app/shared/shared.module';
import { TaskBucketComponent } from './task-bucket.component';
import { TaskBucketDetailComponent } from './task-bucket-detail.component';
import { TaskBucketUpdateComponent } from './task-bucket-update.component';
import { TaskBucketDeleteDialogComponent } from './task-bucket-delete-dialog.component';
import { taskBucketRoute } from './task-bucket.route';

@NgModule({
  imports: [AnonygileSharedModule, RouterModule.forChild(taskBucketRoute)],
  declarations: [TaskBucketComponent, TaskBucketDetailComponent, TaskBucketUpdateComponent, TaskBucketDeleteDialogComponent],
  entryComponents: [TaskBucketDeleteDialogComponent],
})
export class AnonygileTaskBucketModule {}
