import {model, Schema} from "npm:mongoose"

const userSchema = new Schema({
   name: {type: String, required: true},
   netid: {type: String, unique: true, required:true},
   // profile picture
   bio: {type: String, default:""},
   wantedItems: [{type: Schema.Types.ObjectId, ref: "Item"}],
   offeredItems: [{type: Schema.Types.ObjectId, ref: "Item"}],
   friendList: [{type: Schema.Types.ObjectId, ref: "User"}]
},
{timestamps: true});

export default model("User", userSchema);