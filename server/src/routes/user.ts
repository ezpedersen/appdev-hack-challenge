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
  .delete(deleteUser)
  .put(updateUser);

router.route("/friend/:netid")
  .delete(deleteFriend)
  .post(addFriend);

router.route("/")
  .get(getAllUsers)
  .post(createUser);

export default router;
