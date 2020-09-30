import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AnonygileSharedModule } from 'app/shared/shared.module';
import { SizeEstimateComponent } from './size-estimate.component';
import { SizeEstimateDetailComponent } from './size-estimate-detail.component';
import { SizeEstimateUpdateComponent } from './size-estimate-update.component';
import { SizeEstimateDeleteDialogComponent } from './size-estimate-delete-dialog.component';
import { sizeEstimateRoute } from './size-estimate.route';

@NgModule({
  imports: [AnonygileSharedModule, RouterModule.forChild(sizeEstimateRoute)],
  declarations: [SizeEstimateComponent, SizeEstimateDetailComponent, SizeEstimateUpdateComponent, SizeEstimateDeleteDialogComponent],
  entryComponents: [SizeEstimateDeleteDialogComponent],
})
export class AnonygileSizeEstimateModule {}
