// @deno-types="npm:@types/express"
import express from "npm:express";
import {
  addFriend,
  createUser,
  deleteFriend,
  deleteUser,
  getAllUsers,
  getUser,
  updateUser,
} from "../controllers/userController.ts";

const router = express.Router();

router.route("/:netid")
  .get(getUser)
  

router.route("/friend/:netid")
  .delete(deleteFriend)
  .post(addFriend);

router.route("/")
  .get(getAllUsers)
  .post(createUser)
  .delete(deleteUser)
  .put(updateUser)

export default router;
