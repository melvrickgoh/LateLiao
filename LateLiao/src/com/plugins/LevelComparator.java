package com.plugins;

import java.util.Comparator;

import com.example.nav2.User;

public class LevelComparator implements Comparator<User> {

	@Override
	public int compare(User u1, User u2) {
		int uLevel1 = u1.getLevel();
		int uLevel2 = u2.getLevel();
		if (uLevel1 > uLevel2){
			return 1;
		}else if (uLevel1 == uLevel2){
			return 0;
		}else{
			return -1;
		}
	}

}
