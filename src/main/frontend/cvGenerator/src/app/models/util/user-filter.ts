import {BaseFilter} from "./base-filter";

export interface UserFilter extends BaseFilter{

  name?: string;
  genderId?: string;
  roleId?: string;
  dob?: string;
}
