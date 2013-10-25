package com.plugins;

import java.util.Comparator;

import com.example.nav2.User;

public class NameComparator implements Comparator<User> {

	@Override
	public int compare(User u1, User u2) {
		return u1.getName().compareTo(u2.getName());
	}

}
