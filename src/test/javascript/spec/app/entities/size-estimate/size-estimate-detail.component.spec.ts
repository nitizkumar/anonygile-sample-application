import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AnonygileTestModule } from '../../../test.module';
import { SizeEstimateDetailComponent } from 'app/entities/size-estimate/size-estimate-detail.component';
import { SizeEstimate } from 'app/shared/model/size-estimate.model';

describe('Component Tests', () => {
  describe('SizeEstimate Management Detail Component', () => {
    let comp: SizeEstimateDetailComponent;
    let fixture: ComponentFixture<SizeEstimateDetailComponent>;
    const route = ({ data: of({ sizeEstimate: new SizeEstimate(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AnonygileTestModule],
        declarations: [SizeEstimateDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(SizeEstimateDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SizeEstimateDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load sizeEstimate on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.sizeEstimate).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
