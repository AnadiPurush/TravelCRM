export interface JwtPayload {
  sub?: string;
  superAdmin?: boolean;
  roles?: string[];
  name?: string;
  permissions?: string[];
  iat?: number;
  exp?: number;
  [key: string]: any;
}
