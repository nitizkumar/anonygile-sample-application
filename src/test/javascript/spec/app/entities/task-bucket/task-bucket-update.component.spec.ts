import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AnonygileTestModule } from '../../../test.module';
import { TaskBucketUpdateComponent } from 'app/entities/task-bucket/task-bucket-update.component';
import { TaskBucketService } from 'app/entities/task-bucket/task-bucket.service';
import { TaskBucket } from 'app/shared/model/task-bucket.model';

describe('Component Tests', () => {
  describe('TaskBucket Management Update Component', () => {
    let comp: TaskBucketUpdateComponent;
    let fixture: ComponentFixture<TaskBucketUpdateComponent>;
    let service: TaskBucketService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AnonygileTestModule],
        declarations: [TaskBucketUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TaskBucketUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TaskBucketUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TaskBucketService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TaskBucket(123);
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
        const entity = new TaskBucket();
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
