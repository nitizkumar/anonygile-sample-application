import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISizeEstimate } from 'app/shared/model/size-estimate.model';

type EntityResponseType = HttpResponse<ISizeEstimate>;
type EntityArrayResponseType = HttpResponse<ISizeEstimate[]>;

@Injectable({ providedIn: 'root' })
export class SizeEstimateService {
  public resourceUrl = SERVER_API_URL + 'api/size-estimates';

  constructor(protected http: HttpClient) {}

  create(sizeEstimate: ISizeEstimate): Observable<EntityResponseType> {
    return this.http.post<ISizeEstimate>(this.resourceUrl, sizeEstimate, { observe: 'response' });
  }

  update(sizeEstimate: ISizeEstimate): Observable<EntityResponseType> {
    return this.http.put<ISizeEstimate>(this.resourceUrl, sizeEstimate, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISizeEstimate>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISizeEstimate[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
