import axios from 'axios';
import sinon from 'sinon';
import dayjs from 'dayjs';

import ExpenseMySuffixService from './expense-my-suffix.service';
import { DATE_FORMAT, DATE_TIME_FORMAT } from '@/shared/composables/date-format';
import { ExpenseMySuffix } from '@/shared/model/expense-my-suffix.model';

const error = {
  response: {
    status: null,
    data: {
      type: null,
    },
  },
};

const axiosStub = {
  get: sinon.stub(axios, 'get'),
  post: sinon.stub(axios, 'post'),
  put: sinon.stub(axios, 'put'),
  patch: sinon.stub(axios, 'patch'),
  delete: sinon.stub(axios, 'delete'),
};

describe('Service Tests', () => {
  describe('ExpenseMySuffix Service', () => {
    let service: ExpenseMySuffixService;
    let elemDefault;
    let currentDate: Date;

    beforeEach(() => {
      service = new ExpenseMySuffixService();
      currentDate = new Date();
      elemDefault = new ExpenseMySuffix(
        'ABC',
        'AAAAAAA',
        0,
        0,
        0,
        'AAAAAAA',
        currentDate,
        currentDate,
        currentDate,
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = {
          date: dayjs(currentDate).format(DATE_FORMAT),
          arrivalDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
          leaveDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
          ...elemDefault,
        };
        axiosStub.get.resolves({ data: returnedFromService });

        return service.find('ABC').then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });

      it('should not find an element', async () => {
        axiosStub.get.rejects(error);
        return service
          .find('ABC')
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should create a ExpenseMySuffix', async () => {
        const returnedFromService = {
          id: 'ABC',
          date: dayjs(currentDate).format(DATE_FORMAT),
          arrivalDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
          leaveDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
          ...elemDefault,
        };
        const expected = { date: currentDate, arrivalDate: currentDate, leaveDate: currentDate, ...returnedFromService };

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a ExpenseMySuffix', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a ExpenseMySuffix', async () => {
        const returnedFromService = {
          expenseType: 'BBBBBB',
          amount: 1,
          depositAmount: 1,
          totalAmount: 1,
          shift: 'BBBBBB',
          date: dayjs(currentDate).format(DATE_FORMAT),
          arrivalDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
          leaveDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
          guestCount: 1,
          hotelName: 'BBBBBB',
          hotelId: 'BBBBBB',
          restaurantName: 'BBBBBB',
          restaurantId: 'BBBBBB',
          clientId: 'BBBBBB',
          ...elemDefault,
        };

        const expected = { date: currentDate, arrivalDate: currentDate, leaveDate: currentDate, ...returnedFromService };
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a ExpenseMySuffix', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a ExpenseMySuffix', async () => {
        const patchObject = {
          amount: 1,
          depositAmount: 1,
          shift: 'BBBBBB',
          arrivalDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
          hotelName: 'BBBBBB',
          hotelId: 'BBBBBB',
          ...new ExpenseMySuffix(),
        };
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = { date: currentDate, arrivalDate: currentDate, leaveDate: currentDate, ...returnedFromService };
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a ExpenseMySuffix', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of ExpenseMySuffix', async () => {
        const returnedFromService = {
          expenseType: 'BBBBBB',
          amount: 1,
          depositAmount: 1,
          totalAmount: 1,
          shift: 'BBBBBB',
          date: dayjs(currentDate).format(DATE_FORMAT),
          arrivalDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
          leaveDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
          guestCount: 1,
          hotelName: 'BBBBBB',
          hotelId: 'BBBBBB',
          restaurantName: 'BBBBBB',
          restaurantId: 'BBBBBB',
          clientId: 'BBBBBB',
          ...elemDefault,
        };
        const expected = { date: currentDate, arrivalDate: currentDate, leaveDate: currentDate, ...returnedFromService };
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve().then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of ExpenseMySuffix', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a ExpenseMySuffix', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete('ABC').then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a ExpenseMySuffix', async () => {
        axiosStub.delete.rejects(error);

        return service
          .delete('ABC')
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });
    });
  });
});
