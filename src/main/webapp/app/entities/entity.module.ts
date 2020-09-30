import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'board',
        loadChildren: () => import('./board/board.module').then(m => m.AnonygileBoardModule),
      },
      {
        path: 'task-bucket',
        loadChildren: () => import('./task-bucket/task-bucket.module').then(m => m.AnonygileTaskBucketModule),
      },
      {
        path: 'task',
        loadChildren: () => import('./task/task.module').then(m => m.AnonygileTaskModule),
      },
      {
        path: 'size-estimate',
        loadChildren: () => import('./size-estimate/size-estimate.module').then(m => m.AnonygileSizeEstimateModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class AnonygileEntityModule {}
