// @deno-types="npm:@types/express"
import express from "npm:express";
import { acceptListing, createListing, deleteListing, getAllListings, getListing, getListingByNetId } from "../controllers/listingController.ts";

const router = express.Router();

//get all listings
router.get("/", getAllListings);

router.get("/:id/", getListing);

router.get("/:netid/", getListingByNetId);

router.post("/", createListing);

router.put("/:id/accept/", acceptListing);

router.delete("/:id/", deleteListing);

export default router;
