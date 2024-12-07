// @deno-types="npm:@types/express"
import express from "npm:express";
import {
  acceptListing,
  createListing,
  deleteListing,
  getAllListings,
  getListingById,
  getListingByNetId,
  getOtherAsks,
  getOtherGives,
  updateListing,
} from "../controllers/listingController.ts";

const router = express.Router();

router.get("/listing/asks/", getOtherAsks);

router.get("/listing/gives/", getOtherGives);

router.get("/listing/:id/", getListingById);

router.post("/listing/", createListing);

router.put("/listing/:id/", updateListing);

router.put("/listing/:id/", acceptListing);

router.delete("/listing/:id", deleteListing);

// router.get("/", getAllListings);

// router.get("/:id/", getListing);

// router.get("/:netid/", getListingByNetId);

// router.post("/", createListing);

// router.put("/:id/accept/", acceptListing);

// router.delete("/:id/", deleteListing);

export default router;
