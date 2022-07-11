import {BaseModel} from "../base-model";

export interface DataObject<T extends BaseModel> {

  totalRecords: number;
  list: Array<T>;
  recordsPerPage: number;
  dataSize: number;
  firstPage: number;
  maxPage: number;
  currentPage?: number;
}
