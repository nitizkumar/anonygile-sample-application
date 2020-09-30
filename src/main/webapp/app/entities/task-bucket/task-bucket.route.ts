import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITaskBucket, TaskBucket } from 'app/shared/model/task-bucket.model';
import { TaskBucketService } from './task-bucket.service';
import { TaskBucketComponent } from './task-bucket.component';
import { TaskBucketDetailComponent } from './task-bucket-detail.component';
import { TaskBucketUpdateComponent } from './task-bucket-update.component';

@Injectable({ providedIn: 'root' })
export class TaskBucketResolve implements Resolve<ITaskBucket> {
  constructor(private service: TaskBucketService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITaskBucket> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((taskBucket: HttpResponse<TaskBucket>) => {
          if (taskBucket.body) {
            return of(taskBucket.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TaskBucket());
  }
}

export const taskBucketRoute: Routes = [
  {
    path: '',
    component: TaskBucketComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'anonygileApp.taskBucket.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TaskBucketDetailComponent,
    resolve: {
      taskBucket: TaskBucketResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'anonygileApp.taskBucket.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TaskBucketUpdateComponent,
    resolve: {
      taskBucket: TaskBucketResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'anonygileApp.taskBucket.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TaskBucketUpdateComponent,
    resolve: {
      taskBucket: TaskBucketResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'anonygileApp.taskBucket.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
