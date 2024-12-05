// @deno-types="npm:@types/express"
import { Request, Response } from "npm:express";
import User from "../models/User.ts";

const getUser = async (req: Request, res: Response) => {
  const netid = req.params.netid;
  try {
    const user = await User.findOne({ netid });

    if (!user) return res.status(404).json({ error: "user not found" });

    res.status(200).json(user);
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: "internal Server Error" });
  }
};
const deleteUser = async (req: Request, res: Response) => {
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
};
const updateUser = async (req: Request, res: Response) => {
  const netid = req.params.netid;
  const { name, bio } = req.body;

  if (!name && !bio) {
    return res.status(400).json({ error: "Please provide name or bio" });
  }

  try {
    const user = await User.findOne({ netid });
    if (!user) {
      return res.status(404).json({ error: "User not found" });
    }

    if (name) {
      user.name = name;
    }
    if (bio) {
      user.bio = bio;
    }
    await user.save();

    res.status(200).json({ user });
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: "Internal Server Error" });
  }
};

const deleteFriend = async (req: Request, res: Response) => {
  const netid = req.params.netid;
  const { idToRemove } = req.body;

  if (!idToRemove) {
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
};
const addFriend = async (req: Request, res: Response) => {
  const netid = req.params.netid;
  const { idToAdd } = req.body;

  if (!idToAdd) {
    return res.status(400).json({ error: "Please provide netid to add" });
  }

  try {
    const user = await User.findOne({ netid });
    if (!user) {
      return res.status(404).json({ error: "User not found" });
    }

    if (user.friendList.includes(idToAdd.toString())) {
      return res.status(404).json({ error: "Friend already added!" });
    }

    user.friendList.push(idToAdd);
    await user.save();
    res.status(200).json(user);
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: "Internal Server Error" });
  }
};

const getAllUsers = async (_req: Request, res: Response) => {
  try {
    const users = await User.find();

    if (users) {
      res.status(200).json(users);
    }
  } catch (error) {
    console.log(error);
    res.status(500).json({ error: "Something went wrong." });
  }
};
const createUser = async (req: Request, res: Response) => {
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
};
export {
  addFriend,
  createUser,
  deleteFriend,
  deleteUser,
  getAllUsers,
  getUser,
  updateUser,
};
