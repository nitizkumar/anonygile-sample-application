import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AnonygileTestModule } from '../../../test.module';
import { SizeEstimateComponent } from 'app/entities/size-estimate/size-estimate.component';
import { SizeEstimateService } from 'app/entities/size-estimate/size-estimate.service';
import { SizeEstimate } from 'app/shared/model/size-estimate.model';

describe('Component Tests', () => {
  describe('SizeEstimate Management Component', () => {
    let comp: SizeEstimateComponent;
    let fixture: ComponentFixture<SizeEstimateComponent>;
    let service: SizeEstimateService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AnonygileTestModule],
        declarations: [SizeEstimateComponent],
      })
        .overrideTemplate(SizeEstimateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SizeEstimateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SizeEstimateService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SizeEstimate(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.sizeEstimates && comp.sizeEstimates[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
