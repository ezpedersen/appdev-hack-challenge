// @deno-types="npm:@types/express"
import { Request, Response } from "npm:express";
import User from "../models/User.ts";
import Listing from "../models/Listing.ts";
import { AuthenticatedRequest } from "../types/AuthenticatedRequest.ts";

const getOtherAsks = async (req: AuthenticatedRequest, res: Response) => {
  try {
    const uuid = req?.uuid;

    const user = await User.findOne({ "uuid": uuid });
    const friends = user?.friends;

    const asks = [];
    if (friends){
      for (const friendId of friends) {
          const friend = await User.findById(friendId);
          if (friend && friend.asks) {
              asks.push(...friend.asks);
          }
      }
    }

    return res.status(200).json({asks: asks})
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: "Internal Server Error" });
  }
};

const getOtherGives = async(req: AuthenticatedRequest, res: Response) => {
  try {
    const uuid = req?.uuid;
    
    const user = await User.findOne({ "uuid": uuid });
    const friends = user?.friends;

    const asks = [];
    if (friends){
      for (const friendId of friends) {
          const friend = await User.findById(friendId); 
          if (friend && friend.asks) {
              asks.push(...friend.gives); 
          }
      }
    }

    return res.status(200).json({asks: asks})
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: "Internal Server Error" });
  }
}

const getAllListings = async (_req: AuthenticatedRequest, res: Response) => {
  try {
    const listings = await Listing.find();
    res.status(200).json(listings);
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: "Internal Server Error" });
  }
};

const getListingById = async (req: AuthenticatedRequest, res: Response) => {
  try {
    const { id } = req.params;

    const listing = await Listing.findById(id).populate("owner", "name netid");

    if (!listing) {
      return res.status(404).json({ error: "Listing not found!" });
    }

    return res.status(200).json(listing);
  } catch (_error) {
    return res.status(500).json({ error: "Something went wrong" });
  }
};

const getListingByNetId = async (req: Request, res: Response) => {
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
};

const createListing = async (req: AuthenticatedRequest, res: Response) => {
  try {
    const uuid = req?.uuid;

    const { name, description, type, owner, imageURL } = req.body;

    if (!name || !description || !type || !owner || !imageURL) {
      return res.status(400).json({ error: "Missing fields" });
    }

    const user = await User.findOne({ uuid: uuid});

    if (!user) {
      return res.status(400).json({
        error: "Cannot create listing for user that does not exist!",
      });
    }

    if ((type !== "ask") && (type !== "give")) {
      return res.status(400).json({
        error: "Type should be either ask or give.",
      });
    }

    const listing = new Listing({ name: name, description:description, type:type, owner: user?._id, imageURL: imageURL });
    await listing.save();
    if (type == "give") {
      user?.gives.push(listing._id);
      await user.save();
    } else if (type == "give") {
      user?.asks.push(listing._id);
      await user.save();
    }
    return res.status(201).json(listing);
  } catch (_error) {
    return res.status(500).json({
      error: "Something went wrong creating the listing.",
    });
  }
};

const deleteListing = async (req: AuthenticatedRequest, res: Response) => {
  try {
    const { id } = req.params;
    const uuid = req?.uuid;
    const listing = await Listing.findById(id);

    if (!listing) {
      return res.status(404).json({ error: "Listing not found." });
    }

    if (listing.acceptedBy) {
      return res.status(400).json({ error: "Cannot delete a listing that has already been accepted." });
    }
    const user = await User.findOne({ uuid });

    if (!user) {
      return res.status(404).json({ error: "User not found." });
    }

    if (!listing.owner.equals(user._id)) {
      return res.status(403).json({ error: "You are not authorized to delete this listing." });
    }

    await listing.deleteOne();

    // if (listing.type === "ask") {
    //   user.asks = user.asks.filter(
    //     (listingId) => !listingId.equals(listing._id)
    //   );
    // } else if (listing.type === "give") {
    //   user.gives = user.gives.filter(
    //     (listingId) => !listingId.equals(listing._id)
    //   );
    // }

    await user.save();

    return res.status(200).json({ deletedListing: listing});
  } catch (error) {
    console.error(error);
    return res.status(500).json({ error: "Something went wrong." });
  }
};

const updateListing = async (req: AuthenticatedRequest, res: Response) => {
  try {
    const { id } = req.params;
    const uuid = req?.uuid;
    const { name, description, type, imageURL } = req.body;

    const listing = await Listing.findById(id);

    if (!listing) {
      return res.status(404).json({ error: "Listing not found." });
    }

    if (listing.acceptedBy) {
      return res
        .status(400)
        .json({ error: "Cannot update a listing that has already been accepted." });
    }

    const user = await User.findOne({ uuid });

    if (!user) {
      return res.status(404).json({ error: "User not found." });
    }

    if (!listing.owner.equals(user._id)) {
      return res
        .status(403)
        .json({ error: "You are not authorized to update this listing." });
    }

    if (type && type !== "ask" && type !== "give") {
      return res
        .status(400)
        .json({ error: "Type must be either 'ask' or 'give'." });
    }

    if (name) listing.name = name;
    if (description) listing.description = description;
    if (type) listing.type = type;
    if (imageURL) listing.imageUrl = imageURL;

    await listing.save();

    return res.status(200).json({ message: "Listing updated successfully.", listing });
  } catch (error) {
    console.error(error);
    return res.status(500).json({ error: "Something went wrong." });
  }
};


const acceptListing = async (req: AuthenticatedRequest, res: Response) => {
  try {
    const { id } = req.params;
    const uuid = req?.uuid;

    const listing = await Listing.findById(id);

    if (!listing) {
      return res.status(404).json({ error: "Listing not found." });
    }

    if (listing.acceptedBy) {
      return res.status(400).json({ error: "Listing has already been accepted." });
    }

    const user = await User.findOne({ uuid });

    if (!user) {
      return res.status(404).json({ error: "User not found." });
    }

    listing.acceptedBy = user._id;

    await listing.save();

    return res.status(200).json({ listing });
  } catch (error) {
    console.error(error);
    return res.status(500).json({ error: "Something went wrong." });
  }
};

export {
  getOtherAsks,
  getOtherGives,
  getAllListings,
  getListingById,
  getListingByNetId,
  createListing,
  acceptListing,
  deleteListing,
  updateListing,
};
