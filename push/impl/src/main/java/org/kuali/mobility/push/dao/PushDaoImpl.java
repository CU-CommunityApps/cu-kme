/**
 * Copyright 2011 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
 
package org.kuali.mobility.push.dao;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyStore;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.codec.binary.Hex;
import org.kuali.mobility.push.entity.Device;
import org.kuali.mobility.push.entity.Push;
import org.springframework.stereotype.Repository;

@Repository
public class PushDaoImpl implements PushDao {
	
	private static org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(PushDaoImpl.class);		
	private String ME = this.getClass().getName();	

	private String pushTemplate  = "{\"aps\":{\"alert\":\"<MESSAGE>\",\"badge\":<BADGE>},\"i\":<MESSAGEID>,\"e\":\"<EMERGENCY>\"}";
	private String token 	 = "d2c00f25aa0e489f8616a95911f6e4ae6660f8e7036b648d402649c3c024c460";
	private String host 	 = "gateway.sandbox.push.apple.com";
	private int    port 	 = 2195;

	private String GoogleAuthToken = "DQAAAMAAAABPI7vseSHpCilvoAm7jQlNMHiLdHH1riEuxrAX-kXmUfnmUepbgzqZrvzSUbFAdRoZhDPKgIystv1w6pClgV9xTvuuj38tKz5vkudRLcJdTTvjRlJgNq-V_iUduv6mGaUuoeWgbJI25iUBLS7ZezwRcaOag-2wv_-JeIv9QRY25K2Dnz9YdrL4VVnVKCai37-8nDdoEbPDELAKS9MDuroS_My0fmHHHtX0Er63y1VkztCVQ21F-PTmgTGp76ruWlA";
	private String GoogleCollapseKey = "";
	
	private static Thread BackgroundPushThread = null;
	
	
	@PersistenceContext
    private EntityManager entityManager;
	
    public PushDaoImpl(){}

    @SuppressWarnings("unchecked")
	public Push findPushById(Long id){
        Query query = entityManager.createQuery("select p from Push p where p.pushId = '" + id + "'");
        Push result; 
        try{
        	result = (Push) query.getSingleResult();
        }catch(Exception e){
        	LOG.info("Exception: " + e.getMessage());
        	result = null;
        }	
        return result;
	}    
    
    @SuppressWarnings("unchecked")
	public List<Push> findAllPush(){
        Query query = entityManager.createQuery("select p from Push p order by p.postedTimestamp desc");
        return query.getResultList();
	}

    @SuppressWarnings("unchecked")
	public Long countPushes(){
        Query query = entityManager.createQuery("select count(p) from Push p");
        return (Long) query.getSingleResult();
	}
    
    private SSLSocket openConnectionToAPNS(String host, int port, String key, String passphrase){
    	SSLSocket socket;
    	try {    		
    		KeyStore keyStore = KeyStore.getInstance("PKCS12");

//    		keyStore.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("newcert.p12"), "strange word to use".toCharArray());
//    		keyStore.load(getClass().getResourceAsStream("/newcert.p12"), "strange word to use".toCharArray());
//    		keyStore.load(this.getClass().getClassLoader().getResourceAsStream("newcert.p12"), "strange word to use".toCharArray());
    		
    		// This works when built with Eclipse, but not when built from command line. 
    		// Has to do with where the build system puts /resources/*.p12 file
//    		keyStore.load(this.getClass().getClassLoader().getResourceAsStream(key), "strange word to use".toCharArray());
    		
    		// Currently only works when read from the server's FS. Won't currently read from within eclipse project. 
    		// Putting it in /opt/kme/push prevents naming conflicts. 
    		keyStore.load(new FileInputStream("/opt/kme/push/newcert.p12"), "strange word to use".toCharArray());    		   		

    		KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("sunx509");
            keyManagerFactory.init(keyStore, "strange word to use".toCharArray());
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("sunx509");
            trustManagerFactory.init(keyStore);
            SSLContext sslCtx = SSLContext.getInstance("TLS");
            sslCtx.init(keyManagerFactory.getKeyManagers(), null, null);
            SSLSocketFactory sslSocketFactory = sslCtx.getSocketFactory();
            socket = (SSLSocket)sslSocketFactory.createSocket(host, port);
            socket.startHandshake();
           
            //Diagnostic output
    		Enumeration e = keyStore.aliases();
       		LOG.info(e.toString());
    		while(e.hasMoreElements()){
    			LOG.info("Alias: " + e.nextElement().toString());
    		}
            
            String not = (socket.isConnected()) ? "":"NOT ";
            LOG.info("SSLSocket is " + not + "Connected");

            LOG.info("Connected to: " + socket.getInetAddress().getCanonicalHostName());            
            LOG.info("Connected to: " + socket.getInetAddress().getHostAddress());
            
            String cs[] = socket.getEnabledCipherSuites();
            LOG.info("CipherSuites: " + Arrays.toString(cs));
            
            String ep[] = socket.getEnabledProtocols();
            LOG.info("Enabled Protocols: " + Arrays.toString(ep));
            		
            LOG.info("Timeout: " + socket.getSoTimeout());
            LOG.info("Send Buffer Size: " + socket.getSendBufferSize());            

                     
            return socket;
    	} catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
    }

    private void closeConnectionToAPNS(SSLSocket s){
    	try {
			s.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private String preparePayload(Push p){
		String payload = pushTemplate;
		payload = payload.replaceAll("<MESSAGE>", p.getTitle());
		payload = payload.replaceAll("<MESSAGEID>", p.getPushId().toString());
		payload = payload.replaceAll("<BADGE>", "0");
		payload = payload.replaceAll("<EMERGENCY>", (p.getEmergency())?"YES":"NO");
		return payload;
    }
    
	private class SendPushThread implements Runnable {	
		public Push push;
		public List<Device> devices;
		public Long count;
		public Long completed; 
		public SendPushThread(Push push, List<Device> devices){
			this.push = push;
			this.devices = devices;
			this.count = 0L + devices.size();
			this.completed = 0L;
		}
		public void run() {   
			int i = 0;
			LOG.info("----- Thread Starting -----");
			this.completed = tSendPush(this.push, this.devices);
			LOG.info("----- Thread Ending -----");
			push.setRecipients(completed);
			savePush(push);
			
/*
			while(true){
				try{
					i++;
					LOG.info("----- Sleep 4 seconds ----- #" + i);
					Thread.sleep(4000);
				}catch(InterruptedException e){
					LOG.info(e.getMessage(), e);
				}
			}
*/
		}
	}
    
    
   
    @SuppressWarnings("unchecked")
	public Long sendPush(Push push, Device device){
		Long result = 0L;
		if(device.getType().equals("iOS")){
			SSLSocket socket = openConnectionToAPNS(host, port, "newcert.p12", "strange word to use");
			if(sendPushToIOS(push, device, socket)){
				LOG.info("sent to an iOS device");
				closeConnectionToAPNS(socket);
				return 1L;
			}
		}
		if(device.getType().equals("Android")){
			if(sendPushToAndroid(push, device)){
				return 1L;
			}
		}
		if(device.getType().equals("Windows")){
			if(sendPushToWindows(push, device)){
				return 1L;
			}
		}
		if(device.getType().equals("Blackberry")){
			if(sendPushToBlackberry(push, device)){
				return 1L;
			}
		}
    	return -1L;
	}


	private Long tSendPush(Push push, List<Device> devices){
		Long successfulPushes = 0L;
		Iterator iter = devices.iterator();
		SSLSocket socket = openConnectionToAPNS(host, port, "newcert.p12", "strange word to use");
		while(iter.hasNext()){
			Device d = (Device)iter.next();
			if(d.getType().equals("iOS")){
				if(sendPushToIOS(push, d, socket)){
					LOG.info("sent to an iOS device");
					successfulPushes++;					
				}
			}
			if(d.getType().equals("Android")){
				if(sendPushToAndroid(push, d)){
					LOG.info("sent to an Android device");
					successfulPushes++;					
				}
			}
			if(d.getType().equals("Windows")){
				if(sendPushToWindows(push, d)){
					LOG.info("sent to an Windows device");
					successfulPushes++;	
				}
			}
			if(d.getType().equals("Blackberry")){
				if(sendPushToBlackberry(push, d)){
					LOG.info("sent to an Blackberry device");
					successfulPushes++;	
				}
			}
		}
		closeConnectionToAPNS(socket);
    	return successfulPushes;
    }
    
    @SuppressWarnings("unchecked")
	public Long sendPush(Push push, List<Device> devices){
    	Long pushes = 0L;
    	BackgroundPushThread = new Thread(new SendPushThread(push, devices));
    	BackgroundPushThread.setDaemon(true);
    	BackgroundPushThread.start();
    	return pushes;   	
    }

    
    
    // Stub for Sending Push to Windows.
    @SuppressWarnings("unchecked")
	private boolean sendPushToWindows(Push push, Device device){
		return true;
	}

    // Stub for Sending Push to Blackberry.
    @SuppressWarnings("unchecked")
	private boolean sendPushToBlackberry(Push push, Device device){
    	return true;
	}
    
    
    private static class CustomizedHostnameVerifier implements HostnameVerifier {
    	public boolean verify(String hostname, SSLSession session) {
    		return true;
    	}
    }
    
    // Stub for Sending Push to Android.
    @SuppressWarnings("unchecked")
	private boolean sendPushToAndroid(Push push, Device device){
    	
        try {
			HttpsURLConnection.setDefaultHostnameVerifier(new CustomizedHostnameVerifier()); 
			URL url = new URL("https://android.apis.google.com/c2dm/send");
			HttpsURLConnection request = (HttpsURLConnection) url.openConnection();
			request.setDoOutput(true);
			request.setDoInput(true);

			StringBuilder buf = new StringBuilder();
			buf.append("registration_id").append("=").append((URLEncoder.encode(device.getRegId(), "UTF-8")));
			buf.append("&collapse_key").append("=").append((URLEncoder.encode(push.getPostedTimestamp().toString(), "UTF-8")));
			buf.append("&data.message").append("=").append((URLEncoder.encode(push.getMessage(), "UTF-8")));
			buf.append("&data.title").append("=").append((URLEncoder.encode(push.getTitle(), "UTF-8")));
			buf.append("&data.id").append("=").append((URLEncoder.encode(push.getPushId().toString(), "UTF-8")));
			buf.append("&data.url").append("=").append((URLEncoder.encode(push.getUrl().toString(), "UTF-8")));
			
			String emer = (push.getEmergency()) ? "YES" : "NO";
			buf.append("&data.emer").append("=").append((URLEncoder.encode(emer, "UTF-8")));
			
	        request.setRequestMethod("POST");
	        request.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	        request.setRequestProperty("Content-Length", buf.toString().getBytes().length+"");
	        request.setRequestProperty("Authorization", "GoogleLogin auth=" + GoogleAuthToken);

			LOG.info("SEND Android Buffer: " + buf.toString());
	        
	        OutputStreamWriter post = new OutputStreamWriter(request.getOutputStream());
	        post.write(buf.toString());
	        post.flush();
	        
	        BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream()));
	        buf = new StringBuilder();
	        String inputLine;
	        while ((inputLine = in.readLine()) != null) {
	            buf.append(inputLine);
	        }
	        post.close();
	        in.close();

	        LOG.info("response from C2DM server:\n" + buf.toString());
	        						
        } catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
        } catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
    
    // Stub for Sending Push to iOS.
    @SuppressWarnings("unchecked")
	private boolean sendPushToIOS(Push push, Device device, SSLSocket socket){	
		String payload = preparePayload(push);
		LOG.info("Push: " + push);
		LOG.info("Device: " + device);				
		String token = device.getRegId();
		
		try {   
			char[] t = token.toCharArray();
            byte[] b = Hex.decodeHex(t);
                                    
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            // Command Byte. 
            baos.write(0);
            // Device ID Length
            baos.write(0);
            baos.write(32);
            // Device ID
            baos.write(b);
            // Payload Length
            baos.write(0);
            baos.write(payload.length());
            // Payload
            baos.write(payload.getBytes());            
            LOG.info("Payload: Final size: " + baos.size());

			if(socket != null){
				OutputStream out = socket.getOutputStream();
				InputStream in	= socket.getInputStream();
				out.write(baos.toByteArray());
				out.flush();
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}   
		return true;
	}
    
    
    public void savePush(Push push){    
		if(push == null){
    		return;
    	}
    	if(push.getPushId() == null){
    		entityManager.persist(push);
    	}else{
    		entityManager.merge(push);
    	}
    }
    
    
    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

}
