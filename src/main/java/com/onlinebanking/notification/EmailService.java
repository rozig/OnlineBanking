package com.onlinebanking.notification;

public interface EmailService {
	void sendSimpleMessage(String to, String subject, String text);
}
