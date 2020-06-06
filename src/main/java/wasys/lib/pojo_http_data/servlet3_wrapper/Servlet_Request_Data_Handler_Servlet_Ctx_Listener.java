/*
 * Copyright (c) 2020, https://github.com/911992 All rights reserved.
 * License BSD 3-Clause (https://opensource.org/licenses/BSD-3-Clause)
 */

/*
WAsys_pojo_http_data_servlet3_wrapper
File: Servlet_Request_Data_Handler_Servlet_Ctx_Listener.java
Created on: May 29, 2020 2:42:53 AM
    @author https://github.com/911992
 
History:
    0.2.0 (20200606)
        • Updated/Reworked documentation

    initial version: 0.1(20200520)
*/

package wasys.lib.pojo_http_data.servlet3_wrapper;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


/**
 * A Servlet context listener (need to be registered at the {@code web.xml}) that calls for initialize the internal pool of {@link Servlet_Request_Data_Handler} type.
 * <p>
 * For registration, add this listener, to the web descriptor {@code web.xml} file, as following.
 * </p>
 * <pre>
 *  &lt;listener&gt;
 *       &lt;listener-class&gt;wasys.lib.pojo_http_data.servlet3_wrapper.Servlet_Request_Data_Handler_Servlet_Ctx_Listener&lt;/listener-class&gt;
 *  &lt;/listener&gt;
 * </pre>
 * @author https://github.com/911992
 */
public class Servlet_Request_Data_Handler_Servlet_Ctx_Listener implements ServletContextListener{

    /**
     * Calls for initialization of internal pool of the {@link Servlet_Request_Data_Handler}.
     * @param sce the related servlet context
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Servlet_Request_Data_Handler.init_default_pool();
    }

    /**
     * Calls for shutting down the internal pool of the {@link Servlet_Request_Data_Handler}
     * @param sce the related servlet context
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        Servlet_Request_Data_Handler.shutdown_default_pool();
    }

}
