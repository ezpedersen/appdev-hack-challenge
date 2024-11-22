// @deno-types="npm:@types/express"
import express, { NextFunction, Request, Response } from "npm:express";

const app = express();

const PORT = Number(Deno.env.get("PORT")) || 3000;

app.get("/", (_req, res) => {
    res.status(200).send("Testing")
})
app.listen(PORT, ()=>{
    console.log(`Listening on port ${PORT}`)
})