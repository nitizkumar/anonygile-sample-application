import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AnonygileTestModule } from '../../../test.module';
import { SizeEstimateUpdateComponent } from 'app/entities/size-estimate/size-estimate-update.component';
import { SizeEstimateService } from 'app/entities/size-estimate/size-estimate.service';
import { SizeEstimate } from 'app/shared/model/size-estimate.model';

describe('Component Tests', () => {
  describe('SizeEstimate Management Update Component', () => {
    let comp: SizeEstimateUpdateComponent;
    let fixture: ComponentFixture<SizeEstimateUpdateComponent>;
    let service: SizeEstimateService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AnonygileTestModule],
        declarations: [SizeEstimateUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(SizeEstimateUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SizeEstimateUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SizeEstimateService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SizeEstimate(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new SizeEstimate();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
