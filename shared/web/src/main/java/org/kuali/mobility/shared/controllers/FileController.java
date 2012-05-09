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
 
package org.kuali.mobility.shared.controllers;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.kuali.mobility.file.entity.File;
import org.kuali.mobility.file.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller 
@RequestMapping("/files")
public class FileController {

	private static org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(FileController.class);

    @Autowired
    private FileService fileService;

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public void getFile(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response) {
    	    	
    	File file = fileService.findFileById(id);
    	LOG.info("--- Retrieve File ---");
    	LOG.info(file);

    	response.setContentType(file.getContentType());
    	response.setContentLength(file.getBytes().length);
    	try {
			OutputStream out = response.getOutputStream();
			out.write(file.getBytes());
    	} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	
    }

    @RequestMapping(method = RequestMethod.GET)
    public String index(HttpServletRequest request, Model uiModel) {
    	List<File> files = fileService.findAllFiles();
    	uiModel.addAttribute("files", files);
    	uiModel.addAttribute("fileCount", files.size());

    	return "files";
    }
    
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/remove/{fileHash}", method = RequestMethod.GET)
	public String removeFile(Model uiModel, HttpServletRequest request, @PathVariable("fileHash") Long fileHash) {
		File fileToDelete = fileService.findFileById(fileHash);
		if(fileToDelete != null){
			LOG.info("Will delete file with Id: " + fileToDelete.getId());
			if(fileService.removeFile(fileToDelete)){
				LOG.info("Did delete file.");
			}
		}
		
    	return "files";		
	}	    
    
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public String handleFormUpload(MultipartHttpServletRequest request) {
    	    	
    	MultipartFile mfile = request.getFile("file");
    	// This constructor populates the fields in the File object. 
    	File file = new File(mfile);
		file.setPostedTimestamp(new Timestamp(System.currentTimeMillis()));
    	Long fileId = fileService.saveFile(file);

    	LOG.info("--- Saving File ---");
    	LOG.info(file);
    	
   		return "{\"name\":\"" + file.getFileName() + "\",\"fileid\":\"" + file.getId() + "\",\"size\":\"" + file.getBytes().length + "\"}";
    }
}
