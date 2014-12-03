package database;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.time.Duration;

public class Replica extends UnicastRemoteObject implements ReplicaIntf {
	String RMIRegistryAddress;
	boolean isLeader;
	String name;

	Log dataLog;
	Replica leader;

	Thread leaseKiller;
	LockTable lockTable;
	List<LeaseLock> committingWrites;
	
	// the lock lease interval is 10 milliseconds across replicas.
	static Duration LOCK_LEASE_INTERVAL = Duration.ofMillis(10);

	public Replica(String RMIRegistryAddress, boolean isLeader, String name)
			throws RemoteException {
		super();
		this.RMIRegistryAddress = RMIRegistryAddress;
		this.isLeader = isLeader;
		this.name = name;
		this.lockTable = new LockTable();
		this.committingWrites = new LinkedList<LeaseLock>();
		this.leaseKiller = new Thread(new LeaseKiller(lockTable,
				committingWrites));
		leaseKiller.start();

	}

	public void setLog(Log dataLog) {
		this.dataLog = dataLog;
	}

	public void setLeaderSet(Replica leader) {
		this.leader = leader;
	}

	public boolean keepTransactionAlive(List<LeaseLock> locks)
			throws RemoteException {
		// TODO implement this method
		return false;
	}

	public String RWTcommit(Long transactionID, List<LeaseLock> heldLocks,
			HashMap<Integer, Integer> memaddrToValue) throws RemoteException {
		// TODO implement this method
		return "abort";
	}
	
	
	
	


	
}
