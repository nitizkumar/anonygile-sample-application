import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISizeEstimate } from 'app/shared/model/size-estimate.model';
import { SizeEstimateService } from './size-estimate.service';
import { SizeEstimateDeleteDialogComponent } from './size-estimate-delete-dialog.component';

@Component({
  selector: 'jhi-size-estimate',
  templateUrl: './size-estimate.component.html',
})
export class SizeEstimateComponent implements OnInit, OnDestroy {
  sizeEstimates?: ISizeEstimate[];
  eventSubscriber?: Subscription;

  constructor(
    protected sizeEstimateService: SizeEstimateService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.sizeEstimateService.query().subscribe((res: HttpResponse<ISizeEstimate[]>) => (this.sizeEstimates = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSizeEstimates();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISizeEstimate): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSizeEstimates(): void {
    this.eventSubscriber = this.eventManager.subscribe('sizeEstimateListModification', () => this.loadAll());
  }

  delete(sizeEstimate: ISizeEstimate): void {
    const modalRef = this.modalService.open(SizeEstimateDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.sizeEstimate = sizeEstimate;
  }
}
