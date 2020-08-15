/*
 * Copyright (c) 2020, https://github.com/911992 All rights reserved.
 * License BSD 3-Clause (https://opensource.org/licenses/BSD-3-Clause)
 */

 /*
WAsys_pojo_http_data_servlet3_wrapper
File: Servlet_Request_Data_Handler.java
Created on: May 21, 2020 6:36:11 AM
    @author https://github.com/911992
 
History:
    0.2.5 (20200813)
        • Some documentation fixes

    0.2.0 (20200606)
        • Updated/Reworked documentation
        • Changed the way Scanner class is initialized in instream_to_str() method, now using different constructor
        • Changed CTX_POOL_POOL_INIT_LEN_KEY, CTX_POOL_POOL_MAX_LEN_KEY, and CTX_POOL_POOL_FULL_POL_KEY fields names and their values
            • CTX_POOL_POOL_INIT_LEN_KEY -> Servlet_Request_Data_Handler_POOL_INIT_LEN_KEY
            • CTX_POOL_POOL_MAX_LEN_KEY -> Servlet_Request_Data_Handler_POOL_MAX_LEN_KEY
            • CTX_POOL_POOL_FULL_POL_KEY -> Servlet_Request_Data_Handler_POOL_FULL_POL_KEY

    0.1.1 (20200531)
        • (deadly stupid bug), fixed the check at get_param_at() method, about checking if a param is in bound of out-of-form-data params

    initial version: 0.1(20200520)
 */
package wasys.lib.pojo_http_data.servlet3_wrapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Scanner;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import wasys.lib.generic_object_pool.Full_Pool_Object_Creation_Policy;
import wasys.lib.generic_object_pool.Generic_Object_Pool_Policy;
import wasys.lib.generic_object_pool.Object_Pool;
import wasys.lib.generic_object_pool.Pool_Context;
import wasys.lib.generic_object_pool.api.Object_Factory;
import wasys.lib.generic_object_pool.api.Poolable_Object;
import wasys.lib.pojo_http_data.Generic_Object_Filler;
import wasys.lib.pojo_http_data.api.Fillable_Object;
import wasys.lib.pojo_http_data.api.container.Request_Data_Handler;
import wasys.lib.pojo_http_data.api_wrapper.Poolable_Request_Data_Handler_Adapter;
import wasys.lib.pojo_http_data.exception.Unfillable_Object_Ex;

/**
 * A poolable({@link Poolable_Object}) Wrapper/Proxy class for Servlet 3.0 {@link HttpServletRequest}.
 * <p>It implements {@link Request_Data_Handler} by extending
 * its wrapper {@link Poolable_Request_Data_Handler_Adapter}.
 * </p>
 * <p>
 * For instancing(getting an object) this type, considering following approaches are listed below
 * </p>
 * <ul>
 * <li>Using internal pool({@link Object_Pool}) for a poolable({@link Poolable_Object}) object(recommended)</li>
 * <li>Explicit object instancing using {@code new}</li>
 * </ul>
 * <p>
 * considering following samples
 * </p>
 * <p>
 * <b>Using pooled instance</b>
 * </p>
 * <pre>
 * try(Servlet_Request_Data_Handler _srdh = Servlet_Request_Data_Handler.new_pooled_instance(_servlet_req_ins)){
 *  // using _srdh
 * }
 * //OR
 * Servlet_Request_Data_Handler _srdh = Servlet_Request_Data_Handler.new_pooled_instance(_servlet_req_ins)
 * // using _srdh
 * _srdh.close();//important! must be done
 * </pre>
 * 
 * <p>
 * <b>As a standalone instance</b>
 * </p>
 * <pre>
 * Servlet_Request_Data_Handler _srdh = new Servlet_Request_Data_Handler(_serv_req);
 * // using _srdh
 * </pre>
 * <hr>
 * <p><b>Internal Pool</b></p>
 * <p>
 * Internal pool <b>must</b> be initialized, before any pooled version is required.
 * </p>
 * <p>
 * This is recommended to start/shutdown the internal pool, as servlet context is initialized and destroyed using a simple servlet listener. Class {@link Servlet_Request_Data_Handler_Servlet_Ctx_Listener} does the exact thing, but <b>must</b> be mapped/added to target `web.xml` in order to get called.
 * </p>
 * <p>
 * This is also possible to customize the default internal pool initialization config using JNDI, by specifying related keys/values.
 * </p>
 * <p>
 * Considering following sample `web.xml` env resource mapping about internal pool initialization conf data.
 * </p>
 * <pre>
 * &lt;web-app ...&gt;
 * ...
 * &lt;env-entry&gt;
 *     &lt;env-entry-name&gt;Servlet_Request_Data_Handler_POOL_INIT_LEN&lt;/env-entry-name&gt;
 *     &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *     &lt;!-- -1 to specify using the default pool value, probably 0 --&gt;
 *     &lt;env-entry-value&gt;-1&lt;/env-entry-value&gt;
 * &lt;/env-entry&gt;
 * &lt;env-entry&gt;
 *     &lt;env-entry-name&gt;Servlet_Request_Data_Handler_POOL_MAX_LEN&lt;/env-entry-name&gt;
 *     &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *     &lt;env-entry-value&gt;32&lt;/env-entry-value&gt;
 * &lt;/env-entry&gt;
 * &lt;env-entry&gt;
 *     &lt;env-entry-name&gt;Servlet_Request_Data_Handler_POOL_FULL_POL&lt;/env-entry-name&gt;
 *     &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *     &lt;!-- values should be one of wasys.lib.generic_object_pool.Full_Pool_Object_Creation_Policy enum consts --&gt;
 *     &lt;env-entry-value&gt;Create_New_No_Pooling&lt;/env-entry-value&gt;
 * &lt;/env-entry&gt;
 * ...
 * &lt;/web-app&gt;
 * </pre>
 * @author https://github.com/911992
 */
public class Servlet_Request_Data_Handler extends Poolable_Request_Data_Handler_Adapter<HttpServletRequest> {

    /**
     * The JNDI key(integer value in String form/type), for specifying the
     * internal pool initialize value.
     * <p>
     * The value must be a valid decode-able integer value(e.g. 0x20), but as {@link String} type.
     * </p>
     * <p>
     * Considering following web descriptor example {@code web.xml}:
     * </p>
     * <pre>
     * &lt;env-entry&gt;
     * &lt;env-entry-name&gt;Servlet_Request_Data_Handler_POOL_INIT_LEN&lt;/env-entry-name&gt;
     * &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
     * &lt;env-entry-value&gt;8&lt;/env-entry-value&gt;
     * &lt;/env-entry&gt;
     * </pre>
     * <p>
     * <b>Note:</b> please mind the <b>value</b>({@code Servlet_Request_Data_Handler_POOL_INIT_LEN}) of this field should be used as JNDI/resource key, <u><b>not</b> the field's <b>name</b></u>
     * </p>
     */
    public static final String Servlet_Request_Data_Handler_POOL_INIT_LEN_KEY = "Servlet_Request_Data_Handler_POOL_INIT_LEN";

    /**
     * The JNDI key(integer value in String form/type), for specifying the
     * internal pool maximum object count value.
     * <p>
     * The value must be a valid decode-able integer value(e.g. 0x20), but as {@link String} type.
     * </p>
     * <p>
     * Considering following web descriptor example {@code web.xml}:
     * </p>
     * <pre>
     * &lt;env-entry&gt;
     * &lt;env-entry-name&gt;Servlet_Request_Data_Handler_POOL_MAX_LEN&lt;/env-entry-name&gt;
     * &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
     * &lt;env-entry-value&gt;128&lt;/env-entry-value&gt;
     * &lt;/env-entry&gt;
     * </pre>
     * <p>
     * <b>NOTE:</b> pool max len should not be less than the init/minimum({@code Servlet_Request_Data_Handler_POOL_INIT_LEN_KEY}) value.
     * </p>
     * <p>
     * <b>Note:</b> please mind the <b>value</b>({@code Servlet_Request_Data_Handler_POOL_MAX_LEN}) of this field should be used as JNDI/resource key, <u><b>not</b> the field's <b>name</b></u>
     * </p>
     */
    public static final String Servlet_Request_Data_Handler_POOL_MAX_LEN_KEY = "Servlet_Request_Data_Handler_POOL_MAX_LEN";

    /**
     * The JNDI key(String type), for specifying the internal pool full policy.
     * <p>
     * Value should be one of the {@link Full_Pool_Object_Creation_Policy}
     * enum's const (case-sensitive)
     * </p>
     * <p>
     * Considering following web descriptor example {@code web.xml}:
     * </p>
     * <pre>
     * &lt;env-entry&gt;
     * &lt;env-entry-name&gt;Servlet_Request_Data_Handler_POOL_FULL_POL&lt;/env-entry-name&gt;
     * &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
     * &lt;env-entry-value&gt;Create_New_No_Pooling&lt;/env-entry-value&gt;
     * &lt;/env-entry&gt;
     * </pre>
     * <p>
     * <b>Important note:</b> invalid/wrong value may result serious system instability. Please make sure you set a correct full pool policy.
     * </p>
     * <p>
     * <b>Note:</b> please mind the <b>value</b>({@code Servlet_Request_Data_Handler_POOL_FULL_POL}) of this field should be used as JNDI/resource key, <u><b>not</b> the field's <b>name</b></u>
     * </p>
     */
    public static final String Servlet_Request_Data_Handler_POOL_FULL_POL_KEY = "Servlet_Request_Data_Handler_POOL_FULL_POL";

    /**
     * Internal default object factory.
     * <p>
     * Simply creates instances of {@link Servlet_Request_Data_Handler} when asked.
     * </p>
     * <p>
     * Any created object comes with {@code null}-ptr associated to related {@link HttpServletRequest}
     * </p>
     */
    private static final class _Factory implements Object_Factory {

        /**
         * Creates an instance of {@link Servlet_Request_Data_Handler}, with {@code null}-ptr association to {@link HttpServletRequest}.
         * @return a new non-{@code null} (but not-ready) {@link Servlet_Request_Data_Handler} instance
         */
        @Override
        public Poolable_Object create_object() {
            return new Servlet_Request_Data_Handler();
        }

        /**
         * Default constructor.
         */
        _Factory() {
        }

    }

    /**
     * Pointer to a concreted {@link Object_Pool} that holds the instances of
     * this type.
     */
    private static Object_Pool def_pool = null;

    /**
     * The default charset({@code utf-8}) for decoding the multipart.
     * <p>
     * Default charset when the expected
     * {@code _charset_} parameter is not available(not sent by form).
     * </p>
     */
    public static final Charset DEFAULT_CHARSET;

    /**
     * The ASCII charset for decoding the multipart {@code _charset_} parameter.
     */
    public static final Charset ASCII_CHARSET;

    /**
     * Tries to initializes the {@code DEFAULT_CHARSET}, or using the default
     * charset if failed.
     */
    static {
        String _def_charset = "UTF-8";
        if (Charset.isSupported(_def_charset)) {
            DEFAULT_CHARSET = Charset.forName(_def_charset);
        } else {
            DEFAULT_CHARSET = Charset.defaultCharset();
        }
    }

    /**
     * Tries to initializes the {@code ASCII_CHARSET}, or using the default
     * charset if failed.
     */
    static {
        String _ascii_charset = "ASCII";
        if (Charset.isSupported(_ascii_charset)) {
            ASCII_CHARSET = Charset.forName(_ascii_charset);
        } else {
            ASCII_CHARSET = Charset.defaultCharset();
        }
    }

    /**
     * Pointer to working servlet http req.
     * <p>
     * If this object was grabbed as a pooled one from the internal pool, then this field is supposed to be {@code null} meanwhile it's in pool context.
     * </p>
     * <p>
     * During usage of {@code this} object, this ptr <b>MUST</b> point to a non-{@code null} valid {@link HttpServletRequest}
     * </p>
     */
    private HttpServletRequest req;

    /**
     * Pointer to cached {@code _charset_} parameter value (multipart).
     */
    private Charset cached_charset = null;

    /**
     * Var that indicates if {@code _charset_} parameter has been proceed.
     */
    private boolean cached_charset_done = false;

    /**
     * Initializes the internal pool(if required).
     * <p>
     * It asks the JNDI for any
     * config(vals) have been specified for initializing the pool, or uses the
     * default instance in case of missed/invalid values. </p>
     * <p>
     * Before calling this method, requesting for a pooled instance will cause a
     * {@code NullPointerException}
     * </p>
     */
    synchronized public static void init_default_pool() {
        if (def_pool != null) {
            return;
        }
        Generic_Object_Pool_Policy _pol = Generic_Object_Pool_Policy.DEF_INS;
        try {
            int _init_size = _pol.getMin_object_count();
            int _max_size = _pol.getMax_object_count();
            Full_Pool_Object_Creation_Policy _full_pol = _pol.getFull_pool_instancing_policy();
            InitialContext _ic = new InitialContext();
            String _init_size_str = (String) _ic.lookup(String.format("java:comp/env/%s", Servlet_Request_Data_Handler_POOL_INIT_LEN_KEY));
            String _max_size_str = (String) _ic.lookup(String.format("java:comp/env/%s", Servlet_Request_Data_Handler_POOL_MAX_LEN_KEY));
            String _full_pol_str = (String) _ic.lookup(String.format("java:comp/env/%s", Servlet_Request_Data_Handler_POOL_FULL_POL_KEY));
            if (_init_size_str != null) {
                _init_size = Integer.decode(_init_size_str);
                if (_init_size < 0) {
                    _init_size = _pol.getMin_object_count();
                }
            }
            if (_max_size_str != null) {
                _max_size = Integer.decode(_max_size_str);
                if (_max_size < 0) {
                    _max_size = _pol.getMax_object_count();
                }
            }
            if (_full_pol_str != null) {
                _full_pol = Full_Pool_Object_Creation_Policy.valueOf(_full_pol_str);
            }
            _pol = new Generic_Object_Pool_Policy(_init_size, _max_size, _full_pol);
        } catch (Exception e) {
            e.printStackTrace();
        }
        _Factory _fact = new _Factory();
        def_pool = Pool_Context.get_insatcne().get_pool_unregistered_synced(_fact, _pol);
    }

    /**
     * Shutdowns the internal pool, and mark it as {@code null}.
     * <p>After calling this method, requesting for a pooled instance will cause a
     * {@code NullPointerException}</p>
     */
    synchronized public static void shutdown_default_pool() {
        if (def_pool == null) {
            return;
        }
        def_pool.shutdown_pool();
        def_pool = null;
    }

    /**
     * Asks the internal pool for a pooled instance. 
     * <p>The returning instance
     * <b>MUST</b> be released({@code close()}) once it's no more required.
     * </p>
     * <p>
     * Best practice is using a {@code try-with-resources} block to make sure
     * the pooled instance will be closed automatically
     * </p>
     * @param arg_req pointer to concreted/non-{@code null} servlet http req
     * @return a poolable({@link Poolable_Object}) instance of {@code Servlet_Request_Data_Handler}
     * @throws NullPointerException when the internal pool has not been
     * initialized yet
     */
    public static Servlet_Request_Data_Handler new_pooled_instance(HttpServletRequest arg_req) {
        Servlet_Request_Data_Handler _ins = (Servlet_Request_Data_Handler) def_pool.get_an_instance();
        _ins.set_req(arg_req);
        return _ins;
    }

    /**
     * Simply calls the {@code fill_all} method.
     *
     * @param <A> type of the concreted {@link Fillable_Object}
     * @param arg_req concreted and non-{@code null} http servlet request obj
     * @param arg_pojo concreted and non-{@code null} {@link Fillable_Object} needs to be filled
     * @return the same {@code arg_pojo} regardless of success or failed fill op
     * @throws Unfillable_Object_Ex if the given {@code arg_pojo} is not a valid
     * fillable object
     * @throws NullPointerException when the internal pool has not been
     * initialized yet
     */
    public static <A extends Fillable_Object> A fill(HttpServletRequest arg_req, A arg_pojo) throws Unfillable_Object_Ex {
        fill_all(arg_req, arg_pojo);
        return arg_pojo;
    }

    /**
     * Asks the internal pool for a pooled instance, and then calls the default
     * POJO filler({@link Generic_Object_Filler}) to fill the given objects.
     *
     * @param arg_req concreted and non-{@code null} http servlet request obj
     * @param arg_pojos concreted and non-{@code null} {@link Fillable_Object}s
     * need to be filled
     * @throws Unfillable_Object_Ex if the given {@code arg_pojo}s are(all or
     * one) not valid fillable object
     * @throws NullPointerException when the internal pool has not been
     * initialized yet
     */
    public static void fill_all(HttpServletRequest arg_req, Fillable_Object... arg_pojos) throws Unfillable_Object_Ex {
        try (Servlet_Request_Data_Handler _hnd = new_pooled_instance(arg_req)) {
            for (int a = 0; a < arg_pojos.length; a++) {
                _hnd.fill_object(arg_pojos[a]);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    ;
    
    /**
     * When a non-pooled standalone object of this type is needed.
     * @param req concreted and non-{@code null} http servlet request obj
     */
    public Servlet_Request_Data_Handler(HttpServletRequest req) {
        this.req = req;
    }

    /**
     * Called by the default/internal object factory.
     * <p>
     * A new instance of this type with {@code null} ptr for the related {@code req}({@link HttpServletRequest})
     * </p>
     */
    private Servlet_Request_Data_Handler() {
    }

    /**
     * Called by {@code new_pooled_instance} when a not-ready instance is
     * created, and need to be initialized by associating a non-{@code null} http servlet req instance.
     * @param arg_req the valid and non-{@code null} http req instance nee to be used for filling
     */
    private void set_req(HttpServletRequest arg_req) {
        this.req = arg_req;
    }

    /**
     * Returns a multipart part for given name and given index.
     *
     * @param arg_param part/parameter name
     * @param arg_idx part/parameter index
     * @return the {@link Part} if found, not {@code null}
     */
    private Part get_part_at(String arg_param, int arg_idx) {
        try {
            Collection<Part> _pts = req.getParts();
            /**
             * From the javadoc
             * https://docs.oracle.com/javaee/6/api/javax/servlet/http/HttpServletRequest.html#getParts()
             * It doesn't state if request is not a multipart, so we assume this
             * would return a possible null val
             */
            if (_pts == null) {
                return null;
            }
            int _idx = 0;
            for (Part _pt : _pts) {
                if (_pt.getName().equals(arg_param)) {
                    if (_idx == arg_idx) {
                        return _pt;
                    }
                    _idx++;
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Returns a sub-part/parameter from the given part's parameter name.
     *
     * @param arg_part the part instance
     * @param arg_head name of the part header
     * @param arg_head_part name of the sub-part section of part header's
     * @return sub-part value of the part header's or {@code null}
     */
    private String get_part_content_sub_head(Part arg_part, String arg_head, String arg_head_part) {
        String _pt_file_inf_head = arg_part.getHeader(arg_head);
        if (_pt_file_inf_head == null) {
            return null;
        }
        String[] _spl = _pt_file_inf_head.split("\\;");
        for (String _spl_ins : _spl) {
            _spl_ins = _spl_ins.trim();
            if (_spl_ins.startsWith(arg_head_part)) {
                int _idx = _spl_ins.indexOf("=");
                if (_idx == -1) {
                    return "";
                }
                return _spl_ins.substring(_idx + 1).trim();
            }
        }
        return null;
    }

    /**
     * Calls {@code get_part_content_sub_head} to get given sub-param from
     * {@code content-disposition} header.
     *
     * @param arg_part the part instance
     * @param arg_head_part name of the sub-param of {@code content-disposition}
     * header
     * @return value from {@code get_part_content_sub_head}
     */
    private String get_part_content_sub_head_disposition(Part arg_part, String arg_head_part) {
        return get_part_content_sub_head(arg_part, "content-disposition", arg_head_part);
    }

    /**
     * Returns a value if it has marked as form-data.
     *
     * @param arg_param_name the part/parameter name
     * @param arg_idx index of the parameter
     * @return the part that is sit at given index with given name, or {@code null} if part is not available
     */
    private Part get_multipart_form_data(String arg_param_name, int arg_idx) {
        Part _pt = get_part_at(arg_param_name, arg_idx);
        if (_pt == null) {
            return null;
        }
        String _filename = get_part_content_sub_head_disposition(_pt, "filename");
        if (_filename == null) {
            return _pt;
        }
        return null;
    }

    /**
     * Converts a input stream that is associated to a formdata part, into a
     * string.
     * <p>
     * Since version 0.2, the constructor is used for {@link Scanner} class is changed to {@link InputStreamReader}, since the previously used constructor is available since JDK 10 and later.
     * </p>
     * @param arg_is the stream need to be read
     * @param arg_cs the charset need to be considered while reading the in
     * stream
     * @return a string value that were populated from given in stream or
     * {@code null} because of any IO exception
     */
    private String instream_to_str(InputStream arg_is, Charset arg_cs) {
        try (InputStreamReader _insr=new InputStreamReader(arg_is, arg_cs) ;Scanner _sc = new Scanner(_insr).useDelimiter("\\A")) {
            String _res = _sc.next();
            return _res;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Tries to find, read and decode the @{code _charset_} parameter from the
     * request to set the {@code cached_charset} related to the form charset.
     * <p>
     * If the {@code _charset_} could not be found, the {@code DEFAULT_CHARSET}
     * will be used as cached charset.
     * </p>
     *
     * @return the already cached/proceed multipart form charset, or tries to
     * find and decode it
     */
    private Charset get_multipart_charset() {
        if (cached_charset_done) {
            return cached_charset;
        }
        cached_charset_done = true;
        Part _pt = get_multipart_form_data("_charset_", 0);
        if (_pt == null) {
            return null;
        }
        try {
            String _charset_str = instream_to_str(_pt.getInputStream(), ASCII_CHARSET);
            if (_pt != null && Charset.isSupported(_charset_str)) {
                cached_charset = Charset.forName(_charset_str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cached_charset;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String get_param_at(String arg_param_name, int arg_idx) {
        String[] _vals = req.getParameterValues(arg_param_name);
        if (_vals != null) {
            if (_vals.length > arg_idx) {
                return _vals[arg_idx];
            } else {
                arg_idx = arg_idx - _vals.length;
            }
        }
        if (is_multipart_request()) {
            Part _pt = get_multipart_form_data(arg_param_name, arg_idx);
            if (_pt != null) {
                Charset _param_charset = get_multipart_charset();
                if (_param_charset == null) {
                    String _chset = get_part_content_sub_head(_pt, "content-type", "charset");
                    if (_chset != null && Charset.isSupported(_chset)) {
                        _param_charset = Charset.forName(_chset);
                    } else {
                        _param_charset = DEFAULT_CHARSET;
                    }
                }
                try {
                    String _data = instream_to_str(_pt.getInputStream(), _param_charset);
                    return _data;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }
        return null;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String[] get_param_vals(String arg_param_name) {
        return req.getParameterValues(arg_param_name);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public int param_count() {
        return req.getParameterMap().size();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean is_multipart_request() {
        String _head = req.getHeader("Content-Type");
        if (_head == null) {
            return false;
        }
        return _head.startsWith("multipart/form-data");
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String get_part_filename_at(String arg_param, int arg_idx) {
        Part _pt = get_part_at(arg_param, arg_idx);
        if (_pt == null) {
            return null;
        }
        String _fn = get_part_content_sub_head_disposition(_pt, "filename");
        if (_fn == null || _fn.isEmpty()) {
            return null;
        }
        return _fn.replace("\"", "");
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String get_part_mime_part_at(String arg_param, int arg_idx) {
        Part _pt = get_part_at(arg_param, arg_idx);
        if (_pt == null) {
            return null;
        }
        return _pt.getContentType();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public long get_part_size_at(String arg_param, int arg_idx) {
        Part _pt = get_part_at(arg_param, arg_idx);
        if (_pt == null) {
            return -1;
        }
        return _pt.getSize();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public InputStream get_part_stream_at(String arg_param, int arg_idx) throws IOException {
        Part _pt = get_part_at(arg_param, arg_idx);
        if (_pt == null) {
            return null;
        }
        return _pt.getInputStream();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public HttpServletRequest get_associated_request() {
        return req;
    }

    /**
     * Resets the current pooled-supposed instance by marking the working {@link HttpServletRequest}({@code req}) to {@code null}.
     */
    @Override
    public void reset_state() {
        req = null;
        cached_charset = null;
        cached_charset_done = false;
    }

}
