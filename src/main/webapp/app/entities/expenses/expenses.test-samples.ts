import dayjs from 'dayjs/esm';

import { IExpenses, NewExpenses } from './expenses.model';

export const sampleWithRequiredData: IExpenses = {
  id: 77490,
  description: 'initiative',
  amount: 35655,
  date: dayjs('2025-06-24T08:42'),
};

export const sampleWithPartialData: IExpenses = {
  id: 60706,
  description: 'Sharable',
  amount: 2385,
  date: dayjs('2025-06-24T12:44'),
};

export const sampleWithFullData: IExpenses = {
  id: 3074,
  description: 'Samoa one-to-one',
  amount: 73225,
  date: dayjs('2025-06-24T14:48'),
};

export const sampleWithNewData: NewExpenses = {
  description: 'online',
  amount: 12690,
  date: dayjs('2025-06-24T13:47'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
