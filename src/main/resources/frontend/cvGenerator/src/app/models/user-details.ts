import {Gender} from "./gender";

export interface UserDetails {

  firstName: string;
  lastName: string;
  dob: Date;
  age?: number;
  gender: Gender;

}
