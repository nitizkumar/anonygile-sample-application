import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISizeEstimate, SizeEstimate } from 'app/shared/model/size-estimate.model';
import { SizeEstimateService } from './size-estimate.service';
import { SizeEstimateComponent } from './size-estimate.component';
import { SizeEstimateDetailComponent } from './size-estimate-detail.component';
import { SizeEstimateUpdateComponent } from './size-estimate-update.component';

@Injectable({ providedIn: 'root' })
export class SizeEstimateResolve implements Resolve<ISizeEstimate> {
  constructor(private service: SizeEstimateService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISizeEstimate> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((sizeEstimate: HttpResponse<SizeEstimate>) => {
          if (sizeEstimate.body) {
            return of(sizeEstimate.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SizeEstimate());
  }
}

export const sizeEstimateRoute: Routes = [
  {
    path: '',
    component: SizeEstimateComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'anonygileApp.sizeEstimate.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SizeEstimateDetailComponent,
    resolve: {
      sizeEstimate: SizeEstimateResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'anonygileApp.sizeEstimate.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SizeEstimateUpdateComponent,
    resolve: {
      sizeEstimate: SizeEstimateResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'anonygileApp.sizeEstimate.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SizeEstimateUpdateComponent,
    resolve: {
      sizeEstimate: SizeEstimateResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'anonygileApp.sizeEstimate.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
