import {model, Schema} from "npm:mongoose"

const listingSchema = new Schema({
   name: {type: String},
   createdAt: {type: Date, default: Date.now},
   type: {type: String}, // Enum offering or request
   // add references to two users: one who will accept the item and one who will give the item
})

export default model("Listing", listingSchema);