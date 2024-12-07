// @deno-types="npm:@types/express"
import express from "npm:express";
import { createFriendRequest, deleteFriend, getFriendRequests, respondToFriendRequest } from "../controllers/friendController.ts";

// /friendreq
// 	GET (/) -> get all friend requests
// 	POST (/) [netid] -> new friend request
// 	PUT (/:id) [accept: true/false] -> accept or decline friend request
// 	DELETE (/) [netid] -> deleted friend user
const router = express.Router();

router.route("/:netid")
    .put(respondToFriendRequest)
router.route("/")
  .get(getFriendRequests)
  .post(createFriendRequest)
  .delete(deleteFriend)

export default router;
