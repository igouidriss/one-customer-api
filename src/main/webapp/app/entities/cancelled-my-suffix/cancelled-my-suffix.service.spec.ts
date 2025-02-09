import axios from 'axios';
import sinon from 'sinon';
import dayjs from 'dayjs';

import CancelledMySuffixService from './cancelled-my-suffix.service';
import { DATE_TIME_FORMAT } from '@/shared/composables/date-format';
import { CancelledMySuffix } from '@/shared/model/cancelled-my-suffix.model';

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
  describe('CancelledMySuffix Service', () => {
    let service: CancelledMySuffixService;
    let elemDefault;
    let currentDate: Date;

    beforeEach(() => {
      service = new CancelledMySuffixService();
      currentDate = new Date();
      elemDefault = new CancelledMySuffix('ABC', 'AAAAAAA', false, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = { cancelDate: dayjs(currentDate).format(DATE_TIME_FORMAT), ...elemDefault };
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

      it('should create a CancelledMySuffix', async () => {
        const returnedFromService = { id: 'ABC', cancelDate: dayjs(currentDate).format(DATE_TIME_FORMAT), ...elemDefault };
        const expected = { cancelDate: currentDate, ...returnedFromService };

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a CancelledMySuffix', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a CancelledMySuffix', async () => {
        const returnedFromService = {
          cancelReason: 'BBBBBB',
          isItCancelled: true,
          cancelDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
          ...elemDefault,
        };

        const expected = { cancelDate: currentDate, ...returnedFromService };
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a CancelledMySuffix', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a CancelledMySuffix', async () => {
        const patchObject = { cancelReason: 'BBBBBB', ...new CancelledMySuffix() };
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = { cancelDate: currentDate, ...returnedFromService };
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a CancelledMySuffix', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of CancelledMySuffix', async () => {
        const returnedFromService = {
          cancelReason: 'BBBBBB',
          isItCancelled: true,
          cancelDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
          ...elemDefault,
        };
        const expected = { cancelDate: currentDate, ...returnedFromService };
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve().then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of CancelledMySuffix', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a CancelledMySuffix', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete('ABC').then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a CancelledMySuffix', async () => {
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
