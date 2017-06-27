package rebu.web.rest;

import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.REngineException;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

public class HelloRWorld {

    public static void main(String a[]){
        RConnection connection = null;

/*        try {
             Create a connection to Rserve instance running
             * on default port 6311
             
            connection = new RConnection();

            String vector = "c(1,2,3,4)";
            connection.eval("meanVal=mean(" + vector + ")");
            double mean = connection.eval("meanVal").asDouble();
            System.out.println("The mean of given vector is=" + mean);
        } catch (RserveException e) {
            e.printStackTrace();
        } catch (REXPMismatchException e) {
            e.printStackTrace();
        }finally{
            connection.close();
        }*/
        
        try {
            /* Create a connection to Rserve instance running on default port
             * 6311
             */
            connection = new RConnection();
            connection.eval("source('C:\\\\Users\\\\DumitruD\\\\Desktop\\\\liniarRegressionWithDB.R')");
            int unit_id = 82000012;

            int id = 1;
            String temperature = "cpm";
            try {
            	REXP rResponseObject = null;
				try {
					rResponseObject = connection.parseAndEval("try(eval("+ "predictionFromDB("+unit_id+","+temperature+","+id+")" +"),silent=TRUE)");
				} catch (REngineException e) {
					// TODO Auto-generated catch block
					System.out.println("R Serve Eval Exception : "+rResponseObject.asString());
					e.printStackTrace();
				} 
            	if (rResponseObject.inherits("try-error")) { 
            		System.out.println("R Serve Eval Exception : "+rResponseObject.asString());
            		}
				System.out.println(connection.eval("predictionFromDB("+unit_id+","+temperature+","+id+")").asDoubles()[0]);
            
			} catch (REXPMismatchException e) {
				e.printStackTrace();
			}finally{
	            connection.close();
	        }
        } catch (RserveException e) {
            e.printStackTrace();
        } 
    }
}