// @deno-types="npm:@types/express"
import express, { NextFunction, Request, Response } from "npm:express";
import "jsr:@std/dotenv/load";
import mongoose from "npm:mongoose";
import User from "../model/User.ts"
import Listing from "../model/Listing.ts"


const app = express();
const PORT = Number(Deno.env.get("PORT")) || 3000;
const MONGODB_URI = String(Deno.env.get("MONGODB_URI"));

app.use(express.json()); 
await mongoose.connect(MONGODB_URI);

app.get("/", (_req, res) => {
    res.status(200).send("Testing")
})

//get user by id
app.get("/users/:netid", async (req, res) => {
  const netid = req.params.netid;
  try{
    const user = await User.findOne({netid});
 
 
    if (!user) return res.status(404).json({error: "user not found"});
 
 
    res.status(200).json(user)
  }
  catch (error) {
    console.error(error);
    res.status(500).json({ error: "internal Server Error" });
  }
 })
 
//get all users
app.get('/users/', async (req,res)=>{
  try{
    const users = await User.find()

    if (users){
      res.status(200).json(users);
    }
  }catch(error){
    res.status(500).json({error:"Something went wrong."})
  }
});


//create a user
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

//delete a user
app.delete("/users/:netid/", async (req, res) => {
  try {
    const netid = req.params.netid;

    const deletedUser = await User.findOneAndDelete({ netid: netid });

    if (!deletedUser) {
      return res.status(404).json({ error: "User not found" }); 
    }

    res.status(200).json({
      message: "User deleted successfully",
      user: deletedUser,
    });
  } catch (error) {
    console.error(error);
    return res.status(500).json({ error: "Internal Server Error" });
  }
});

//edit user profile
app.put("/users/profile/:netid", async (req, res) => {
  const netid  = req.params.netid;
  const { name, bio } = req.body;
 
 
  if (!name && !bio){
    return res.status(400).json({ error: "Please provide name or bio" });
  }
 
 
  try {
    const user = await User.findOne({netid});
    if (!user) {
      return res.status(404).json({ error: "User not found" });
    }
 
 
    if (name) {
      user.name = name;}
    if (bio){
      user.bio = bio;}
    await user.save();
 
 
    res.status(200).json({user});
 
 
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: "Internal Server Error" });
  }
 });
 
 
 //deletes a friend
 app.delete("/users/friend/:netid", async (req, res) => {
  const netid = req.params.netid;
  const {idToRemove } = req.body;
 
 
  if (!idToRemove){
    return res.status(400).json({ error: "Please provide netid to remove" });
  }
 
 
  try {
    const user = await User.findOne({ netid });
    if (!user) {
      return res.status(404).json({ error: "User not found" });
    }
 
 
    const friendIndex = user.friendList.findIndex(idToRemove);
 
 
    if (friendIndex === -1) {
      return res.status(404).json({ error: "Friend not found" });
    }
 
 
    user.friendList.splice(friendIndex, 1);
    await user.save();
 
 
    res.status(200).json(user);
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: "Internal Server Error" });
  }
 });
 
 
 //adds a friend
 app.post("/users/friend/:netid", async (req, res) =>{
  const netid = req.params.netid;
  const {idToAdd} = req.body;
 
 
  if (!idToAdd){
    return res.status(400).json({ error: "Please provide netid to add" });
  }
 
 
  try {
    const user = await User.findOne({netid});
    if (!user){
      return res.status(404).json({error: "User not found"})}
   
   
    if (user.friendList.includes(idToAdd.toString())) return res.status(404).json({error: "Friend already added!"})
   
    user.friendList.push(idToAdd);
    await user.save();
    res.status(200).json(user);
  }
  catch(error){
    console.error(error);
    res.status(500).json({error: "Internal Server Error"});
  }
 })
 
  //get all listsings 
app.get("/listings/", async (req, res) => {
  try{
    const listings = await Listing.find();
    res.status(200).json(listings);
  }
  catch (error){
    console.error(error);
    res.status(500).json({error: "Internal Server Error"});
  }
 })
 
app.get("/listing/:id/", async(req,res)=>{
  try{
    const {id} = req.params;
    
    const listing = await Listing.findById(id).populate('owner','name netid');

    if (!listing){
      return res.status(404).json({error:"Listing not found"})
    }

    return res.status(200).json(listing);

  }catch(_error){
    return res.status(500).json({error:"Something went wrong."})
  }
});

app.get("/listings/:netid/", async(req,res)=>{
  try{
    const {netid} = req.params;

    const user = await User.findOne({netid})
    if (!user){
      return res.status(404).json({error:"User not found!"})
    }

    const listings = await Listing.find({owner:user._id}).populate('owner','name netid')
    if (!listings){
      return res.status(400).json({error:"Cannot find listing for user"})
    }

    return res.status(200).json(listings)

  }
  catch(_error){
    res.status(500).json({error:"Something went wrong"})
  }

})

app.post("/listings/", async(req,res)=>{
  try{
    const {name, description, type, owner} = req.body;
    
    if (!name || !description || !type || !owner){
      return res.status(400).json({error: "Missing fields"})
    };

    const user = await User.findOne({netid:owner})

    if (!user){
      return res.status(400).json({error: "Cannot create listing for user that does not exist!"})
    }

    if ((type !== "offering") && (type !== "request")){
      return res.status(400).json({error:"Type should be either offering or request."})
    }

    const listing = new Listing({name, description, type, owner:user._id});
    await listing.save()
    if (type == "offering"){
      user?.offeredItems.push(listing._id);
      await user.save();
    }else if (type == "request"){
      user?.wantedItems.push(listing._id);
      await user.save();
    }
    return res.status(201).json(listing)
  }catch(_error){
    return res.status(500).json({error:'Something went wrong creating the listing.'})
  }
});

app.post("/listing/:id/accept/", async(req,res)=>{
  try{
    const {id} = req.params;
    const {netid} = req.body;
  
    const requestor = await User.findOne({netid});

    if (!requestor){
      return res.status(404).json({error:"Requestor not found."})
    }

    const listing = await Listing.findById(id).populate('owner','netid bio');

    if (!listing){
      return res.status(404).json({error: "Listing not found."})
    }

    requestor.wantedItems.push(listing._id);
    await requestor.save();

    return res.status(200).json(requestor)
  }catch(_error){
    return res.status(500).json({error:"Something went wrong."})
  }

});

app.delete("/listing/:id/", async (req, res) => {
  try {
    const { id } = req.params;

    // Step 1: Find the listing by ID
    const listing = await Listing.findById(id);
    if (!listing) {
      return res.status(404).json({ error: "Listing not found!" });
    }

    // Step 2: Remove the listing from the associated user's `offeredItems` or `wantedItems`
    const user = await User.findById(listing.owner);
    if (user) {
      // Remove the listing from either `offeredItems` or `wantedItems`
      user.offeredItems = user.offeredItems.filter((item) => !item.equals(listing._id));
      user.wantedItems = user.wantedItems.filter((item) => !item.equals(listing._id));
      await user.save();
    }

    // Step 3: Delete the listing itself
    await listing.deleteOne();

    // Step 4: Respond with success
    res.status(200).json({ message: "Listing deleted successfully!", deletedListing: listing });
  } catch (error) {
    console.error("Error deleting listing:", error);
    res.status(500).json({ error: "Something went wrong while deleting the listing." });
  }
});




app.listen(PORT, ()=>{
    console.log(`Listening on port ${PORT}`)
})