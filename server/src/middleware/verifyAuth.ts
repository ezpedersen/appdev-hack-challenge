// @deno-types="npm:@types/express"
import { NextFunction, Request, Response } from "npm:express";
import { auth } from "../firebase.ts";

const verifyAuth = (req: Request, res: Response, next: NextFunction) => {
  const authHeader = req.headers.authorization;
  if (!authHeader?.startsWith("Bearer ")) return res.sendStatus(401);
  const idToken = authHeader.split(" ")[1];
  auth.verifyIdToken(idToken).then((decodedToken) => {
    const uid = decodedToken.uid;
    res.json(uid);
    next();
  }).catch((error) => res.json(error));
  
};
export { verifyAuth };
