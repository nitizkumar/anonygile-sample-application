import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISizeEstimate } from 'app/shared/model/size-estimate.model';

@Component({
  selector: 'jhi-size-estimate-detail',
  templateUrl: './size-estimate-detail.component.html',
})
export class SizeEstimateDetailComponent implements OnInit {
  sizeEstimate: ISizeEstimate | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sizeEstimate }) => (this.sizeEstimate = sizeEstimate));
  }

  previousState(): void {
    window.history.back();
  }
}
