import axios from 'axios';
import sinon from 'sinon';
import dayjs from 'dayjs';

import OneCustomerMySuffixService from './one-customer-my-suffix.service';
import { DATE_TIME_FORMAT } from '@/shared/composables/date-format';
import { OneCustomerMySuffix } from '@/shared/model/one-customer-my-suffix.model';

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
  describe('OneCustomerMySuffix Service', () => {
    let service: OneCustomerMySuffixService;
    let elemDefault;
    let currentDate: Date;

    beforeEach(() => {
      service = new OneCustomerMySuffixService();
      currentDate = new Date();
      elemDefault = new OneCustomerMySuffix('ABC', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = { timestamp: dayjs(currentDate).format(DATE_TIME_FORMAT), ...elemDefault };
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

      it('should create a OneCustomerMySuffix', async () => {
        const returnedFromService = { id: 'ABC', timestamp: dayjs(currentDate).format(DATE_TIME_FORMAT), ...elemDefault };
        const expected = { timestamp: currentDate, ...returnedFromService };

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a OneCustomerMySuffix', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a OneCustomerMySuffix', async () => {
        const returnedFromService = {
          domaine: 'BBBBBB',
          aggregateId: 'BBBBBB',
          aggregateType: 'BBBBBB',
          timestamp: dayjs(currentDate).format(DATE_TIME_FORMAT),
          ...elemDefault,
        };

        const expected = { timestamp: currentDate, ...returnedFromService };
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a OneCustomerMySuffix', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a OneCustomerMySuffix', async () => {
        const patchObject = {
          aggregateType: 'BBBBBB',
          timestamp: dayjs(currentDate).format(DATE_TIME_FORMAT),
          ...new OneCustomerMySuffix(),
        };
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = { timestamp: currentDate, ...returnedFromService };
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a OneCustomerMySuffix', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of OneCustomerMySuffix', async () => {
        const returnedFromService = {
          domaine: 'BBBBBB',
          aggregateId: 'BBBBBB',
          aggregateType: 'BBBBBB',
          timestamp: dayjs(currentDate).format(DATE_TIME_FORMAT),
          ...elemDefault,
        };
        const expected = { timestamp: currentDate, ...returnedFromService };
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve({ sort: {}, page: 0, size: 10 }).then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of OneCustomerMySuffix', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a OneCustomerMySuffix', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete('ABC').then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a OneCustomerMySuffix', async () => {
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
