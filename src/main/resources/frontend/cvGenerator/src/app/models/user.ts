import {UserDetails} from "./user-details";
import {UserSecurity} from "./user-security";
import {BaseModel} from "./base-model";

export interface User extends BaseModel{

  userDetails: UserDetails;
  userSecurity: UserSecurity;
  version?: number;

}
