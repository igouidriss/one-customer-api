import axios from 'axios';
import sinon from 'sinon';
import dayjs from 'dayjs';

import PayloadMySuffixService from './payload-my-suffix.service';
import { DATE_FORMAT } from '@/shared/composables/date-format';
import { PayloadMySuffix } from '@/shared/model/payload-my-suffix.model';

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
  describe('PayloadMySuffix Service', () => {
    let service: PayloadMySuffixService;
    let elemDefault;
    let currentDate: Date;

    beforeEach(() => {
      service = new PayloadMySuffixService();
      currentDate = new Date();
      elemDefault = new PayloadMySuffix('ABC', 'AAAAAAA', 'AAAAAAA', currentDate, 'AAAAAAA', false);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = { birthDate: dayjs(currentDate).format(DATE_FORMAT), ...elemDefault };
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

      it('should create a PayloadMySuffix', async () => {
        const returnedFromService = { id: 'ABC', birthDate: dayjs(currentDate).format(DATE_FORMAT), ...elemDefault };
        const expected = { birthDate: currentDate, ...returnedFromService };

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a PayloadMySuffix', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a PayloadMySuffix', async () => {
        const returnedFromService = {
          lastName: 'BBBBBB',
          firstName: 'BBBBBB',
          birthDate: dayjs(currentDate).format(DATE_FORMAT),
          lang: 'BBBBBB',
          isVip: true,
          ...elemDefault,
        };

        const expected = { birthDate: currentDate, ...returnedFromService };
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a PayloadMySuffix', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a PayloadMySuffix', async () => {
        const patchObject = { birthDate: dayjs(currentDate).format(DATE_FORMAT), lang: 'BBBBBB', ...new PayloadMySuffix() };
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = { birthDate: currentDate, ...returnedFromService };
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a PayloadMySuffix', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of PayloadMySuffix', async () => {
        const returnedFromService = {
          lastName: 'BBBBBB',
          firstName: 'BBBBBB',
          birthDate: dayjs(currentDate).format(DATE_FORMAT),
          lang: 'BBBBBB',
          isVip: true,
          ...elemDefault,
        };
        const expected = { birthDate: currentDate, ...returnedFromService };
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve().then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of PayloadMySuffix', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a PayloadMySuffix', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete('ABC').then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a PayloadMySuffix', async () => {
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
