import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.ibm.broker.javacompute.MbJavaComputeNode;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;
import com.ibm.broker.plugin.MbOutputTerminal;
import com.ibm.broker.plugin.MbUserException;


public class InsertDataToDB2_errorrecords extends MbJavaComputeNode {

	public void evaluate(MbMessageAssembly inAssembly) throws MbException {
		MbOutputTerminal out = getOutputTerminal("out");

		MbMessage inMessage = inAssembly.getMessage();
		MbMessageAssembly outAssembly = null;
		try {
			// create new message as a copy of the input
			MbMessage outMessage = new MbMessage(inMessage);
			outAssembly = new MbMessageAssembly(inAssembly, outMessage);
			// ----------------------------------------------------------
			// Add user code below
			Connection conn = null;
		    Statement stmt = null;
			try {

				// Obtain a java.sql.Connection using a JDBC Type4 datasource
		        // This example uses a Policy of type JDBCProviders called "MyJDBCPolicy" in Policy Project "MyPolicies"  
		        conn = getJDBCType4Connection("{MyPolicies}:DB2Policy", JDBC_TransactionType.MB_TRANSACTION_AUTO);
		        // Example of using the Connection to create a java.sql.Statement 
		        stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);	        
		        inMessage.getRootElement().getLastChild().getFirstChild();	        
		        // Executes the given SQL statement, which may be an INSERT, UPDATE, or DELETE statement
		        
//		        stmt.executeUpdate("INSERT INTO MYSCHEMA.ERROR_RECORDS VALUES("
//		        		+ dataElement.getFirstElementByPath("employee/pkey").getValueAsString() + ","
//		        		+ "'" + dataElement.getFirstElementByPath("employee/firstname").getValueAsString() + "',"
//		        		+ "'" + dataElement.getFirstElementByPath("employee/lastname").getValueAsString() + "',"
//		        		+ "'" + dataElement.getFirstElementByPath("employee/country").getValueAsString() + "'"
//	       		+")");
			} catch (MbException e) {
				// Re-throw to allow Broker handling of MbException
				throw e;
			} catch (RuntimeException e) {
				// Re-throw to allow Broker handling of RuntimeException
				throw e;
			} catch (SQLException sqx) {
				// Consider replacing Exception with type(s) thrown by user code
				// Example handling ensures all exceptions are re-thrown to be handled in the flow
				throw new MbUserException(this, "evaluate()", "", "", sqx.toString(), null);
			} finally {
				try {
					stmt.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
			// End of user code
			// ----------------------------------------------------------
		} catch (MbException e) {
			// Re-throw to allow Broker handling of MbException
			throw e;
		} catch (RuntimeException e) {
			// Re-throw to allow Broker handling of RuntimeException
			throw e;
		} catch (Exception e) {
			// Consider replacing Exception with type(s) thrown by user code
			// Example handling ensures all exceptions are re-thrown to be handled in the flow
			throw new MbUserException(this, "evaluate()", "", "", e.toString(),
					null);
		}
		// The following should only be changed
		// if not propagating message to the 'out' terminal
		out.propagate(outAssembly);

	}

	/**
	 * onPreSetupValidation() is called during the construction of the node
	 * to allow the node configuration to be validated.  Updating the node
	 * configuration or connecting to external resources should be avoided.
	 *
	 * @throws MbException
	 */
	@Override
	public void onPreSetupValidation() throws MbException {
	}

	/**
	 * onSetup() is called during the start of the message flow allowing
	 * configuration to be read/cached, and endpoints to be registered.
	 *
	 * Calling getPolicy() within this method to retrieve a policy links this
	 * node to the policy. If the policy is subsequently redeployed the message
	 * flow will be torn down and reinitialized to it's state prior to the policy
	 * redeploy.
	 *
	 * @throws MbException
	 */
	@Override
	public void onSetup() throws MbException {
	}

	/**
	 * onStart() is called as the message flow is started. The thread pool for
	 * the message flow is running when this method is invoked.
	 *
	 * @throws MbException
	 */
	@Override
	public void onStart() throws MbException {
	}

	/**
	 * onStop() is called as the message flow is stopped. 
	 *
	 * The onStop method is called twice as a message flow is stopped. Initially
	 * with a 'wait' value of false and subsequently with a 'wait' value of true.
	 * Blocking operations should be avoided during the initial call. All thread
	 * pools and external connections should be stopped by the completion of the
	 * second call.
	 *
	 * @throws MbException
	 */
	@Override
	public void onStop(boolean wait) throws MbException {
	}

	/**
	 * onTearDown() is called to allow any cached data to be released and any
	 * endpoints to be deregistered.
	 *
	 * @throws MbException
	 */
	@Override
	public void onTearDown() throws MbException {
	}

}
