package com.suny;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import com.suny.whereami.service.SearchService;
import com.suny.whereami.service.google.Place;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("location/{lat}/{lon}/{query}")
public class MyResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     * @throws IOException 
     */
    @GET
    //@Path("/{lat}/{lon}/{query}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getLocation(@PathParam("lat") double latCell, @PathParam("lon") double lonCell, @PathParam("query") String query) {
    	long startTime = System.currentTimeMillis();
    	System.out.println("getLocation: latCell = " + latCell + "\tlonCell = " + lonCell + "\tQuery: " + query);
    	   	
    	int result = 0;
    	double lat = 0.0;
    	double lon = 0.0;
        Place loc;
		try {
			loc = SearchService.searchLocation(latCell, lonCell, query);
			if (loc != null) {
	        	lat = loc.getLatitude();
	        	lon = loc.getLongitude();
	        	result = 1;
	        } 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		long runTime = endTime - startTime;
		System.out.println("Running Time: " + runTime);
        return result + "," + lat + "," + lon;
    }
    
    private String FILE_PATH = "/tmp/";
        
    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(
    		@FormDataParam("file") InputStream uploadedInputStream,
    		@FormDataParam("file") FormDataContentDisposition fileDetail) {


        //how to get strings?????

        String imageFileName = fileDetail.getFileName();

        String uploadedFileLocation = FILE_PATH + imageFileName;

        saveToFile(uploadedInputStream, uploadedFileLocation);

        String output = "success";

        return Response.status(200).entity(output).build();

    }

    private void saveToFile(InputStream uploadedInputStream,
                String uploadedFileLocation) {

        try {
            OutputStream out = null;
            int read = 0;
            byte[] bytes = new byte[1024];

            out = new FileOutputStream(new File(uploadedFileLocation));
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}
