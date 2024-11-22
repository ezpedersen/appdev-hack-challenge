import {model, Schema} from "npm:mongoose"

const userSchema = new Schema({
   name: {type: String, required: true},
   netid: {type: String, unique: true, required:true},
   // profile picture
   bio: {type: String, default:""},
   borrowedItems: [{type: Schema.Types.ObjectId, ref: "Item"}],
   lentItems: [{type: Schema.Types.ObjectId, ref: "Item"}]
},
{timestamps: true});

export default model("User", userSchema);