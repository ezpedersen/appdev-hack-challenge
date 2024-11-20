import express from 'express' 
import dotenv from "dotenv";

const app = express();
dotenv.config();

const PORT = 8080;

app.listen(PORT, ()=>{
    console.log(`Listening on port ${PORT}`)
})