import { model, Schema } from "npm:mongoose";

const userSchema = new Schema({
  // google uuid
  uuid: { type: String, required: true, unique: true},
  name: { type: String },
  netid: { type: String, unique: true},
  bio: { type: String, default: "" },
  wantedItems: [{ type: Schema.Types.ObjectId, ref: "Item" }],
  offeredItems: [{ type: Schema.Types.ObjectId, ref: "Item" }],
  friendList: [{ type: Schema.Types.ObjectId, ref: "User" }],
}, { timestamps: true });

export default model("User", userSchema);
