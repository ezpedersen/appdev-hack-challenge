// @deno-types="npm:@types/express"
import { Response } from "npm:express";
import User from "../models/User.ts";
import { AuthenticatedRequest } from "../types/AuthenticatedRequest.ts";

const createUser = async (req: AuthenticatedRequest, res: Response) => {
  const uuid = req!.uuid;
  try {
    const { name, netid, bio = "", imageUrl } = req.body;
    const newUser = new User({
      uuid,
      name,
      imageUrl,
      netid,
      bio,
    });

    const _savedUser = await newUser.save();

    res.status(201).json({
      message: "New user created",
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

const getUser = async (req: AuthenticatedRequest, res: Response) => {
  const netid = req.params.netid;

  try {
    const user = await User.findOne({ netid })
      .select("name imageURL netid bio asks gives friends")
      .populate("asks gives friends");

    if (!user) return res.status(404).json({ error: "user not found" });

    res.status(200).json(user);
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: "internal Server Error" });
  }
};

const getUUIDUser = async (req: AuthenticatedRequest, res: Response) => {
  const uuid = req.params.uuid;

  try {
    const user = await User.findOne({ uuid })
      .select("name imageURL netid bio asks gives friends")
      .populate("asks gives friends");

    if (!user) return res.status(404).json({ error: "user not found" });
    res.status(200).json(user);
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: "internal Server Error" });
  }
};

const deleteUser = async (req: AuthenticatedRequest, res: Response) => {
  try {
    const uuid = req?.uuid;

    const deletedUser = await User.findOneAndDelete({ uuid: uuid });

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

const updateUser = async (req: AuthenticatedRequest, res: Response) => {
  const uuid = req?.uuid;
  const { bio } = req.body;

  if (!bio) {
    return res.status(400).json({ error: "Please provide bio" });
  }

  try {
    const user = await User.findOne({ uuid });
    if (!user) {
      return res.status(404).json({ error: "User not found" });
    }

    user.bio = bio;

    await user.save();

    res.status(200).json({ user });
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: "Internal Server Error" });
  }
};

/*const deleteFriend = async (req: AuthenticatedRequest, res: Response) => {
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

    const friendIndex = user.friends.findIndex(idToRemove);

    if (friendIndex === -1) {
      return res.status(404).json({ error: "Friend not found" });
    }

    user.friends.splice(friendIndex, 1);
    await user.save();

    res.status(200).json(user);
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: "Internal Server Error" });
  }
};
const addFriend = async (req: AuthenticatedRequest, res: Response) => {
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

    if (user.friends.includes(idToAdd.toString())) {
      return res.status(404).json({ error: "Friend already added!" });
    }

    user.friends.push(idToAdd);
    await user.save();
    res.status(200).json(user);
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: "Internal Server Error" });
  }
};

const getAllUsers = async (_req: AuthenticatedRequest, res: Response) => {
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

export {
  addFriend,
  getUUIDUser,
  createUser,
  deleteFriend,
  deleteUser,
  getAllUsers,
  getUser,
  updateUser,
};*/

export { createUser, deleteUser, getUser, getUUIDUser, updateUser };
