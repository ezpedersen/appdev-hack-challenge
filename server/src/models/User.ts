import { model, Schema } from "npm:mongoose";

const userSchema = new Schema({
  // google uuid
  uuid: { type: String, required: true, unique: true },
  name: { type: String, required: true },
  imageUrl: { type: String, required: true },
  netid: { type: String, unique: true, required: true },
  bio: { type: String, default: "", required: true },
  asks: {
    type: [{ type: Schema.Types.ObjectId, ref: "Listing" }],
    required: true,
    default: [],
  },
  gives: {
    type: [{ type: Schema.Types.ObjectId, ref: "Listing" }],
    required: true,
    default: [],
  },
  friends: {
    type: [{ type: Schema.Types.ObjectId, ref: "User" }],
    required: true,
    default: [],
  },
  friendRequests: {
    type: [{ type: Schema.Types.ObjectId, ref: "FriendRequest" }],
    required: true,
    default: [],
  },
}, { timestamps: true });

export default model("User", userSchema);
