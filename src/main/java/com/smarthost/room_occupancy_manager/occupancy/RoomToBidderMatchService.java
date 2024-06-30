package com.smarthost.room_occupancy_manager.occupancy;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoomToBidderMatchService {
    /**
     * @param bidsList
     * @param freePremiumRooms
     * @param freeEconomyRooms
     * @return
     */
    public Map<RoomType, RoomsOffer> matchRoomsToBidders(List<Integer> bidsList, Integer freePremiumRooms, Integer freeEconomyRooms) {
        HashMap<RoomType, RoomsOffer> result = new HashMap<>();
        result.put(RoomType.ECONOMY, new RoomsOffer(freeEconomyRooms,freeEconomyRooms * 3));
        result.put(RoomType.PREMIUM, new RoomsOffer(freePremiumRooms, freePremiumRooms * 5));
        return result;
    }
}
