package com.barani.travel.auth;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AdminDashboardResponse {

    private Long totalUsers;

    private Long totalBookings;

    private Long cancelledBookings;

    private BigDecimal totalRevenue;
}