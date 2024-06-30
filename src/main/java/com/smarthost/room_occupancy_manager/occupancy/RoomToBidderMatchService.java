package com.smarthost.room_occupancy_manager.occupancy;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RoomToBidderMatchService {
    // amount from which client is considered "PREMIUM", inclusive.
    // In cents to avoid rounding errors during calculations
    private static final Integer PREMIUM_CLIENT_CUTOFF = 100_00;

    /**
     * @param bidsList list of bids per each client (note: passed in as eurocents to avoid rounding errors)
     * @param freePremiumRooms number of premium rooms available
     * @param freeEconomyRooms number of economy rooms available
     * @return map of number of taken rooms and total predicted income for each room type (economy/premium)
     */
    public Map<RoomType, RoomsOffer> matchRoomsToBidders(List<Integer> bidsList, Integer freePremiumRooms, Integer freeEconomyRooms) {
        // divide bids into two groups to classify clients as "ECONOMY" or "PREMIUM"
        // at the same sort descending to group highest-paying customers first
        Map<Boolean, List<Integer>> bidsPartitionedByClientClassDescending = bidsList.stream().sorted((o1, o2) -> o2 - o1).collect(Collectors.partitioningBy(bid -> bid >= PREMIUM_CLIENT_CUTOFF));
        List<Integer> premiumClientsBids = bidsPartitionedByClientClassDescending.get(Boolean.TRUE);
        List<Integer> economyClientsBids = bidsPartitionedByClientClassDescending.get(Boolean.FALSE);

        List<Integer> allocatedPremiumBids = new ArrayList<>();
        List<Integer> allocatedEconomyBids = new ArrayList<>();
        int takenPremiumRooms = 0;
        int takenEconomyRooms = 0;

        // allocate as many premium clients as possible
        // boolean premiumRoomsLeft = false;
        if (freePremiumRooms > premiumClientsBids.size()) {
            // all premium clients will get their rooms and at least one economy client will be offered a premium room too
            freePremiumRooms = freePremiumRooms - premiumClientsBids.size();
            allocatedPremiumBids.addAll(premiumClientsBids);
            takenPremiumRooms += premiumClientsBids.size();
        } else {
            // only premium clients get allocated into premium rooms
            allocatedPremiumBids.addAll(premiumClientsBids.stream().limit(freePremiumRooms).toList());
            takenPremiumRooms += allocatedPremiumBids.size();
            freePremiumRooms = 0;
        }

        // if there are more economy clients than economy rooms and premium rooms are still left over, allocate highest bidders into premium
        // and only then allocate the rest into economy rooms
        if ((economyClientsBids.size() > freeEconomyRooms) && freePremiumRooms > 0) {
            // allocate highest paying economy clients first, up until freePremiumRooms are taken
            allocatedPremiumBids.addAll(economyClientsBids.stream().limit(freePremiumRooms).toList());
            List<Integer> remainingEconomyClients = economyClientsBids.subList(freePremiumRooms, economyClientsBids.size());
            takenPremiumRooms += freePremiumRooms;

            // allocate the remainder into economy rooms, up to the availability level
            allocatedEconomyBids.addAll(remainingEconomyClients.stream().limit(freeEconomyRooms).toList());
            takenEconomyRooms += freeEconomyRooms;
        } else {
            // otherwise only allocate as many economy into economy rooms as possible
            allocatedEconomyBids.addAll(economyClientsBids.stream().limit(freeEconomyRooms).toList());
            takenEconomyRooms += allocatedEconomyBids.size();
        }

        Map<RoomType, RoomsOffer> result = new HashMap<>();
        result.put(RoomType.ECONOMY, new RoomsOffer(takenEconomyRooms,allocatedEconomyBids.stream().collect(Collectors.summingInt(value -> value))));
        result.put(RoomType.PREMIUM, new RoomsOffer(takenPremiumRooms,allocatedPremiumBids.stream().collect(Collectors.summingInt(value -> value))));
        return result;
    }
}
