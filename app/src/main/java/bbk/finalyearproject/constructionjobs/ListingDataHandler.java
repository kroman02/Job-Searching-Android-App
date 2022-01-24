package bbk.finalyearproject.constructionjobs;

import java.util.List;

public interface ListingDataHandler {
    public boolean addListing(Listing listing);
    public List<Listing> getAllListings();
}
