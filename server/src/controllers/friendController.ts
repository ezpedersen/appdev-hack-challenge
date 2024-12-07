// @deno-types="npm:@types/express"
import { Request, Response } from "npm:express";
import User from "../models/User.ts";
import Listing from "../models/Listing.ts";
import FriendRequest from "../models/FriendRequest.ts";
import { AuthenticatedRequest } from "../types/AuthenticatedRequest.ts";

const respondToFriendRequest = async (
  req: AuthenticatedRequest,
  res: Response,
) => {
  const id = req.params.id;
  const uuid = req!.uuid;
  const response = !!req.body.accept;
  try {
    const friendRequest = await FriendRequest.findById(id);
    const receiverId = friendRequest!.receiver;
    const receiver = await User.findById(receiverId);
    if (receiver?.uuid == uuid) {
      if (response == true) {
        const senderId = friendRequest?.sender;
        const sender = await User.findById(senderId);
        sender?.friends.push(receiverId);
        sender?.save();
      }
      FriendRequest.deleteOne({ _id: id });

      res.sendStatus(200);
    } else {
      res.sendStatus(403); // Forbidden
    }
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: "Internal Server Error" });
  }
};

const getFriendRequests = async (req: AuthenticatedRequest, res: Response) => {
  const uuid = req!.uuid;
  try {
    const user = await User.findOne({ uuid });
    if (!user) {
      return res.sendStatus(404);
    }
    const friendRequests = [];
    for (const fqId of user.friendRequests) {
      const friendRequest = FriendRequest.findById(fqId);
      friendRequests.push(friendRequest);
    }
    res.json(friendRequests);
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: "Internal Server Error" });
  }
};
const createFriendRequest = async (
  req: AuthenticatedRequest,
  res: Response,
) => {
  const uuid = req!.uuid;
  const { netid } = req.body;
  try {
    const user = await User.findOne({ uuid });
    if (!user) {
      return res.sendStatus(404);
    }
    const newFriendRequest = new FriendRequest({
      sender: user.netid,
      receiver: netid,
    });
    await newFriendRequest.save();
    res.sendStatus(200);
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: "Internal Server Error" });
  }
};
const deleteFriend = async (req: AuthenticatedRequest, res: Response) => {
  const uuid = req!.uuid;
  try {
    const netid = req.body.netid;
    const friend = await User.findOne({netid});
    const user = await User.findOne({uuid})
    if(!friend || !user){
        return res.sendStatus(404)
    }
    if(!user){
        return res.sendStatus(404)
    }
    const friends = user.friends;
    user.friends = friends.filter(friendId => friendId != friend._id)
    await user.save();
  
    res.status(200).json({
      message: "Friend deleted successfully",
    });
    
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: "Internal Server Error" });
  }
};

export {
  createFriendRequest,
  deleteFriend,
  getFriendRequests,
  respondToFriendRequest,
};
