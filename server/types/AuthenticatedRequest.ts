// @deno-types="npm:@types/express"
import { Request } from "npm:express";

export interface AuthenticatedRequest extends Request {
  uid?: string;
}