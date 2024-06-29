package com.smarthost.room_occupancy_manager.occupancy;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoomToBidderMatchService {
    public Map<RoomType, RoomsOffer> matchRoomsToBiggers(List<Integer> bidsList, Integer freeEconomyRooms, Integer freePremiumRooms) {
        HashMap<RoomType, RoomsOffer> result = new HashMap<>();
        result.put(RoomType.ECONOMY, new RoomsOffer(freeEconomyRooms,freeEconomyRooms * 3));
        result.put(RoomType.PREMIUM, new RoomsOffer(freePremiumRooms, freePremiumRooms * 5));
        return result;
    }
}
