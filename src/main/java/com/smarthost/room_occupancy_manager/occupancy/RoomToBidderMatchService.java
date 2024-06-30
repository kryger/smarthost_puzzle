package com.smarthost.room_occupancy_manager.occupancy;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RoomToBidderMatchService {
    // amount from which client is considered "PREMIUM", inclusive.
    // In cents to avoid rounding errors during calculations
    private static final Integer PREMIUM_CLIENT_CUTOFF = 100_00;

    /**
     * @param bidsList
     * @param freePremiumRooms
     * @param freeEconomyRooms
     * @return
     */
    public Map<RoomType, RoomsOffer> matchRoomsToBidders(List<Integer> bidsList, Integer freePremiumRooms, Integer freeEconomyRooms) {
        // divide bids into two groups to classify clients as "ECONOMY" or "PREMIUM"
        // at the same sort descending to group highest-paying customers first
        Map<Boolean, List<Integer>> bidsPartitionedByClientClassDescending = bidsList.stream().sorted((o1, o2) -> o2-o1).collect(Collectors.partitioningBy(bid -> bid >= PREMIUM_CLIENT_CUTOFF));
        List<Integer> premiumClients = bidsPartitionedByClientClassDescending.get(Boolean.TRUE);
        List<Integer> economyClients = bidsPartitionedByClientClassDescending.get(Boolean.FALSE);

        // allocate as many premium clients as possible
        // allocate as many economy clients as possible
        // if there are premium rooms and economy client left, allocate as many as possible


        // mock response to satisfy the return type
        HashMap<RoomType, RoomsOffer> result = new HashMap<>();
        result.put(RoomType.ECONOMY, new RoomsOffer(freeEconomyRooms,freeEconomyRooms * 3));
        result.put(RoomType.PREMIUM, new RoomsOffer(freePremiumRooms, freePremiumRooms * 5));
        return result;
    }
}
