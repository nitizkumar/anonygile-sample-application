import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AnonygileTestModule } from '../../../test.module';
import { TaskBucketDetailComponent } from 'app/entities/task-bucket/task-bucket-detail.component';
import { TaskBucket } from 'app/shared/model/task-bucket.model';

describe('Component Tests', () => {
  describe('TaskBucket Management Detail Component', () => {
    let comp: TaskBucketDetailComponent;
    let fixture: ComponentFixture<TaskBucketDetailComponent>;
    const route = ({ data: of({ taskBucket: new TaskBucket(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AnonygileTestModule],
        declarations: [TaskBucketDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TaskBucketDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TaskBucketDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load taskBucket on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.taskBucket).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
