package halo.web.action.upload.cos;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

public class MultipartRequest {

    private static final int DEFAULT_MAX_POST_SIZE = 1024 * 1024; // 1 Meg

    // name Vector of values
    protected Map<String, List<String>> parameters = new LinkedHashMap<String, List<String>>();

    // name - UploadedFile
    protected Map<String, UploadedFile> files = new LinkedHashMap<String, UploadedFile>();

    public MultipartRequest(HttpServletRequest request, String saveDirectory)
            throws IOException, ExceededSizeException {
        this(request, saveDirectory, DEFAULT_MAX_POST_SIZE);
    }

    public MultipartRequest(HttpServletRequest request, String saveDirectory,
            int maxPostSize) throws IOException, ExceededSizeException {
        this(request, saveDirectory, maxPostSize, null, null);
    }

    public MultipartRequest(HttpServletRequest request, String saveDirectory,
            String encoding) throws IOException, ExceededSizeException {
        this(request, saveDirectory, DEFAULT_MAX_POST_SIZE, encoding, null);
    }

    public MultipartRequest(HttpServletRequest request, String saveDirectory,
            int maxPostSize, FileRenamePolicy policy) throws IOException,
            ExceededSizeException {
        this(request, saveDirectory, maxPostSize, null, policy);
    }

    public MultipartRequest(HttpServletRequest request, String saveDirectory,
            int maxPostSize, String encoding) throws IOException,
            ExceededSizeException {
        this(request, saveDirectory, maxPostSize, encoding, null);
    }

    @SuppressWarnings({ "deprecation", "unchecked" })
    public MultipartRequest(HttpServletRequest request, String saveDirectory,
            int maxPostSize, String encoding, FileRenamePolicy policy)
            throws IOException, ExceededSizeException {
        // Sanity check values
        if (request == null)
            throw new IllegalArgumentException("request cannot be null");
        if (saveDirectory == null)
            throw new IllegalArgumentException("saveDirectory cannot be null");
        if (maxPostSize <= 0) {
            throw new IllegalArgumentException("maxPostSize must be positive");
        }
        // Save the dir
        File dir = new File(saveDirectory);
        // Check saveDirectory is truly a directory
        if (!dir.isDirectory())
            throw new IllegalArgumentException("Not a directory: "
                    + saveDirectory);
        // Check saveDirectory is writable
        if (!dir.canWrite())
            throw new IllegalArgumentException("Not writable: " + saveDirectory);
        // Parse the incoming multipart, storing files in the dir provided,
        // and populate the meta objects which describe what we found
        MultipartParser parser = new MultipartParser(request, maxPostSize,
                true, true, encoding);
        // Some people like to fetch query string parameters from
        // MultipartRequest, so here we make that possible. Thanks to
        // Ben Johnson, ben.johnson@merrillcorp.com, for the idea.
        if (request.getQueryString() != null) {
            // Let HttpUtils create a name->String[] structure
            Hashtable<String, String[]> queryParameters = javax.servlet.http.HttpUtils
                    .parseQueryString(request.getQueryString());
            // For our own use, name it a name->Vector structure
            Enumeration<String> queryParameterNames = queryParameters.keys();
            while (queryParameterNames.hasMoreElements()) {
                String paramName = queryParameterNames.nextElement();
                String[] values = queryParameters.get(paramName);
                Vector<String> newValues = new Vector<String>();
                for (int i = 0; i < values.length; i++) {
                    newValues.add(values[i]);
                }
                parameters.put(paramName, newValues);
            }
        }
        Part part;
        while ((part = parser.readNextPart()) != null) {
            String name = part.getName();
            if (name == null) {
                throw new IOException(
                        "Malformed input: parameter name missing (known Opera 7 bug)");
            }
            if (part.isParam()) {
                // It's a parameter part, add it to the vector of values
                ParamPart paramPart = (ParamPart) part;
                String value = paramPart.getStringValue();
                List<String> existingValues = parameters.get(name);
                if (existingValues == null) {
                    existingValues = new ArrayList<String>();
                    parameters.put(name, existingValues);
                }
                existingValues.add(value);
            }
            else if (part.isFile()) {
                // It's a file part
                FilePart filePart = (FilePart) part;
                String fileName = filePart.getFileName();
                if (fileName != null) {
                    filePart.setRenamePolicy(policy); // null policy is OK
                    // The part actually contained a file
                    filePart.writeTo(dir);
                    files.put(name, new UploadedFile(dir.toString(), fileName,
                            filePart.getFilePath(), filePart.getContentType()));
                }
                else {
                    // The field did not contain a file
                    files.put(name, new UploadedFile(null, null, null, null));
                }
            }
        }
    }

    /**
     * Constructor with an old signature, kept for backward compatibility.
     * Without this constructor, a servlet compiled against a previous version
     * of this class (pre 1.4) would have to be recompiled to link with this
     * version. This constructor supports the linking via the old signature.
     * Callers must simply be careful to pass in an HttpServletRequest.
     */
    public MultipartRequest(ServletRequest request, String saveDirectory)
            throws IOException, ExceededSizeException {
        this((HttpServletRequest) request, saveDirectory);
    }

    /**
     * Constructor with an old signature, kept for backward compatibility.
     * Without this constructor, a servlet compiled against a previous version
     * of this class (pre 1.4) would have to be recompiled to link with this
     * version. This constructor supports the linking via the old signature.
     * Callers must simply be careful to pass in an HttpServletRequest.
     */
    public MultipartRequest(ServletRequest request, String saveDirectory,
            int maxPostSize) throws IOException, ExceededSizeException {
        this((HttpServletRequest) request, saveDirectory, maxPostSize);
    }

    /**
     * Returns the names of all the parameters as an Enumeration of Strings. It
     * returns an empty Enumeration if there are no parameters.
     * 
     * @return the names of all the parameters as an Enumeration of Strings.
     */
    public Set<String> getParameterNames() {
        return parameters.keySet();
    }

    /**
     * Returns the names of all the uploaded files as an Enumeration of Strings.
     * It returns an empty Enumeration if there are no file input fields on the
     * form. Any file input field that's left empty will result in a FilePart
     * with null contents. Each file name is the name specified by the form, not
     * by the user.
     * 
     * @return the names of all the uploaded files as an Enumeration of Strings.
     */
    public Set<String> getFileNames() {
        return files.keySet();
    }

    /**
     * Returns the value of the named parameter as a String, or null if the
     * parameter was not sent or was sent without a value. The value is
     * guaranteed to be in its normal, decoded form. If the parameter has
     * multiple values, only the last one is returned (for backward
     * compatibility). For parameters with multiple values, it's possible the
     * last "value" may be null.
     * 
     * @param name
     *            the parameter name.
     * @return the parameter value.
     */
    public String getParameter(String name) {
        try {
            List<String> values = parameters.get(name);
            if (values == null || values.size() == 0) {
                return null;
            }
            String value = values.get(values.size() - 1);
            return value;
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * Returns the values of the named parameter as a String array, or null if
     * the parameter was not sent. The array has one entry for each parameter
     * field sent. If any field was sent without a value that entry is stored in
     * the array as a null. The values are guaranteed to be in their normal,
     * decoded form. A single value is returned as a one-element array.
     * 
     * @param name
     *            the parameter name.
     * @return the parameter values.
     */
    public String[] getParameterValues(String name) {
        try {
            List<String> values = parameters.get(name);
            if (values == null || values.size() == 0) {
                return null;
            }
            String[] valuesArray = values.toArray(new String[values.size()]);
            return valuesArray;
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * Returns the filesystem name of the specified file, or null if the file
     * was not included in the upload. A filesystem name is the name specified
     * by the user. It is also the name under which the file is actually saved.
     * 
     * @param name
     *            the html page's file parameter name.
     * @return the filesystem name of the file.
     */
    public String getFilesystemName(String name) {
        try {
            UploadedFile file = files.get(name);
            return file.getFilesystemName(); // may be null
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * Returns the original filesystem name of the specified file (before any
     * renaming policy was applied), or null if the file was not included in the
     * upload. A filesystem name is the name specified by the user.
     * 
     * @param name
     *            the html page's file parameter name.
     * @return the original file name of the file.
     */
    public String getOriginalFileName(String name) {
        try {
            UploadedFile file = files.get(name);
            return file.getOriginalFileName(); // may be null
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * Returns the content type of the specified file (as supplied by the client
     * browser), or null if the file was not included in the upload.
     * 
     * @param name
     *            the html page's file parameter name.
     * @return the content type of the file.
     */
    public String getContentType(String name) {
        try {
            UploadedFile file = files.get(name);
            return file.getContentType(); // may be null
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * Returns a File object for the specified file saved on the server's
     * filesystem, or null if the file was not included in the upload.
     * 
     * @param name
     *            the html page's file parameter name.
     * @return a File object for the named file.
     */
    public File getFile(String name) {
        try {
            UploadedFile file = files.get(name);
            return file.getFile(); // may be null
        }
        catch (Exception e) {
            return null;
        }
    }
}

// A class to hold information about an uploaded file.
//
class UploadedFile {

    private String dir;

    private String filename;

    private String original;

    private String type;

    UploadedFile(String dir, String filename, String original, String type) {
        this.dir = dir;
        this.filename = filename;
        this.original = original;
        this.type = type;
    }

    public String getContentType() {
        return type;
    }

    public String getFilesystemName() {
        return filename;
    }

    public String getOriginalFileName() {
        return original;
    }

    public File getFile() {
        if (dir == null || filename == null) {
            return null;
        }
        return new File(dir + File.separator + filename);
    }
}
