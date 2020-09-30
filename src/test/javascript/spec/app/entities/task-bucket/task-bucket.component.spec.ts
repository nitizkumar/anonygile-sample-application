import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AnonygileTestModule } from '../../../test.module';
import { TaskBucketComponent } from 'app/entities/task-bucket/task-bucket.component';
import { TaskBucketService } from 'app/entities/task-bucket/task-bucket.service';
import { TaskBucket } from 'app/shared/model/task-bucket.model';

describe('Component Tests', () => {
  describe('TaskBucket Management Component', () => {
    let comp: TaskBucketComponent;
    let fixture: ComponentFixture<TaskBucketComponent>;
    let service: TaskBucketService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AnonygileTestModule],
        declarations: [TaskBucketComponent],
      })
        .overrideTemplate(TaskBucketComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TaskBucketComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TaskBucketService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TaskBucket(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.taskBuckets && comp.taskBuckets[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
