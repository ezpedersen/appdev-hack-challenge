// @deno-types="npm:@types/express"
import express from "npm:express";
import "jsr:@std/dotenv/load";
import mongoose from "npm:mongoose";
import usersRouter from "./routes/users.ts";
import listingsRouter from "./routes/listings.ts";
import { verifyAuth } from "./middleware/verifyAuth.ts";

// MongoDB setup
const MONGODB_URI = String(Deno.env.get("MONGODB_URI"));
await mongoose.connect(MONGODB_URI);

// Firebase setup


// App setup
const app = express();
const PORT = Number(Deno.env.get("PORT")) || 3000;
app.use(express.json());

app.get("/", (_req, res) => {
  res.status(200).send("Testing");
});

// Protected routes
app.use(verifyAuth);
app.use("/users", usersRouter);
app.use("/listings", listingsRouter);

app.listen(PORT, () => {
  console.log(`Listening on port ${PORT}`);
});
