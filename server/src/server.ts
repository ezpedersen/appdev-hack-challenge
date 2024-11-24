// @deno-types="npm:@types/express"
import express, { NextFunction, Request, Response } from "npm:express";
import "jsr:@std/dotenv/load";
import mongoose from "npm:mongoose";
import User from "../model/User.ts"
import Listing from "../model/Listing.ts"

const app = express();
const PORT = Number(Deno.env.get("PORT")) || 3000;
const MONGODB_URI = String(Deno.env.get("MONGODB_URI"));

app.use(express.json({ limit: "1mb" })); 
await mongoose.connect(MONGODB_URI);

app.get("/", (_req, res) => {
    res.status(200).send("Testing")
})


//get specific user by id
app.get('/user/:netid', async (req, res) =>{
    const {netid} = req.params;
    const user = await User.findOne({id: netid});

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

app.get("/users/", async (req, res) => {
    try {
      const users = await User.find();
  
      res.status(200).json({
        message: "Users retrieved successfully",
        users: users,
      });
    } catch (error) {
      console.error(error);
      res.status(500).json({ error: "Internal Server Error" });
    }
  });
  

  app.post("/users/", async (req, res) => {
    try {
      const { name, netid, bio = "" } = req.body;
  
      const newUser = new User({
        name,
        netid,
        bio,
      });
  
      const savedUser = await newUser.save();
  
      res.status(201).json({
        message: "New user created",
        user: savedUser,
      });
    } catch (error) {
      if (error instanceof Error && "code" in error && error.code === 11000) {
        res.status(400).json({ error: "NetID already exists" });
      } else if (error instanceof Error && error.name === "ValidationError") {
        res.status(400).json({ error: error.message });
      } else {
        res.status(500).json({ error: "Internal Server Error" });
      }
    }
  });
  

app.delete("/users/:id", async (req, res) => {
    try {
      const userId = req.params.id;
      const deletedUser = await User.findByIdAndDelete(userId);
  
      if (!deletedUser) {
        return res.status(404).json({ error: "User not found" });
      }
  
      res.status(200).json({
        message: "User deleted successfully",
        user: deletedUser,
      });
    } catch (error) {
      console.error(error);
      res.status(500).json({ error: "Internal Server Error" });
    }
  });
  

app.listen(PORT, ()=>{
    console.log(`Listening on port ${PORT}`)
})