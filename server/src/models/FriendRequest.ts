import { model, Schema } from "npm:mongoose";

const friendRequestSchema = new Schema(
  {
    sender: { type: Schema.Types.ObjectId, ref: "User", required: true },
    receiver: { type: Schema.Types.ObjectId, ref: "User", required: true},
    createdAt: { type: Date, default: Date.now },
    respondedTo: {type: Boolean, default: false}
  },
);

export default model("FriendRequest", friendRequestSchema);
