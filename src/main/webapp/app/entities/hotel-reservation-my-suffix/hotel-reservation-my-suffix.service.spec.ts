import axios from 'axios';
import sinon from 'sinon';
import dayjs from 'dayjs';

import HotelReservationMySuffixService from './hotel-reservation-my-suffix.service';
import { DATE_FORMAT, DATE_TIME_FORMAT } from '@/shared/composables/date-format';
import { HotelReservationMySuffix } from '@/shared/model/hotel-reservation-my-suffix.model';

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
  describe('HotelReservationMySuffix Service', () => {
    let service: HotelReservationMySuffixService;
    let elemDefault;
    let currentDate: Date;

    beforeEach(() => {
      service = new HotelReservationMySuffixService();
      currentDate = new Date();
      elemDefault = new HotelReservationMySuffix(
        'ABC',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        currentDate,
        0,
        currentDate,
        currentDate,
        0,
        'AAAAAAA',
        'AAAAAAA',
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = {
          reservationTimestamp: dayjs(currentDate).format(DATE_TIME_FORMAT),
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

      it('should create a HotelReservationMySuffix', async () => {
        const returnedFromService = {
          id: 'ABC',
          reservationTimestamp: dayjs(currentDate).format(DATE_TIME_FORMAT),
          date: dayjs(currentDate).format(DATE_FORMAT),
          arrivalDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
          leaveDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
          ...elemDefault,
        };
        const expected = {
          reservationTimestamp: currentDate,
          date: currentDate,
          arrivalDate: currentDate,
          leaveDate: currentDate,
          ...returnedFromService,
        };

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a HotelReservationMySuffix', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a HotelReservationMySuffix', async () => {
        const returnedFromService = {
          aggregateId: 'BBBBBB',
          aggregateType: 'BBBBBB',
          clientId: 'BBBBBB',
          domaine: 'BBBBBB',
          source: 'BBBBBB',
          reservationTimestamp: dayjs(currentDate).format(DATE_TIME_FORMAT),
          projection: 'BBBBBB',
          date: dayjs(currentDate).format(DATE_FORMAT),
          totalAmount: 1,
          arrivalDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
          leaveDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
          guestCount: 1,
          hotelName: 'BBBBBB',
          hotelId: 'BBBBBB',
          ...elemDefault,
        };

        const expected = {
          reservationTimestamp: currentDate,
          date: currentDate,
          arrivalDate: currentDate,
          leaveDate: currentDate,
          ...returnedFromService,
        };
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a HotelReservationMySuffix', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a HotelReservationMySuffix', async () => {
        const patchObject = {
          date: dayjs(currentDate).format(DATE_FORMAT),
          totalAmount: 1,
          leaveDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
          hotelName: 'BBBBBB',
          hotelId: 'BBBBBB',
          ...new HotelReservationMySuffix(),
        };
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = {
          reservationTimestamp: currentDate,
          date: currentDate,
          arrivalDate: currentDate,
          leaveDate: currentDate,
          ...returnedFromService,
        };
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a HotelReservationMySuffix', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of HotelReservationMySuffix', async () => {
        const returnedFromService = {
          aggregateId: 'BBBBBB',
          aggregateType: 'BBBBBB',
          clientId: 'BBBBBB',
          domaine: 'BBBBBB',
          source: 'BBBBBB',
          reservationTimestamp: dayjs(currentDate).format(DATE_TIME_FORMAT),
          projection: 'BBBBBB',
          date: dayjs(currentDate).format(DATE_FORMAT),
          totalAmount: 1,
          arrivalDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
          leaveDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
          guestCount: 1,
          hotelName: 'BBBBBB',
          hotelId: 'BBBBBB',
          ...elemDefault,
        };
        const expected = {
          reservationTimestamp: currentDate,
          date: currentDate,
          arrivalDate: currentDate,
          leaveDate: currentDate,
          ...returnedFromService,
        };
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve({ sort: {}, page: 0, size: 10 }).then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of HotelReservationMySuffix', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a HotelReservationMySuffix', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete('ABC').then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a HotelReservationMySuffix', async () => {
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
