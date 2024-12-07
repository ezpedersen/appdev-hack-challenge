// @deno-types="npm:@types/express"
import express from "npm:express";
import {
  getUUIDUser,
  createUser,
  deleteUser,
  getUser,
  updateUser,
} from "../controllers/userController.ts";

const router = express.Router();

router.route("/:netid")
  .get(getUser)

router.route("/uuid/:uuid")
  .get(getUUIDUser)

router.route("/")
  .post(createUser)
  .delete(deleteUser)
  .put(updateUser)

// router.route("/:netid")
//   .get(getUser)
  

// router.route("/friend/:netid")
//   .delete(deleteFriend)
//   .post(addFriend);

// router.route("/")
//   .get(getAllUsers)
//   .post(createUser)
//   .delete(deleteUser)
//   .put(updateUser)

export default router;
