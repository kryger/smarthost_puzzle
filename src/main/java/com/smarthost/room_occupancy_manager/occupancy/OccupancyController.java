package com.smarthost.room_occupancy_manager.occupancy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/occupancy")
public class OccupancyController {
    private final BidsService bidsService;
    private final RoomToBidderMatchService roomToBidderMatchService;

    @Autowired
    public OccupancyController(BidsService bidsService, RoomToBidderMatchService roomToBidderMatchService) {
        this.bidsService = bidsService;
        this.roomToBidderMatchService = roomToBidderMatchService;
    }

    @GetMapping("/matchRooms")
    public Map<RoomType, RoomsOffer> calculate(@RequestParam Integer freePremiumRooms, @RequestParam Integer freeEconomyRooms) {
        List<Integer> bidsList = bidsService.getCurrentBidsInCents();
        return roomToBidderMatchService.matchRoomsToBidders(bidsList, freePremiumRooms, freeEconomyRooms);
    }
}
