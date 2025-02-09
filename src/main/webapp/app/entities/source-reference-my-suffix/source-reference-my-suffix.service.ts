import axios from 'axios';

import { type ISourceReferenceMySuffix } from '@/shared/model/source-reference-my-suffix.model';

const baseApiUrl = 'api/source-references';

export default class SourceReferenceMySuffixService {
  public find(id: string): Promise<ISourceReferenceMySuffix> {
    return new Promise<ISourceReferenceMySuffix>((resolve, reject) => {
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

  public retrieve(): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrl)
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

  public create(entity: ISourceReferenceMySuffix): Promise<ISourceReferenceMySuffix> {
    return new Promise<ISourceReferenceMySuffix>((resolve, reject) => {
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

  public update(entity: ISourceReferenceMySuffix): Promise<ISourceReferenceMySuffix> {
    return new Promise<ISourceReferenceMySuffix>((resolve, reject) => {
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

  public partialUpdate(entity: ISourceReferenceMySuffix): Promise<ISourceReferenceMySuffix> {
    return new Promise<ISourceReferenceMySuffix>((resolve, reject) => {
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
