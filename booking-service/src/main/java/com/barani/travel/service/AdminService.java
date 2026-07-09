package com.barani.travel.service;

import com.barani.travel.auth.AdminDashboardResponse;
import com.barani.travel.auth.UserResponse;

import java.util.List;

public interface AdminService {

    AdminDashboardResponse dashboard();

    List<UserResponse> searchUsers(String keyword);

    List<UserResponse> getUsers();
}