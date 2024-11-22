// @deno-types="npm:@types/express"
import express, { NextFunction, Request, Response } from "npm:express";
import "jsr:@std/dotenv/load";
import mongoose from "npm:mongoose";
import User from "../model/User.ts"

const app = express();
const PORT = Number(Deno.env.get("PORT")) || 3000;
const MONGODB_URI = String(Deno.env.get("MONGODB_URI"));

app.use(express.json());

await mongoose.connect(MONGODB_URI);

app.get("/", (_req, res) => {
    res.status(200).send("Testing")
})

app.post("/users", async (req,res)=>{
    try{
        const {name, netid, bio=""} = req.body;
    
        const newUser = new User({
            name,
            netid,
            bio,
        })
    
        const savedUser = await newUser.save();
    
        res.status(201).json({
            message:"New user created",
            user: savedUser,
        })
    }catch (error) {
        // Handle errors (e.g., validation errors or duplicate netid)
        if (error.code === 11000) {
          res.status(400).json({ error: "NetID already exists" });
        } else if (error.name === "ValidationError") {
          res.status(400).json({ error: error.message });
        } else {
          res.status(500).json({ error: "Internal Server Error" });
        }
      }
});




app.listen(PORT, ()=>{
    console.log(`Listening on port ${PORT}`)
})