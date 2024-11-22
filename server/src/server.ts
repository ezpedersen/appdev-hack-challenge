// @deno-types="npm:@types/express"
import express, { NextFunction, Request, Response } from "npm:express";
import "jsr:@std/dotenv/load";
import mongoose from "npm:mongoose";

const app = express();
const PORT = Number(Deno.env.get("PORT")) || 3000;
const MONGODB_URI = String(Deno.env.get("MONGODB_URI"));

await mongoose.connect(MONGODB_URI);

app.get("/", (_req, res) => {
    res.status(200).send("Testing")
})

app.listen(PORT, ()=>{
    console.log(`Listening on port ${PORT}`)
})