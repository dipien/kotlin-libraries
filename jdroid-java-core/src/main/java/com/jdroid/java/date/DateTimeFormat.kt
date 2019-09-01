package com.jdroid.java.date

object DateTimeFormat {

    /**
	 * Date format like 2013-01-11T20:30:00.000
	 */
    const val YYYYMMDDTHHMMSSSSS = "yyyy-MM-dd'T'HH:mm:ss.SSS"

    /**
	 * Date format like yyyy-MM-ddTHH:mm:ss ZZZZ
	 */
    const val YYYYMMDDTHHMMSSZ = "yyyy-MM-dd'T'HH:mm:ssZZZZ"

    /**
	 * Date format like yyyy-MM-dd HH:mm:ss Z
	 */
    const val YYYYMMDDHHMMSSZ = "yyyy-MM-dd HH:mm:ss Z"

    /**
	 * Date format like 2010-10-25 21:30:00.123
	 */
    const val YYYYMMDDHHMMSSSSS = "yyyy-MM-dd HH:mm:ss.SSS"

    /**
	 * Date format like 2010-10-25 21:30:00
	 */
    const val YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss"

    /**
	 * Date format like 10/25/2010 21:30
	 */
    const val MMDDYYYYHHMM = "MM/dd/yyyy HH:mm"

    /**
	 * Date format like Nov 5 1985 3:45 PM
	 */
    const val MMMDYYYYHHMMAA = "MMM d yyyy hh:mm aa"

    /**
	 * Date format like Nov 5 3:45 PM
	 */
    const val MMMDHHMMAA = "MMM d hh:mm aa"

    /**
	 * Date format like 10/25/2010
	 */
    const val MMDDYYYY = "MM/dd/yyyy"

    /**
	 * Date format like 10-25-2010
	 */
    const val MMDDYYYY_SLASH = "MM-dd-yyyy"

    /**
	 * Date format like 2010-10-25
	 */
    const val YYYYMMDD = "yyyy-MM-dd"

    /**
	 * Date format like Nov 5 1985
	 */
    const val MMMDYYYY = "MMM d yyyy"

    /**
	 * Date format like Sat, Dec 15, 2012
	 */
    const val EEMMMDYYYY = "EE, MMM d, yyyy"

    /**
	 * Date format like Monday Nov 5 1985
	 */
    const val EEEE_MMMDYYYY = "EEEE MMM d yyyy"

    /**
	 * Date format like Friday 5 November 2013
	 */
    const val EEEEDMMMMYYYY = "EEEE d MMMM yyyy"

    /**
	 * Date format like Fri 5 November
	 */
    const val EEDMMMM = "EE d MMMM"

    /**
	 * Date format like Fri 5 November 2013
	 */
    const val EEDMMMMYYYY = "EE d MMMM yyyy"

    /**
	 * Date format like Monday Nov 5
	 */
    const val EEEE_MMMD = "EEEE MMM d"

    /**
	 * Date format like Friday 5 November
	 */
    const val EEEEDMMMM = "EEEE d MMMM"

    /**
	 * Date format like Friday November
	 */
    const val EEEEMMMM = "EEEE MMMM"

    /**
	 * Date format like November 5
	 */
    const val MMMMD = "MMMM d"

    /**
	 * Date format like Nov 5
	 */
    const val MMMD = "MMM d"

    /**
	 * Date format like Friday
	 */
    const val EEEE = "EEEE"

    /**
	 * Date format like Fri
	 */
    const val E = "E"

    /**
	 * Date format like Fri 5:15 AM
	 */
    const val EHHMMAA = "E hh:mm aa"

    /**
	 * Date format like 21:45:34
	 */
    const val HHMMSS = "HH:mm:ss"

    /**
	 * Date format like 03:45 PM
	 */
    const val HHMMAA = "hh:mm aa"

    /**
	 * Date format like 21:45
	 */
    const val HHMM = "HH:mm"
}
