package com.travelcheck.library.exception;

import java.util.logging.Logger;

/**
 * Mobile Payment Solutions Ltd. Package: com.mps.transfers.archive User: marcus Date: May 29, 2011 Time: 1:09:47 PM
 */
public class ArchiveException extends BaseException {
	private static final Logger log = Logger.getLogger(ArchiveException.class.getName());

	public ArchiveException(String s) {
		super(s);
	}
}
