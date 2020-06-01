package com.bantuin.ticket.controller;

import com.bantuin.ticket.model.User;
import id.co.javan.keboot.core.exception.UnauthorizedException;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseApiController {
    private final static int MAX_TRY = 10;

    protected User getCurrentUser(HttpServletRequest request) {
        return (User)request.getAttribute("current_user");
    }

    protected List getUserGroups(HttpServletRequest request) {
        return (List)request.getAttribute("group_id");
    }

    protected boolean hasGroupId(HttpServletRequest request, String groupId) {
        List<String> groupIdList = (List) request.getAttribute("group_id");
        for (String groupIdRow : groupIdList) {
            if (groupId.equals(groupIdRow)) {
                return true;
            }
        }

        return false;
    }

    protected ResponseEntity<Map> ok(Map responseData) {
        Map response = new HashMap();

        if(responseData != null) {
            response.putAll(responseData);
        }

        return new ResponseEntity<Map>(response, HttpStatus.OK);
    }

    protected ResponseEntity<User> ok(User responseData) {

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    protected ResponseEntity<Map> ok(String message) {
        Map response = new HashMap();
        response.put("message", message);

        return new ResponseEntity<Map>(response, HttpStatus.OK);
    }

    protected ResponseEntity<Page> ok(Page page) {

        return new ResponseEntity<Page>(page, HttpStatus.OK);
    }

    protected ResponseEntity<Resource> ok(Resource resource, File file) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.setContentLength(file.length());
        headers.setContentDisposition(ContentDisposition.parse("attachment; filename=" + file.getName()));
        headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));

        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }

    protected ResponseEntity<byte[]> ok(byte[] file, String filename) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.setContentLength(file.length);
        headers.setContentDisposition(ContentDisposition.parse("attachment; filename=" + filename));
        headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));

        return new ResponseEntity<byte[]>(file, headers, HttpStatus.OK);
    }

    protected ResponseEntity<byte[]> ok(byte[] file, String filename, String contentType) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.setContentLength(file.length);
        headers.setContentDisposition(ContentDisposition.parse("attachment; filename=" + filename));
        headers.setContentType(MediaType.parseMediaType(contentType));

        return new ResponseEntity<byte[]>(file, headers, HttpStatus.OK);
    }

    protected ResponseEntity<Object> okEntity(Object entity) {

        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    protected void handlePermitted(boolean condition) throws UnauthorizedException {
        if(!condition) {
            throw new UnauthorizedException("Anda tidak memiliki akses terhadap data ini");
        }
    }
}
