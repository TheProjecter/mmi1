//package mmi;

import java.awt.Dimension;
import java.util.Date;

/**
 * Contains the results of test case.
 * 
 * @author Shterev
 * 
 */
public class Testcase {

	private String testId; // anzahl der versuche vs. versuchsnummer ?

	private String tunnelId;
	private Dimension tunnelSize;
	private double mouseSpeed;
	private float time;
	private int errorCount;
	private String userName;
	private String userGender;

	public Testcase() {
	}

	public Testcase(String testId, String tunnelId, Dimension tunnelSize, double mouseSpeed, float time, int errorCount, String userName, String userGender) {
		super();
		this.testId = testId;
		this.tunnelId = tunnelId;
		this.tunnelSize = tunnelSize;
		this.mouseSpeed = mouseSpeed;
		this.time = time;
		this.errorCount = errorCount;
		this.userName = userName;
		this.userGender = userGender;
	}

	/**
	 * @return the testId
	 */
	public String getTestId() {
		return testId;
	}

	/**
	 * @param testId
	 *            the testId to set
	 */
	public void setTestId(String testId) {
		this.testId = testId;
	}

	/**
	 * @return the tunnelId
	 */
	public String getTunnelId() {
		return tunnelId;
	}

	/**
	 * @param tunnelId
	 *            the tunnelId to set
	 */
	public void setTunnelId(String tunnelId) {
		this.tunnelId = tunnelId;
	}

	/**
	 * @return the tunnelSize
	 */
	public Dimension getTunnelSize() {
		return tunnelSize;
	}

	/**
	 * @param tunnelSize
	 *            the tunnelSize to set
	 */
	public void setTunnelSize(Dimension tunnelSize) {
		this.tunnelSize = tunnelSize;
	}

	/**
	 * @return the mouseSpeed
	 */
	public double getMouseSpeed() {
		return mouseSpeed;
	}

	/**
	 * @param mouseSpeed
	 *            the mouseSpeed to set
	 */
	public void setMouseSpeed(double mouseSpeed) {
		this.mouseSpeed = mouseSpeed;
	}

	/**
	 * @return the time
	 */
	public float getTime() {
		return time;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setTime(float time) {
		this.time = time;
	}

	/**
	 * @return the errorCount
	 */
	public int getErrorCount() {
		return errorCount;
	}

	/**
	 * @param errorCount
	 *            the errorCount to set
	 */
	public void setErrorCount(int errorCount) {
		this.errorCount = errorCount;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the userGender
	 */
	public String getUserGender() {
		return userGender;
	}

	/**
	 * @param userGender
	 *            the userGender to set
	 */
	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}

}
