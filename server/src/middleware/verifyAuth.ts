// @deno-types="npm:@types/express"
import { NextFunction, Response } from "npm:express";
import { auth } from "../firebase.ts";
import { AuthenticatedRequest } from "../types/AuthenticatedRequest.ts";
const verifyAuth = (req: AuthenticatedRequest, res: Response, next: NextFunction) => {
  const authHeader = req.headers.authorization;
  if (!authHeader?.startsWith("Bearer ")) return res.sendStatus(401);
  const idToken = authHeader.split(" ")[1];
  auth.verifyIdToken(idToken).then((decodedToken) => {
    const uid = decodedToken.uid;
    req.uid = uid;
    next();
  }).catch((error) => {
    console.log(error);
    res.sendStatus(403);
  });
};
export { verifyAuth };
