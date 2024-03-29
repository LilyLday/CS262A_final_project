//Author Matt Weber matt.weber@berkeley.edu

package database;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class RemoteRegistry extends UnicastRemoteObject implements
		RemoteRegistryIntf {

	//static String TERRATEST = "128.32.48.222";
	static String TERRATEST = "localhost";
	HashMap<String, String> RemoteNameToNetworkName;

	static int objectPortOnTerratest = 1050;
	
	public RemoteRegistry() throws RemoteException {
		super(objectPortOnTerratest);
		this.RemoteNameToNetworkName = new HashMap<String, String>();
	}

	public synchronized boolean hasRemoteName(String RemoteObjectName){
		if(RemoteNameToNetworkName.containsKey(RemoteObjectName)){
			return true;
		} else {
			return false;
		}
	}
	
	public synchronized boolean registerNetworkName(String NetworkName,
			String RemoteObjectName) throws RemoteException {
		if (RemoteNameToNetworkName.containsKey(RemoteObjectName)) {
			return false;
		} else {
			RemoteNameToNetworkName.put(RemoteObjectName, NetworkName);
			return true;
		}
	}

	public synchronized boolean unRegisterRemoteName(
			String RemoteObjectName) throws RemoteException {
		if(RemoteNameToNetworkName.containsKey(RemoteObjectName)) {
			RemoteNameToNetworkName.remove(RemoteObjectName);
			return true;
		} else {
			return false;
		}
	}

	public synchronized void reset() throws RemoteException{
		this.RemoteNameToNetworkName.clear();
	}
	
	public synchronized String getNetworkName(String RemoteObjectName)
			throws RemoteException {
		return RemoteNameToNetworkName.get(RemoteObjectName);
	}

	public static void main(String args[]) throws Exception {
		System.out.println("RemoteRegistry server started");
		try { // special exception handler for registry creation
			LocateRegistry.createRegistry(1099);
			System.out.println("java RMI registry created.");
		} catch (RemoteException e) {
			// do nothing, error means registry already exists
			System.out.println("java RMI registry already exists.");
		}

		RemoteRegistry me = new RemoteRegistry();
		
		// Bind this objects instance to the name "RemoteRegistry"
		Naming.rebind("//" +  TERRATEST + "/RemoteRegistry", me);
		System.out.println("RemoteRegistry bound in local registry");
	}
}
	
//	System.out.println("RemoteRegistry server started");
//	try { // special exception handler for registry creation
//		LocateRegistry.createRegistry(1099);
//		System.out.println("java RMI registry created.");
//	} catch (RemoteException e) {
//		// do nothing, error means registry already exists
//		System.out.println("java RMI registry already exists.");
//	}
//
//	try{
//	RemoteRegistry me = new RemoteRegistry();
//	Naming.rebind("//localhost/RemoteRegistry", me);
//	} catch (RemoteException e) {
//		System.out.println("Error, unable to bind.");
//	}
//			
//try {
//	RemoteRegistryIntf obj = (RemoteRegistryIntf)Naming.lookup("//localhost/RemoteRegistry");
//} catch (RemoteException e) {
//	System.out.println("Error, unable to startup.");
//}
//	}
//	
//}
//	
