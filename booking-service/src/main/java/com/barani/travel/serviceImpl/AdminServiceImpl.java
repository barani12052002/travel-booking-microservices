package com.barani.travel.serviceImpl;

import com.barani.travel.auth.AdminDashboardResponse;
import com.barani.travel.auth.UserResponse;
import com.barani.travel.enums.BookingStatus;
import com.barani.travel.repository.BookingRepository;
import com.barani.travel.repository.UserRepository;
import com.barani.travel.service.AdminService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;

    public AdminServiceImpl(UserRepository userRepository,
                            BookingRepository bookingRepository) {

        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public AdminDashboardResponse dashboard() {

        AdminDashboardResponse dto = new AdminDashboardResponse();

        dto.setTotalUsers(userRepository.count());

        dto.setTotalBookings(bookingRepository.count());

        dto.setCancelledBookings(bookingRepository.countByBookingStatus(BookingStatus.CANCELLED));
        dto.setTotalRevenue(bookingRepository.getTotalRevenue());

        return dto;
    }

    @Override
    public List<UserResponse> searchUsers(String keyword) {

        return userRepository
                .findByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                        keyword,
                        keyword)
                .stream()
                .map(user -> {

                    UserResponse response = new UserResponse();

                    response.setId(user.getId());
                    response.setUsername(user.getUsername());
                    response.setEmail(user.getEmail());
                    response.setRole(user.getRole().name());

                    return response;

                })
                .toList();
    }

    @Override
    public List<UserResponse> getUsers() {

        return userRepository.findAll()
                .stream()
                .map(user -> {

                    UserResponse response = new UserResponse();

                    response.setId(user.getId());
                    response.setUsername(user.getUsername());
                    response.setEmail(user.getEmail());
                    response.setRole(user.getRole().name());

                    return response;

                })
                .toList();
    }
}