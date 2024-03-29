package database;

import java.io.Serializable;
import java.time.Instant;

enum AccessMode {
	READ,WRITE
}
public class LeaseLock implements Serializable {
	/**
	 * Using default.
	 */
	private static final long serialVersionUID = 1L;
	Long ownerTransactionID;
	AccessMode mode;
	Instant expirationTime;
	int lockedKey;
	
	@Override
	public String toString() {
		return "LeaseLock [ownerTransactionID=" + ownerTransactionID
				+ ", mode=" + mode + ", expirationTime=" + expirationTime
				+ ", lockedKey=" + lockedKey + "]";
	}

	public LeaseLock(Long ownerTransactionID, AccessMode mode, Instant expirationTime, int lockedKey) {
		this.ownerTransactionID = ownerTransactionID;
		this.mode = mode;
		this.expirationTime = expirationTime;
		this.lockedKey = lockedKey;
	}
	
	public synchronized Long getOwnerTransactionID() {
		return ownerTransactionID;
	}

	public synchronized void setOwnerTransactionID(Long ownerTransactionID) {
		this.ownerTransactionID = ownerTransactionID;
	}

	public synchronized AccessMode getMode() {
		return mode;
	}

	public synchronized void setMode(AccessMode mode) {
		this.mode = mode;
	}

	public synchronized int getLockedKey() {
		return lockedKey;
	}

	public synchronized void setLockedKey(int lockedKey) {
		this.lockedKey = lockedKey;
	}

	public synchronized Instant getExpirationTime() {
		return expirationTime;
	}

	public synchronized void setExpirationTime(Instant expirationTime) {
		this.expirationTime = expirationTime;
	}

	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof LeaseLock)) {
			return false;
		} else {
			LeaseLock lock = (LeaseLock) obj;
			return ownerTransactionID.equals(lock.ownerTransactionID) && (lockedKey == lock.lockedKey);
		}
	}
	
	public boolean equalForValidatingLocks(Object obj) {
		if (obj == null || !(obj instanceof LeaseLock)) {
			return false;
		} else {
			LeaseLock lock = (LeaseLock) obj;
			return ownerTransactionID.equals(lock.ownerTransactionID) && (lockedKey == lock.lockedKey) && (mode == lock.mode);
		}
	}
	
	 public int hashCode() { 
		 	int hash = 1;
		    return (int) ((hash * 31 + ownerTransactionID) * 31 + lockedKey);
		   
	 }


}
