import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ExpensesFormService, ExpensesFormGroup } from './expenses-form.service';
import { IExpenses } from '../expenses.model';
import { ExpensesService } from '../service/expenses.service';

@Component({
  selector: 'jhi-expenses-update',
  templateUrl: './expenses-update.component.html',
})
export class ExpensesUpdateComponent implements OnInit {
  isSaving = false;
  expenses: IExpenses | null = null;

  editForm: ExpensesFormGroup = this.expensesFormService.createExpensesFormGroup();

  constructor(
    protected expensesService: ExpensesService,
    protected expensesFormService: ExpensesFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ expenses }) => {
      this.expenses = expenses;
      if (expenses) {
        this.updateForm(expenses);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const expenses = this.expensesFormService.getExpenses(this.editForm);
    if (expenses.id !== null) {
      this.subscribeToSaveResponse(this.expensesService.update(expenses));
    } else {
      this.subscribeToSaveResponse(this.expensesService.create(expenses));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IExpenses>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(expenses: IExpenses): void {
    this.expenses = expenses;
    this.expensesFormService.resetForm(this.editForm, expenses);
  }
}
