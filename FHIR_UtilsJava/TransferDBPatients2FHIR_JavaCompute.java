import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.ibm.broker.javacompute.MbJavaComputeNode;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbJSON;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;
import com.ibm.broker.plugin.MbOutputTerminal;
import com.ibm.broker.plugin.MbUserException;
import org.apache.commons.lang3.StringUtils;


public class TransferDBPatients2FHIR_JavaCompute extends MbJavaComputeNode {

	public void evaluate(MbMessageAssembly inAssembly) throws MbException {
		MbOutputTerminal out = getOutputTerminal("out");

		MbMessage inMessage = inAssembly.getMessage();
		MbMessageAssembly outAssembly = null;
		try {
			// create new message as a copy of the input
			MbMessage outMessage = new MbMessage();
			outAssembly = new MbMessageAssembly(inAssembly, outMessage);
			// ----------------------------------------------------------
			// Add user code below
			Connection conn = null;
			ResultSet rs = null;
			Statement stmt = null;
			try {
				
				// retrive data from db2
				conn = getJDBCType4Connection("{MyPolicies}:DB2Policy", JDBC_TransactionType.MB_TRANSACTION_AUTO);
		        stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);	        
		        inMessage.getRootElement().getLastChild().getFirstChild();	        
		        rs = stmt.executeQuery("SELECT * FROM MYSCHEMA.PBASINFO");
		        
		        MbElement jsonData = outMessage.getRootElement()
		        		.createElementAsLastChild(MbJSON.PARSER_NAME)
		        		.createElementAsLastChild(MbElement.TYPE_NAME, MbJSON.DATA_ELEMENT_NAME, null)
		        		.createElementAsLastChild(MbJSON.ARRAY,MbJSON.DATA_ELEMENT_NAME, null);
		        MbElement jsonPatientsItem = null;
		        
		        while (rs.next()){
		        	jsonPatientsItem = jsonData.createElementAsLastChild(MbElement.TYPE_NAME, MbJSON.ARRAY_ITEM_NAME, null);
		        	jsonPatientsItem.createElementAsFirstChild( MbElement.TYPE_NAME_VALUE, "PHISTNUM", rs.getBigDecimal("PHISTNUM"));
		        			jsonPatientsItem.createElementAsFirstChild( MbElement.TYPE_NAME_VALUE, "PIDNO", this.formatStr(rs.getString("PIDNO")));
		        			jsonPatientsItem.createElementAsFirstChild( MbElement.TYPE_NAME_VALUE, "PRKNO", rs.getInt("PRKNO"));
		        			jsonPatientsItem.createElementAsFirstChild( MbElement.TYPE_NAME_VALUE, "PNAMEC", this.formatStr(rs.getString("PNAMEC")));
		        			jsonPatientsItem.createElementAsFirstChild( MbElement.TYPE_NAME_VALUE, "PNAME", this.formatStr(rs.getString("PNAME")));
		        			jsonPatientsItem.createElementAsFirstChild( MbElement.TYPE_NAME_VALUE, "PSEX", this.formatGender(rs.getInt("PSEX")));
		        			jsonPatientsItem.createElementAsFirstChild( MbElement.TYPE_NAME_VALUE, "PBIRTHDT", rs.getInt("PBIRTHDT"));
		        			jsonPatientsItem.createElementAsFirstChild( MbElement.TYPE_NAME_VALUE, "PETHGRP", this.formatStr(rs.getString("PETHGRP")));
		        			jsonPatientsItem.createElementAsFirstChild( MbElement.TYPE_NAME_VALUE, "PRESDNCE", this.formatStr(rs.getString("PRESDNCE")));
		        			jsonPatientsItem.createElementAsFirstChild( MbElement.TYPE_NAME_VALUE, "PRELIGIN", this.formatStr(rs.getString("PRELIGIN")));
		        			jsonPatientsItem.createElementAsFirstChild( MbElement.TYPE_NAME_VALUE, "PVETERAN", this.formatStr(rs.getString("PVETERAN")));
		        			jsonPatientsItem.createElementAsFirstChild( MbElement.TYPE_NAME_VALUE, "PADDR1", this.formatStr(rs.getString("PADDR1")));
		        			jsonPatientsItem.createElementAsFirstChild( MbElement.TYPE_NAME_VALUE, "PPATZIP", rs.getInt("PPATZIP"));
		        			jsonPatientsItem.createElementAsFirstChild( MbElement.TYPE_NAME_VALUE, "PPHONNO1", this.formatStr(rs.getString("PPHONNO1")));
		        			jsonPatientsItem.createElementAsFirstChild( MbElement.TYPE_NAME_VALUE, "PPATCAGY", rs.getInt("PPATCAGY"));
		        			jsonPatientsItem.createElementAsFirstChild( MbElement.TYPE_NAME_VALUE, "PFSTVDT", rs.getInt("PFSTVDT"));
		        			jsonPatientsItem.createElementAsFirstChild( MbElement.TYPE_NAME_VALUE, "PFSTMED", this.formatStr(rs.getString("PFSTMED")));
		        			jsonPatientsItem.createElementAsFirstChild( MbElement.TYPE_NAME_VALUE, "PLSTVDT", rs.getInt("PLSTVDT"));
		        			jsonPatientsItem.createElementAsFirstChild( MbElement.TYPE_NAME_VALUE, "PLSTMED", this.formatStr(rs.getString("PLSTMED")));
		        			jsonPatientsItem.createElementAsFirstChild( MbElement.TYPE_NAME_VALUE, "PACCVOL", rs.getInt("PACCVOL"));
		        			jsonPatientsItem.createElementAsFirstChild( MbElement.TYPE_NAME_VALUE, "PMEDSTAT", rs.getInt("PMEDSTAT"));
		        			jsonPatientsItem.createElementAsFirstChild( MbElement.TYPE_NAME_VALUE, "PCNTCARD", this.formatStr(rs.getString("PCNTCARD")));
		        			jsonPatientsItem.createElementAsFirstChild( MbElement.TYPE_NAME_VALUE, "PBFIN1", this.formatStr(rs.getString("PBFIN1")));
		        			jsonPatientsItem.createElementAsFirstChild( MbElement.TYPE_NAME_VALUE, "PBFIN2", this.formatStr(rs.getString("PBFIN2")));
		        			jsonPatientsItem.createElementAsFirstChild( MbElement.TYPE_NAME_VALUE, "PICD91", this.formatStr(rs.getString("PICD91")));
		        			jsonPatientsItem.createElementAsFirstChild( MbElement.TYPE_NAME_VALUE, "PICD92", this.formatStr(rs.getString("PICD92")));
		        			jsonPatientsItem.createElementAsFirstChild( MbElement.TYPE_NAME_VALUE, "PICD93", this.formatStr(rs.getString("PICD93")));
		        			jsonPatientsItem.createElementAsFirstChild( MbElement.TYPE_NAME_VALUE, "POR1", this.formatStr(rs.getString("POR1")));
		        			jsonPatientsItem.createElementAsFirstChild( MbElement.TYPE_NAME_VALUE, "POR2", this.formatStr(rs.getString("POR2")));
		        			jsonPatientsItem.createElementAsFirstChild( MbElement.TYPE_NAME_VALUE, "PMEDCAGY", this.formatStr(rs.getString("PMEDCAGY")));
		        			jsonPatientsItem.createElementAsFirstChild( MbElement.TYPE_NAME_VALUE, "PFIRE", this.formatStr(rs.getString("PFIRE")));
		        			jsonPatientsItem.createElementAsFirstChild( MbElement.TYPE_NAME_VALUE, "PMODFYDT", rs.getInt("PMODFYDT"));
		        			jsonPatientsItem.createElementAsFirstChild( MbElement.TYPE_NAME_VALUE, "POPERID", this.formatStr(rs.getString("POPERID")));
		        			jsonPatientsItem.createElementAsFirstChild( MbElement.TYPE_NAME_VALUE, "PPBLOOD", this.formatStr(rs.getString("PPBLOOD")));
		        			jsonPatientsItem.createElementAsFirstChild( MbElement.TYPE_NAME_VALUE, "PPAIDS", this.formatStr(rs.getString("PPAIDS")));
		        			jsonPatientsItem.createElementAsFirstChild( MbElement.TYPE_NAME_VALUE, "PPINF1", this.formatStr(rs.getString("PPINF1")));
		        			jsonPatientsItem.createElementAsFirstChild( MbElement.TYPE_NAME_VALUE, "PPINF2", this.formatStr(rs.getString("PPINF2")));
		        			jsonPatientsItem.createElementAsFirstChild( MbElement.TYPE_NAME_VALUE, "PPINF3", this.formatStr(rs.getString("PPINF3")));
		        			jsonPatientsItem.createElementAsFirstChild( MbElement.TYPE_NAME_VALUE, "PADDR2", this.formatStr(rs.getString("PADDR2")));
		        			jsonPatientsItem.createElementAsFirstChild( MbElement.TYPE_NAME_VALUE, "PPHONNO2", this.formatStr(rs.getString("PPHONNO2")));	
		        			jsonPatientsItem.createElementAsFirstChild( MbElement.TYPE_NAME_VALUE, "PPATSTAT", this.formatStr(rs.getString("PPATSTAT")));	
		        			jsonPatientsItem.createElementAsFirstChild( MbElement.TYPE_NAME_VALUE, "PDEADFLG", this.formatStr(rs.getString("PDEADFLG")));
		        			jsonPatientsItem.createElementAsFirstChild( MbElement.TYPE_NAME_VALUE, "PAPPEND", this.formatStr(rs.getString("PAPPEND")));
		        	
		        }
		        
		        
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

	public String formatGender (int gender){
		return gender == 1 ? "male" : "femail";
	}
	
	public String formatStr(String input) {
		if (input == null){
			return null;
		}
		input = StringUtils.trim(input);
		input = StringUtils.trimToEmpty(input);
		input = StringUtils.trimToNull(input);
		input = StringUtils.strip(input);
		input = StringUtils.stripToEmpty(input);
		input = StringUtils.stripToNull(input);
		input = StringUtils.deleteWhitespace(input);
		return input;
	}

}
