// @deno-types="npm:@types/express"
import express from "npm:express";
import User from "../models/User.ts";
import Listing from "../models/Listing.ts";

const router = express.Router();

//get all listings
router.get("/", async (_req, res) => {
  try {
    const listings = await Listing.find();
    res.status(200).json(listings);
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: "Internal Server Error" });
  }
});

router.get("/:id/", async (req, res) => {
  try {
    const { id } = req.params;

    const listing = await Listing.findById(id).populate("owner", "name netid");

    if (!listing) {
      return res.status(404).json({ error: "Listing not found" });
    }

    return res.status(200).json(listing);
  } catch (_error) {
    return res.status(500).json({ error: "Something went wrong." });
  }
});

router.get("/:netid/", async (req, res) => {
  try {
    const { netid } = req.params;

    const user = await User.findOne({ netid });
    if (!user) {
      return res.status(404).json({ error: "User not found!" });
    }

    const listings = await Listing.find({ owner: user._id }).populate(
      "owner",
      "name netid",
    );
    if (!listings) {
      return res.status(400).json({ error: "Cannot find listing for user" });
    }

    return res.status(200).json(listings);
  } catch (_error) {
    res.status(500).json({ error: "Something went wrong" });
  }
});

router.post("/", async (req, res) => {
  try {
    const { name, description, type, owner } = req.body;

    if (!name || !description || !type || !owner) {
      return res.status(400).json({ error: "Missing fields" });
    }

    const user = await User.findOne({ netid: owner });

    if (!user) {
      return res.status(400).json({
        error: "Cannot create listing for user that does not exist!",
      });
    }

    if ((type !== "offering") && (type !== "request")) {
      return res.status(400).json({
        error: "Type should be either offering or request.",
      });
    }

    const listing = new Listing({ name, description, type, owner: user._id });
    await listing.save();
    if (type == "offering") {
      user?.offeredItems.push(listing._id);
      await user.save();
    } else if (type == "request") {
      user?.wantedItems.push(listing._id);
      await user.save();
    }
    return res.status(201).json(listing);
  } catch (_error) {
    return res.status(500).json({
      error: "Something went wrong creating the listing.",
    });
  }
});

router.post("/:id/accept/", async (req, res) => {
  try {
    const { id } = req.params;
    const { netid } = req.body;

    const requestor = await User.findOne({ netid });

    if (!requestor) {
      return res.status(404).json({ error: "Requestor not found." });
    }

    const listing = await Listing.findById(id).populate("owner", "netid bio");

    if (!listing) {
      return res.status(404).json({ error: "Listing not found." });
    }

    requestor.wantedItems.push(listing._id);
    await requestor.save();

    return res.status(200).json(requestor);
  } catch (_error) {
    return res.status(500).json({ error: "Something went wrong." });
  }
});

router.delete("/:id/", async (req, res) => {
  try {
    const { id } = req.params;

    // Step 1: Find the listing by ID
    const listing = await Listing.findById(id);
    if (!listing) {
      return res.status(404).json({ error: "Listing not found!" });
    }

    // Step 2: Remove the listing from the associated user's `offeredItems` or `wantedItems`
    const user = await User.findById(listing.owner);
    if (user) {
      // Remove the listing from either `offeredItems` or `wantedItems`
      user.offeredItems = user.offeredItems.filter((item) =>
        !item.equals(listing._id)
      );
      user.wantedItems = user.wantedItems.filter((item) =>
        !item.equals(listing._id)
      );
      await user.save();
    }

    // Step 3: Delete the listing itself
    await listing.deleteOne();

    // Step 4: Respond with success
    res.status(200).json({
      message: "Listing deleted successfully!",
      deletedListing: listing,
    });
  } catch (error) {
    console.error("Error deleting listing:", error);
    res.status(500).json({
      error: "Something went wrong while deleting the listing.",
    });
  }
});

export default router;
