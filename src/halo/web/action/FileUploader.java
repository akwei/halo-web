package halo.web.action;

import halo.web.action.upload.cos.ExceededSizeException;
import halo.web.action.upload.cos.MultipartRequest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

public class FileUploader {

    private HkMultiRequest hkMultiRequest;

    private UploadFile[] uploadFiles;

    private static final String METHOD_POST = "POST";

    // 上传文件的总大小限制在15M，默认设置
    private int _maxPostSize = 20;

    private static final int dv = 1024 * 1024;

    public FileUploader(HttpServletRequest request, String uploadFileTempPath,
            int maxPostSize) throws IOException, ExceededSizeException {
        if (!(request.getMethod().equals(METHOD_POST) && HkMultiRequest
                .isMultipart(request))) {
            throw new RuntimeException(
                    "not post method and multipart/form-data");
        }
        File dir = new File(uploadFileTempPath);
        if (!dir.isDirectory()) {
            if (!dir.mkdir()) {
                throw new IllegalArgumentException(
                        "no permission on server to mkdir file [ "
                                + uploadFileTempPath + " ]");
            }
        }
        if (!dir.canWrite()) {
            throw new IllegalArgumentException(
                    "no permission on server to operate file [ "
                            + uploadFileTempPath + " ]");
        }
        int _size = maxPostSize;
        if (_size == 0) {
            _size = this._maxPostSize;
        }
        MultipartRequest multipartRequest = new MultipartRequest(request,
                uploadFileTempPath, _size * dv, "utf-8");
        this.hkMultiRequest = new HkMultiRequest(request, multipartRequest);
        Set<String> set = multipartRequest.getFileNames();
        List<File> fileList = new ArrayList<File>(1);
        List<UploadFile> uploadFileList = new ArrayList<UploadFile>(1);
        UploadFile uploadFile = null;
        for (String n : set) {
            File f = multipartRequest.getFile(n);
            if (f != null) {
                fileList.add(f);
                uploadFile = new UploadFile(n, f,
                        multipartRequest.getOriginalFileName(n));
                uploadFileList.add(uploadFile);
            }
        }
        if (fileList.size() > 0) {
            uploadFiles = uploadFileList.toArray(new UploadFile[uploadFileList
                    .size()]);
        }
    }

    public HkMultiRequest getHkMultiRequest() {
        return hkMultiRequest;
    }

    public UploadFile[] getUploadFiles() {
        return uploadFiles;
    }
}