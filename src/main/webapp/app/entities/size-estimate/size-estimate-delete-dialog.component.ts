import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISizeEstimate } from 'app/shared/model/size-estimate.model';
import { SizeEstimateService } from './size-estimate.service';

@Component({
  templateUrl: './size-estimate-delete-dialog.component.html',
})
export class SizeEstimateDeleteDialogComponent {
  sizeEstimate?: ISizeEstimate;

  constructor(
    protected sizeEstimateService: SizeEstimateService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.sizeEstimateService.delete(id).subscribe(() => {
      this.eventManager.broadcast('sizeEstimateListModification');
      this.activeModal.close();
    });
  }
}
