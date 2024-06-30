package com.smarthost.room_occupancy_manager.occupancy;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;


class RoomToBidderMatchServiceTest {
    private final RoomToBidderMatchService service = new RoomToBidderMatchService();
    private final static List<Integer> MOCK_BIDS = Arrays.asList(2300, 4500, 15500, 37400, 2200, 9999, 10000, 10100, 11500, 20900);

    @Test
    void testControlConditions1() {
        Map<RoomType, RoomsOffer> results = service.matchRoomsToBidders(MOCK_BIDS, 3, 3);

        assertThat(results.get(RoomType.PREMIUM).takenRooms()).isEqualTo(3);
        assertThat(results.get(RoomType.ECONOMY).takenRooms()).isEqualTo(3);

        assertThat(results.get(RoomType.PREMIUM).totalIncome()).isEqualTo(73800);
        assertThat(results.get(RoomType.ECONOMY).totalIncome()).isEqualTo(16799);

    }

    @Test
    void testControlConditions2() {
        Map<RoomType, RoomsOffer> results = service.matchRoomsToBidders(MOCK_BIDS, 7, 5);

        assertThat(results.get(RoomType.PREMIUM).takenRooms()).isEqualTo(6);
        assertThat(results.get(RoomType.ECONOMY).takenRooms()).isEqualTo(4);

        assertThat(results.get(RoomType.PREMIUM).totalIncome()).isEqualTo(105400);
        assertThat(results.get(RoomType.ECONOMY).totalIncome()).isEqualTo(18999);
    }

    @Test
    void testControlConditions3() {
        Map<RoomType, RoomsOffer> results = service.matchRoomsToBidders(MOCK_BIDS, 2, 7);

        assertThat(results.get(RoomType.PREMIUM).takenRooms()).isEqualTo(2);
        assertThat(results.get(RoomType.ECONOMY).takenRooms()).isEqualTo(4);

        assertThat(results.get(RoomType.PREMIUM).totalIncome()).isEqualTo(58300);
        assertThat(results.get(RoomType.ECONOMY).totalIncome()).isEqualTo(18999);
    }

    @Test
    void testControlConditions4() {
        Map<RoomType, RoomsOffer> results = service.matchRoomsToBidders(MOCK_BIDS, 7, 1);

        assertThat(results.get(RoomType.PREMIUM).takenRooms()).isEqualTo(7);
        assertThat(results.get(RoomType.ECONOMY).takenRooms()).isEqualTo(1);

        // NOTE: apparent mistake/typo in the instructions, expected 45.99EUR but 45.00 in input data
        // assertThat(results.get(RoomType.ECONOMY).totalIncome()).isEqualTo(4599); // incorrect
        assertThat(results.get(RoomType.ECONOMY).totalIncome()).isEqualTo(4500);
        // NOTE: consequently the premium sum is not going to be 1153.00, but 1153.99
        // assertThat(results.get(RoomType.PREMIUM).totalIncome()).isEqualTo(115300); // incorrect
        assertThat(results.get(RoomType.PREMIUM).totalIncome()).isEqualTo(115399);
    }

}