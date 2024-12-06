import { initializeApp } from "npm:firebase-admin/app";
import { getAuth } from "npm:firebase-admin/auth";

initializeApp();

const auth = getAuth();

export { auth };
