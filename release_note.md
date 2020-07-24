# WAsys_pojo_http_data_servlet3_wrapper Release Note

repo: https://github.com/911992/WAsys_pojo_http_data_servlet3_wrapper  
Author: [911992](https://github.com/911992)  
*(NOTE: following list carries mentionable(not all) changes. For detailed changes, check source code(s))*  

**0.2.2** (Jun 24, 2020)

0. Updated the depedency of [WAsys_pojo_http_data](https://github.com/911992/WAsys_pojo_http_data) to `v0.2.1` (20200724)
1. Repo
    * Updated `pom.xml`
        * Updated the dependency of `WAsys_pojo_http_data` to `0.2.1`
        * Updated the artifact to `0.2.2` ( to avoid confusion with `0.2.0`)
    * Updated `README.md` file
        * Updated the maven repositiory to `0.2.2`

<hr/>

**0.2.1** (Jun 8, 2020)

0. Added the sample usage repo link (https://github.com/911992/WAsys_pojo_http_data_servlet3_wrapper_test)
1. Repo
    * Updated `README.md` file
        * Added "Sample Usage" section, and link to the repo
        * Marked the "Usage example" in "TODOs" section checked

<hr/>

**0.2.0** (Jun 6, 2020)

0. Lib/artifact now is available from **Maven** central repository (⌐■_■)
1. Project now follows a std Maven project layout.
    * Moved all source files from `src` to `src/main/java` folder
2. `Source_Code::all`
    * Fixed and reworked documentation
3. `Source_Code::Servlet_Request_Data_Handler`
    * Changed the way `Scanner` class is initialized in `instream_to_str()` method, now using different constructor.
    * Changed `CTX_POOL_POOL_INIT_LEN_KEY`, `CTX_POOL_POOL_MAX_LEN_KEY`, and `CTX_POOL_POOL_FULL_POL_KEY` fields names and their values
        * `CTX_POOL_POOL_INIT_LEN_KEY` -> `Servlet_Request_Data_Handler_POOL_INIT_LEN_KEY`
        * `CTX_POOL_POOL_MAX_LEN_KEY` -> `Servlet_Request_Data_Handler_POOL_MAX_LEN_KEY`
        * `CTX_POOL_POOL_FULL_POL_KEY` -> `Servlet_Request_Data_Handler_POOL_FULL_POL_KEY`
4. Added `package-info.java` file for package `wasys.lib.pojo_http_data.servlet3_wrapper`
5. Diagrams
    * Updated the class diagram (changes could be found [here](./_docs/diagram/class_diagram_release_note.md))
6. Repo
    * Added `pom.xml` maven file
    * Added `README.txt`, and `LICENSE.txt` files
    * Added `target`(as maven output) to `.gitignore`
    * Updated `README.md` file
        * Added Maven Repository section *(another victory for greater good)*, same on "TODOs" as a checked list.
        * Updated **`Servlet_Request_Data_Handler` Internal Pool Initializing** section.
            * Fixed the sample `web.xml`, with updated key values of `Servlet_Request_Data_Handler`

<hr/>

**0.1.1** (May 31, 2020)

0. `Source_Code::Servlet_Request_Data_Handler`
    * *(deadly stupid bug)*, fixed the check at `get_param_at()` method, about validating if a param is in bound of out-of-form-data params

<hr/>

**Initial Release 0.1** (May 20, 2020)