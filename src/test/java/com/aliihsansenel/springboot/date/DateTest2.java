package com.aliihsansenel.springboot.date;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateTest2 {

	public static void main(String[] args) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = (Date) sdf.parse("24-02-1999");
		//Date date = Date.valueOf("1999-02-24");

		System.out.println(date);
	}

}
