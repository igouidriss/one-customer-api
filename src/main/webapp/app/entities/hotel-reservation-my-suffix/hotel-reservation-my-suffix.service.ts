import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { type IHotelReservationMySuffix } from '@/shared/model/hotel-reservation-my-suffix.model';

const baseApiUrl = 'api/hotel-reservations';

export default class HotelReservationMySuffixService {
  public find(id: string): Promise<IHotelReservationMySuffix> {
    return new Promise<IHotelReservationMySuffix>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${id}`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public retrieve(paginationQuery?: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}?${buildPaginationQueryOpts(paginationQuery)}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public delete(id: string): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .delete(`${baseApiUrl}/${id}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public create(entity: IHotelReservationMySuffix): Promise<IHotelReservationMySuffix> {
    return new Promise<IHotelReservationMySuffix>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public update(entity: IHotelReservationMySuffix): Promise<IHotelReservationMySuffix> {
    return new Promise<IHotelReservationMySuffix>((resolve, reject) => {
      axios
        .put(`${baseApiUrl}/${entity.id}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public partialUpdate(entity: IHotelReservationMySuffix): Promise<IHotelReservationMySuffix> {
    return new Promise<IHotelReservationMySuffix>((resolve, reject) => {
      axios
        .patch(`${baseApiUrl}/${entity.id}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
