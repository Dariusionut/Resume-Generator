import {Role} from "./role";

export interface UserSecurity {

  username: string;
  email: string;
  password: string;
  roles: Role[];
  isEnabled?: boolean;
}
