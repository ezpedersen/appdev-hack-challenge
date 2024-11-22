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


import User from "../model/User.ts";
//get specific user by id
app.get('/user/:uid', async (req, res) =>{
    const {uid} = req.params;
    const user = await User.findOne({id: uid});

    if (!user){
        res.status(404).json({error: "user not found"});
    }
    try{
        res.status(200).json(user);
    }
    catch(err){
            if (err instanceof Error) {
                res.status(500).json({ error: err.message });
            } else {
                res.status(500).json({ error: 'An unknown error occurred' });
            }
        }
})

app.listen(PORT, ()=>{
    console.log(`Listening on port ${PORT}`)
})