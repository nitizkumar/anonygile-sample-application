import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISizeEstimate, SizeEstimate } from 'app/shared/model/size-estimate.model';
import { SizeEstimateService } from './size-estimate.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IBoard } from 'app/shared/model/board.model';
import { BoardService } from 'app/entities/board/board.service';

type SelectableEntity = IUser | IBoard;

@Component({
  selector: 'jhi-size-estimate-update',
  templateUrl: './size-estimate-update.component.html',
})
export class SizeEstimateUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  boards: IBoard[] = [];

  editForm = this.fb.group({
    id: [],
    size: [],
    estimate: [],
    user: [],
    board: [],
  });

  constructor(
    protected sizeEstimateService: SizeEstimateService,
    protected userService: UserService,
    protected boardService: BoardService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sizeEstimate }) => {
      this.updateForm(sizeEstimate);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.boardService.query().subscribe((res: HttpResponse<IBoard[]>) => (this.boards = res.body || []));
    });
  }

  updateForm(sizeEstimate: ISizeEstimate): void {
    this.editForm.patchValue({
      id: sizeEstimate.id,
      size: sizeEstimate.size,
      estimate: sizeEstimate.estimate,
      user: sizeEstimate.user,
      board: sizeEstimate.board,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const sizeEstimate = this.createFromForm();
    if (sizeEstimate.id !== undefined) {
      this.subscribeToSaveResponse(this.sizeEstimateService.update(sizeEstimate));
    } else {
      this.subscribeToSaveResponse(this.sizeEstimateService.create(sizeEstimate));
    }
  }

  private createFromForm(): ISizeEstimate {
    return {
      ...new SizeEstimate(),
      id: this.editForm.get(['id'])!.value,
      size: this.editForm.get(['size'])!.value,
      estimate: this.editForm.get(['estimate'])!.value,
      user: this.editForm.get(['user'])!.value,
      board: this.editForm.get(['board'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISizeEstimate>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
