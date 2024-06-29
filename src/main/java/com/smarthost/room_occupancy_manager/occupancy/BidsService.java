package com.smarthost.room_occupancy_manager.occupancy;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class BidsService {
    // Note: returns mocked data as of the first version
    // Note: values as integers, converted to eurocents to avoid rounding
    public List<Integer> getCurrentBidsInCents() {
        return Arrays.asList(2300, 4500, 15500, 37400, 2200, 9999, 10000, 10100, 11500, 20900);
    }
}
