import {model, Schema} from "npm:mongoose"

const userSchema = new Schema({
   name: {type: String},
   netid: {type: String, unique: true},
   // profile picture
   bio: {type: String},
   createdAt: {type: Date, default: Date.now},
})

export default model("User", userSchema);