import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { type IRestorationReservationMySuffix } from '@/shared/model/restoration-reservation-my-suffix.model';

const baseApiUrl = 'api/restoration-reservations';

export default class RestorationReservationMySuffixService {
  public find(id: string): Promise<IRestorationReservationMySuffix> {
    return new Promise<IRestorationReservationMySuffix>((resolve, reject) => {
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

  public create(entity: IRestorationReservationMySuffix): Promise<IRestorationReservationMySuffix> {
    return new Promise<IRestorationReservationMySuffix>((resolve, reject) => {
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

  public update(entity: IRestorationReservationMySuffix): Promise<IRestorationReservationMySuffix> {
    return new Promise<IRestorationReservationMySuffix>((resolve, reject) => {
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

  public partialUpdate(entity: IRestorationReservationMySuffix): Promise<IRestorationReservationMySuffix> {
    return new Promise<IRestorationReservationMySuffix>((resolve, reject) => {
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
