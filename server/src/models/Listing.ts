import { model, Schema } from "npm:mongoose";

const listingSchema = new Schema(
  {
    name: { type: String, required: true },
    createdAt: { type: Date, default: Date.now },
    description: { type: String, required: true },
    type: {
      type: String,
      enum: ["offering", "request"],
      required: true,
    },
    owner: { type: Schema.Types.ObjectId, ref: "User", required: true },
    acceptedBy: { type: Schema.Types.ObjectId, ref: "User" },
  },
);

export default model("Listing", listingSchema);
